package io.github.discusser.toomanyentities.neoforge;

import io.github.discusser.toomanyentities.TooManyEntities;
import io.github.discusser.toomanyentities.client.TooManyEntitiesKeys;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(TooManyEntities.MODID)
public final class TooManyEntitiesForge {
    public TooManyEntitiesForge(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerBindings);

//        EventBuses.registerModEventBus(TooManyEntities.MODID, modEventBus);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (client, screen) -> AutoConfig.getConfigScreen(TooManyEntitiesConfig.class, screen).get());

        TooManyEntities.init();
    }

    private void clientSetup(FMLClientSetupEvent ignoredEvent) {
        TooManyEntities.initClient();
    }

    private void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(TooManyEntitiesKeys.KEY_TOGGLE_MOD);
    }
}
