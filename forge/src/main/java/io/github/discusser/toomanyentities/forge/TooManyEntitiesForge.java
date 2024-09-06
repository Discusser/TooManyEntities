package io.github.discusser.toomanyentities.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.discusser.toomanyentities.TooManyEntities;
import io.github.discusser.toomanyentities.client.TooManyEntitiesKeys;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TooManyEntities.MODID)
@Mod.EventBusSubscriber(modid = TooManyEntities.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class TooManyEntitiesForge {
    public TooManyEntitiesForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::clientSetup);

        EventBuses.registerModEventBus(TooManyEntities.MODID, modEventBus);

        TooManyEntities.init();
    }

    private void clientSetup(FMLClientSetupEvent ignoredEvent) {
        MinecraftForge.registerConfigScreen(screen -> AutoConfig.getConfigScreen(TooManyEntitiesConfig.class, screen).get());

        TooManyEntities.initClient();
    }

    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(TooManyEntitiesKeys.KEY_TOGGLE_MOD);
    }
}
