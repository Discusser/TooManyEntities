package io.github.discusser.toomanyentities.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.discusser.toomanyentities.TooManyEntities;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(WorldRenderer.class)
public class CommonWorldRendererMixin {
    @Unique
    Map<String, Map<Entity, Integer>> too_many_entities$distances = new HashMap<>();
    @Final
    @Shadow
    private MinecraftClient client;

    @Inject(method = "renderEntities", at = @At(value = "HEAD"))
    private void renderEntities(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, Camera camera,
            RenderTickCounter tickCounter, List<Entity> entities, CallbackInfo ci) {
        if (TooManyEntitiesConfig.instance.hideBasedOnDistance) {
            too_many_entities$distances.clear();

            List<Entity> sortedEntities = entities.stream()
                    .sorted((a, b) -> Float.compare(a.distanceTo(client.player), b.distanceTo(client.player))).toList();
            Map<String, Integer> maxDistances = new HashMap<>();
            for (Entity entity : sortedEntities) {
                String key = entity.getType().getTranslationKey();
                if (!too_many_entities$distances.containsKey(key)) {
                    too_many_entities$distances.put(key, new HashMap<>());
                }
                int max = maxDistances.getOrDefault(key, -1);
                too_many_entities$distances.get(key).put(entity, ++max);
                maxDistances.put(key, max);
            }
        }
    }

    @Inject(method = "renderEntity", at = @At(value = "HEAD"), cancellable = true)
    private void beforeEntityRender(CallbackInfo info, @Local(argsOnly = true) Entity entity) {
        String key = entity.getType().getTranslationKey();
        int maxEntityCount = TooManyEntities.getMaxCountForEntity(entity);

        boolean cancelRender = false;
        if (TooManyEntitiesConfig.instance.hideBasedOnDistance) {
            if (too_many_entities$distances.containsKey(key)) {
                cancelRender = too_many_entities$distances.get(key).getOrDefault(entity, 0) >= maxEntityCount;
            }
        } else {
            cancelRender = TooManyEntities.renderedCount.getOrDefault(key, 0) >= maxEntityCount;
        }

        if (TooManyEntities.modEnabled && maxEntityCount > 0 && cancelRender) {
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
