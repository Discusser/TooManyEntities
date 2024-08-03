package io.github.discusser.toomanyentities.forge;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.LoadingModList;

public class TooManyEntitiesImpl {
    public static boolean isModPresent(String modid) {
        return ModList.get() == null
                ? LoadingModList.get().getModFileById(modid) != null
                : ModList.get().isLoaded(modid);
    }
}
