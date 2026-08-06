package io.github.discusser.toomanyentities.mixin.client;

import io.github.discusser.toomanyentities.TooManyEntities;
import io.github.discusser.toomanyentities.EntityCullingCompat;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class EntityCullingWorldRendererMixin {
    @Unique
    private int too_many_entities$previousRenderedEntities = 0;

    @Inject(method = "pushEntityRenders", at = @At("HEAD"))
    private void afterEntityCountIncrement(MatrixStack matrices, WorldRenderState renderStates,
            OrderedRenderCommandQueue queue, CallbackInfo ci) {
        int previous = too_many_entities$previousRenderedEntities;
        int current = EntityCullingCompat.getInstance().renderedEntities;
        int toRenderCount = current - previous;
        int passes = 0;

        for (EntityRenderState renderState : renderStates.entityRenderStates) {
            if (renderState.entityType == null) continue;

            String key = renderState.entityType.getTranslationKey();
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
