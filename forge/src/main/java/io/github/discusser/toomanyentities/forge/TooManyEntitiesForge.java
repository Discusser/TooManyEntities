package io.github.discusser.toomanyentities.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.discusser.toomanyentities.forge.config.MapGuiProvider;
import io.github.discusser.toomanyentities.forge.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import io.github.discusser.toomanyentities.TooManyEntities;
import net.minecraftforge.fml.loading.LoadingModList;

import java.util.Map;

@Mod(TooManyEntities.MODID)
@Mod.EventBusSubscriber(modid = TooManyEntities.MODID)
public final class TooManyEntitiesForge {
    public TooManyEntitiesForge() {
        EventBuses.registerModEventBus(TooManyEntities.MODID, FMLJavaModLoadingContext.get().getModEventBus());

        TooManyEntities.init();
    }

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent ignoredEvent) {
        AutoConfig.register(TooManyEntitiesConfig.class, GsonConfigSerializer::new);
        GuiRegistry registry = AutoConfig.getGuiRegistry(TooManyEntitiesConfig.class);
        registry.registerPredicateProvider(new MapGuiProvider(), field -> Map.class.isAssignableFrom(field.getType()));
        TooManyEntitiesConfig.instance = AutoConfig.getConfigHolder(TooManyEntitiesConfig.class).getConfig();

        TooManyEntities.initClient();
    }
}
