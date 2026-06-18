package io.github.discusser.toomanyentities.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tr7zw.entityculling.EntityCullingModBase;
import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class EntityCullingWorldRendererMixin {
    @Unique
    private int too_many_entities$previousRenderedEntities = 0;

    @Inject(method = "submitEntities", at = @At("HEAD"))
    private void afterEntityCountIncrement(PoseStack poseStack, LevelRenderState renderStates,
            SubmitNodeCollector output, CallbackInfo ci) {
        int previous = too_many_entities$previousRenderedEntities;
        int current = EntityCullingModBase.instance.renderedEntities;
        int toRenderCount = current - previous;
        int passes = 0;

        for (EntityRenderState renderState : renderStates.entityRenderStates) {
            String key = renderState.entityType.getDescriptionId();
            if (TooManyEntitiesConfig.instance.useEntityCulling) {
                if (passes < toRenderCount) {
                    TooManyEntitiesClient.toRenderCount.put(key,
                            TooManyEntitiesClient.toRenderCount.getOrDefault(key, 0) + 1);
                    passes++;
                }
            } else {
                TooManyEntitiesClient.toRenderCount.put(key,
                        TooManyEntitiesClient.toRenderCount.getOrDefault(key, 0) + 1);
            }
        }
        too_many_entities$previousRenderedEntities = current;
    }
}
