package io.github.discusser.toomanyentities.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import io.github.discusser.toomanyentities.access.WorldRendererAccess;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(LevelRenderer.class)
public class CommonWorldRendererMixin implements WorldRendererAccess {
    @Unique
    Map<String, Map<EntityRenderState, Integer>> too_many_entities$distances = new HashMap<>();

    @Inject(method = "submitEntities", at = @At(value = "HEAD"))
    private void renderEntities(PoseStack poseStack, LevelRenderState renderStates, SubmitNodeCollector output,
            CallbackInfo ci) {
        if (TooManyEntitiesConfig.instance.hideBasedOnDistance) {
            too_many_entities$distances.clear();

            List<EntityRenderState> sortedEntities = renderStates.entityRenderStates.stream()
                    .sorted(Comparator.comparingDouble(state -> state.distanceToCameraSq)).toList();
            Map<String, Integer> maxDistances = new HashMap<>();
            for (EntityRenderState entity : sortedEntities) {
                String key = entity.entityType.getDescriptionId();
                if (!too_many_entities$distances.containsKey(key)) {
                    too_many_entities$distances.put(key, new HashMap<>());
                }
                int max = maxDistances.getOrDefault(key, -1);
                too_many_entities$distances.get(key).put(entity, ++max);
                maxDistances.put(key, max);
            }
        }
    }

    @Inject(method = "extractLevel", at = @At(value = "TAIL"))
    private void afterEntityCountReset(DeltaTracker deltaTracker,
            Camera camera, float deltaPartialTick, CallbackInfo info) {
        TooManyEntitiesClient.toRenderCount.clear();
        TooManyEntitiesClient.renderedCount.clear();
    }

    @Override
    public Map<String, Map<EntityRenderState, Integer>> too_many_entities$distances() {
        return this.too_many_entities$distances;
    }
}
