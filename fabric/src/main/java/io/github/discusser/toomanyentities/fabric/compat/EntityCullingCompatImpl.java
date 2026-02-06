package io.github.discusser.toomanyentities.fabric.compat;

import net.fabricmc.loader.api.FabricLoader;

public class EntityCullingCompatImpl {
    public static boolean isModPresent(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}
