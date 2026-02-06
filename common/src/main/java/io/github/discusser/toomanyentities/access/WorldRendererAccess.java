package io.github.discusser.toomanyentities.access;

import net.minecraft.client.render.entity.state.EntityRenderState;

import java.util.Map;

public interface WorldRendererAccess {
    Map<String, Map<EntityRenderState, Integer>> too_many_entities$distances();
}
