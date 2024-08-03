package io.github.discusser.toomanyentities.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class TooManyEntitiesImpl {
    public static boolean isModPresent(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}
