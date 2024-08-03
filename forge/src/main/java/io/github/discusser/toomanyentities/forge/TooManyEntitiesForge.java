package io.github.discusser.toomanyentities.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import io.github.discusser.toomanyentities.TooManyEntities;

@Mod(TooManyEntities.MODID)
public final class TooManyEntitiesForge {
    public TooManyEntitiesForge() {
        EventBuses.registerModEventBus(TooManyEntities.MODID, FMLJavaModLoadingContext.get().getModEventBus());

        TooManyEntities.init();
    }
}
