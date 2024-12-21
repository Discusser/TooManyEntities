package io.github.discusser.toomanyentities.forge;

import io.github.discusser.toomanyentities.TooManyEntities;
import io.github.discusser.toomanyentities.client.TooManyEntitiesKeys;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(TooManyEntities.MODID)
public final class TooManyEntitiesForge {
    public TooManyEntitiesForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerBindings);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, screen) -> AutoConfig.getConfigScreen(TooManyEntitiesConfig.class, screen).get()
                )
        );
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        TooManyEntities.initClient();
        ForgeRegistries.ENTITY_TYPES.forEach(entityType -> TooManyEntitiesConfig.instance.entityMaxCounts.put(entityType.getTranslationKey(), 0));
    }

    private void clientSetup(FMLClientSetupEvent ignoredEvent) {
//        TooManyEntities.initClient();
    }

    private void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(TooManyEntitiesKeys.KEY_TOGGLE_MOD);
    }
}
