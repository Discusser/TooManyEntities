package io.github.discusser.toomanyentities.mixin.client;

import io.github.discusser.toomanyentities.TooManyEntities;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Final
    @Shadow
    private List<Entity> renderedEntities;

    // This mixin exists as a fallback in case EntityCulling is not present
    @Inject(method = "render", at = @At(value = "FIELD", target="Lnet/minecraft/client/render/WorldRenderer;renderedEntitiesCount:I", ordinal = 0, shift = At.Shift.AFTER))
    private void afterEntityCountIncrement(CallbackInfo info) {
        for (Entity entity : this.renderedEntities) {
            String key = entity.getType().getTranslationKey();
            TooManyEntities.toRenderCount.put(key, TooManyEntities.toRenderCount.getOrDefault(key, 0) + 1);
        }
    }
}
