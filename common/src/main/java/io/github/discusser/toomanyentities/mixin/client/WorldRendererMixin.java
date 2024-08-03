package io.github.discusser.toomanyentities.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.discusser.toomanyentities.TooManyEntities;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;regularEntityCount:I", ordinal = 1, shift = At.Shift.AFTER))
    private void afterEntityCountIncrement(CallbackInfo info, @Local Entity entity) {
        String key = entity.getType().getTranslationKey();
        TooManyEntities.entityCounts.put(key, TooManyEntities.entityCounts.getOrDefault(key, 0) + 1);
    }
}
