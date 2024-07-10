package io.github.discusser.toomanyentities.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
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
    private final HashMap<Class<?>, Integer> entityCounts = new HashMap<>();

    @Inject(method = "renderEntity", at = @At(value = "HEAD"), cancellable = true)
    private void beforeEntityRender(CallbackInfo info, @Local(argsOnly = true) Entity entity) {
        int MAX_ENTITY_COUNT = 128;
        if (entityCounts.getOrDefault(entity.getClass(), 0) > MAX_ENTITY_COUNT) {
            info.cancel();
        }
    }

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;regularEntityCount:I", ordinal = 1, shift = At.Shift.AFTER))
    private void afterEntityCountIncrement(CallbackInfo info, @Local Entity entity) {
        Class<? extends Entity> klass = entity.getClass();
        entityCounts.put(klass, entityCounts.getOrDefault(klass, 0) + 1);
    }


    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;regularEntityCount:I", ordinal = 0))
    private void afterEntityCountReset(CallbackInfo info) {
        entityCounts.clear();
    }
}
