package io.github.discusser.toomanyentities.neoforge;


import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class ModCheckerImpl {
    public static boolean isModPresent(String modid) {
        return ModList.get() == null ? FMLLoader.getLoadingModList().getModFileById(modid) != null :
               ModList.get().isLoaded(modid);
    }
}
