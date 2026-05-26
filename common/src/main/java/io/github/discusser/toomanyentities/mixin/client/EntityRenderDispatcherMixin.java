package io.github.discusser.toomanyentities.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.discusser.toomanyentities.TooManyEntities;
import io.github.discusser.toomanyentities.access.WorldRendererAccess;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Inject(method = "submit", at = @At(value = "HEAD"), cancellable = true)
    private <S extends EntityRenderState> void beforeEntityRender(
            CallbackInfo info,
            @Local(argsOnly = true) S entity
    ) {
        String key = entity.entityType.getDescriptionId();
        int maxEntityCount = TooManyEntities.getMaxCountForEntity(entity.entityType);

        boolean cancelRender;
        if (TooManyEntitiesConfig.instance.hideBasedOnDistance) {
            var distances = ((WorldRendererAccess) Minecraft.getInstance().levelRenderer).too_many_entities$distances();
            if (distances.containsKey(key)) {
                cancelRender = distances.get(key).getOrDefault(entity, 0) >= maxEntityCount;
            } else {
                cancelRender = true;
            }
        } else {
            cancelRender = TooManyEntities.renderedCount.getOrDefault(key, 0) >= maxEntityCount;
        }

        if (TooManyEntities.modEnabled && maxEntityCount > 0 && cancelRender) {
            info.cancel();
        } else {
            TooManyEntities.renderedCount.put(key, TooManyEntities.renderedCount.getOrDefault(key, 0) + 1);
        }
    }
}
