package io.github.discusser.toomanyentities.mixin.client;

import io.github.discusser.toomanyentities.TooManyEntities;
import io.github.discusser.toomanyentities.access.WorldRendererAccess;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(WorldRenderer.class)
public class CommonWorldRendererMixin implements WorldRendererAccess {
    @Unique
    Map<String, Map<EntityRenderState, Integer>> too_many_entities$distances = new HashMap<>();

    @Inject(method = "pushEntityRenders", at = @At(value = "HEAD"))
    private void renderEntities(MatrixStack matrices, WorldRenderState renderStates, OrderedRenderCommandQueue queue,
            CallbackInfo ci) {
        if (TooManyEntitiesConfig.instance.hideBasedOnDistance) {
            too_many_entities$distances.clear();

            List<EntityRenderState> sortedEntities = renderStates.entityRenderStates.stream()
                    .sorted(Comparator.comparingDouble(state -> state.squaredDistanceToCamera)).toList();
            Map<String, Integer> maxDistances = new HashMap<>();
            for (EntityRenderState entity : sortedEntities) {
                String key = entity.entityType.getTranslationKey();
                if (!too_many_entities$distances.containsKey(key)) {
                    too_many_entities$distances.put(key, new HashMap<>());
                }
                int max = maxDistances.getOrDefault(key, -1);
                too_many_entities$distances.get(key).put(entity, ++max);
                maxDistances.put(key, max);
            }
        }
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    private void afterEntityCountReset(CallbackInfo info) {
        TooManyEntities.toRenderCount.clear();
        TooManyEntities.renderedCount.clear();
    }

    @Override
    public Map<String, Map<EntityRenderState, Integer>> too_many_entities$distances() {
        return this.too_many_entities$distances;
    }
}
