package io.github.discusser.toomanyentities.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
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
    @Inject(method = "submitEntities", at = @At("TAIL"))
    private void afterEntityCountIncrement(PoseStack poseStack, LevelRenderState levelRenderState,
            SubmitNodeCollector output, CallbackInfo ci) {
        for (EntityRenderState state : levelRenderState.entityRenderStates) {
            String key = state.entityType.getDescriptionId();
            TooManyEntitiesClient.toRenderCount.put(key, TooManyEntitiesClient.toRenderCount.getOrDefault(key, 0) + 1);
        }
    }
}
