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
public class CommonWorldRendererMixin {
    @Inject(method = "renderEntity", at = @At(value = "HEAD"), cancellable = true)
    private void beforeEntityRender(CallbackInfo info, @Local(argsOnly = true) Entity entity) {
        String key = entity.getType().getTranslationKey();
        int maxEntityCount = TooManyEntities.getMaxCountForEntity(entity);
        if (TooManyEntities.modEnabled && maxEntityCount > 0 &&
                TooManyEntities.renderedCount.getOrDefault(key, 0) >= maxEntityCount) {
            info.cancel();
        } else {
            TooManyEntities.renderedCount.put(key, TooManyEntities.renderedCount.getOrDefault(key, 0) + 1);
        }
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    private void afterEntityCountReset(CallbackInfo info) {
        TooManyEntities.toRenderCount.clear();
        TooManyEntities.renderedCount.clear();
    }
}
