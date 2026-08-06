package io.github.discusser.toomanyentities.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.discusser.toomanyentities.TooManyEntities;
import io.github.discusser.toomanyentities.access.WorldRendererAccess;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderManager.class)
public class EntityRenderManagerMixin {
    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    private <S extends EntityRenderState> void beforeEntityRender(CallbackInfo info, @Local(argsOnly = true) S renderState) {
        // See https://github.com/Discusser/TooManyEntities/issues/23
        if (renderState == null || renderState.entityType == null) return;

        String key = renderState.entityType.getTranslationKey();
        int maxEntityCount = TooManyEntities.getMaxCountForEntity(renderState.entityType);

        // We only cancel a render if the render state meets explicit criteria
        boolean cancelRender = false;
        if (TooManyEntitiesConfig.instance.hideBasedOnDistance) {
            var distances = ((WorldRendererAccess)MinecraftClient.getInstance().worldRenderer).too_many_entities$distances();
            if (distances.containsKey(key)) {
                cancelRender = distances.get(key).getOrDefault(renderState, 0) >= maxEntityCount;
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
