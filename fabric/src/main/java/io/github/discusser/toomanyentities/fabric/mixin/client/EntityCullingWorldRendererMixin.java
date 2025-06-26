package io.github.discusser.toomanyentities.fabric.mixin.client;

import dev.tr7zw.entityculling.EntityCullingModBase;
import io.github.discusser.toomanyentities.TooManyEntities;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(WorldRenderer.class)
public class EntityCullingWorldRendererMixin {
    @Unique
    private int too_many_entities$previousRenderedEntities = 0;
    @Final
    @Shadow
    private List<Entity> renderedEntities;

    @Inject(method = "render",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;renderedEntitiesCount:I",
                     ordinal = 0, shift = At.Shift.AFTER))
    private void afterEntityCountIncrement(CallbackInfo info) {
        int previous = too_many_entities$previousRenderedEntities;
        int current = EntityCullingModBase.instance.renderedEntities;
        int toRenderCount = current - previous;
        int passes = 0;

        for (Entity entity : renderedEntities) {
            String key = entity.getType().getTranslationKey();
            if (TooManyEntitiesConfig.instance.useEntityCulling) {
                if (passes < toRenderCount) {
                    TooManyEntities.toRenderCount.put(key, TooManyEntities.toRenderCount.getOrDefault(key, 0) + 1);
                    passes++;
                }
            } else {
                TooManyEntities.toRenderCount.put(key, TooManyEntities.toRenderCount.getOrDefault(key, 0) + 1);
            }
        }
        too_many_entities$previousRenderedEntities = current;
    }
}
