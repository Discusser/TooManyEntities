package io.github.discusser.toomanyentities.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Unique
    private final HashMap<String, Integer> entityCounts = new HashMap<>();

    @Inject(method = "renderEntity", at = @At(value = "HEAD"), cancellable = true)
    private void beforeEntityRender(CallbackInfo info, @Local(argsOnly = true) Entity entity) {
        String key = entity.getType().getTranslationKey();
        int maxEntityCount = TooManyEntitiesConfig.instance.applyMaxEntityCountGlobally
                ? TooManyEntitiesConfig.instance.maxEntityCount
                : TooManyEntitiesConfig.instance.entityMaxCounts.get(key);
        if (maxEntityCount > 0 && entityCounts.getOrDefault(key, 0) > maxEntityCount) {
            info.cancel();
        }
    }

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;regularEntityCount:I", ordinal = 1, shift = At.Shift.AFTER))
    private void afterEntityCountIncrement(CallbackInfo info, @Local Entity entity) {
        String key = entity.getType().getTranslationKey();
        entityCounts.put(key, entityCounts.getOrDefault(key, 0) + 1);
    }


    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;regularEntityCount:I", ordinal = 0))
    private void afterEntityCountReset(CallbackInfo info) {
        entityCounts.clear();
    }
}
