package io.github.discusser.toomanyentities.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
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
        int maxEntityCount = TooManyEntitiesConfig.instance.applyMaxEntityCountGlobally
                ? TooManyEntitiesConfig.instance.maxEntityCount
                : TooManyEntitiesConfig.instance.entityMaxCounts.get(key);
        if (maxEntityCount > 0 && TooManyEntitiesClient.entityCounts.getOrDefault(key, 0) > maxEntityCount) {
            info.cancel();
        }
    }
}
