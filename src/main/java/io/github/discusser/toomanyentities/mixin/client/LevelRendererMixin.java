package io.github.discusser.toomanyentities.mixin.client;

import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
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

    // This mixin exists as a fallback in case EntityCulling is not present
    @Inject(method = "extractVisibleEntities", at = @At("TAIL"))
    private void afterEntityCountIncrement(Camera camera, Frustum frustum, DeltaTracker deltaTracker,
            LevelRenderState output, CallbackInfo info) {
        for (EntityRenderState state : levelRenderState.entityRenderStates) {
            //noinspection ConstantValue
            if (state.entityType == null) continue;

            String key = state.entityType.getDescriptionId();
            TooManyEntitiesClient.toRenderCount.put(key, TooManyEntitiesClient.toRenderCount.getOrDefault(key, 0) + 1);
        }
    }
}
