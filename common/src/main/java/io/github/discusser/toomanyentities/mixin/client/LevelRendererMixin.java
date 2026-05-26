package io.github.discusser.toomanyentities.mixin.client;

import io.github.discusser.toomanyentities.TooManyEntities;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Final
    @Shadow
    private LevelRenderState levelRenderState;

    // Fallback when Entity Culling is not present
    @Inject(method = "extractVisibleEntities", at = @At("TAIL"))
    private void afterEntityCountIncrement(
            net.minecraft.client.Camera camera,
            Frustum frustum,
            net.minecraft.client.DeltaTracker deltaTracker,
            LevelRenderState output,
            CallbackInfo info
    ) {
        for (EntityRenderState state : output.entityRenderStates) {
            String key = state.entityType.getDescriptionId();
            TooManyEntities.toRenderCount.put(key, TooManyEntities.toRenderCount.getOrDefault(key, 0) + 1);
        }
    }
}
