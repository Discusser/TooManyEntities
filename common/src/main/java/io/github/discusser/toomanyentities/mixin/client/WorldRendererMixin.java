package io.github.discusser.toomanyentities.mixin.client;

import io.github.discusser.toomanyentities.TooManyEntities;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.WorldRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Final
    @Shadow
    private WorldRenderState worldRenderState;

    // This mixin exists as a fallback in case EntityCulling is not present
    @Inject(method = "fillEntityRenderStates", at = @At("TAIL"))
    private void afterEntityCountIncrement(CallbackInfo info) {
        for (EntityRenderState state : worldRenderState.entityRenderStates) {
            if (state.entityType == null) continue;

            String key = state.entityType.getTranslationKey();
            TooManyEntities.toRenderCount.put(key, TooManyEntities.toRenderCount.getOrDefault(key, 0) + 1);
        }
    }
}
