package io.github.discusser.toomanyentities.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.tr7zw.entityculling.EntityCullingModBase;
import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class EntityCullingWorldRendererMixin {
    @Unique
    private int previousRenderedEntities = 0;

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;regularEntityCount:I", ordinal = 1, shift = At.Shift.AFTER))
    private void afterEntityCountIncrement(CallbackInfo info, @Local Entity entity) {
        String key = entity.getType().getTranslationKey();
        if (TooManyEntitiesConfig.instance.useEntityCulling) {
            int previous = previousRenderedEntities;
            int current = EntityCullingModBase.instance.renderedEntities;
            if (current - previous > 0) {
                TooManyEntitiesClient.entityCounts.put(key, TooManyEntitiesClient.entityCounts.getOrDefault(key, 0) + current - previous);
            }
            previousRenderedEntities = current;
        } else {
            TooManyEntitiesClient.entityCounts.put(key, TooManyEntitiesClient.entityCounts.getOrDefault(key, 0) + 1);
        }
    }

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;regularEntityCount:I", ordinal = 0))
    private void afterEntityCountReset(CallbackInfo info) {
        TooManyEntitiesClient.entityCounts.clear();
    }
}
