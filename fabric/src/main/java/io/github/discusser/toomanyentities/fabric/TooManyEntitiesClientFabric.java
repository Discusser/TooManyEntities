package io.github.discusser.toomanyentities.fabric;

import io.github.discusser.toomanyentities.TooManyEntities;
import io.github.discusser.toomanyentities.fabric.config.MapGuiProvider;
import io.github.discusser.toomanyentities.fabric.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

import java.util.Map;

public final class TooManyEntitiesClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AutoConfig.register(TooManyEntitiesConfig.class, GsonConfigSerializer::new);
        GuiRegistry registry = AutoConfig.getGuiRegistry(TooManyEntitiesConfig.class);
        registry.registerPredicateProvider(new MapGuiProvider(), field -> Map.class.isAssignableFrom(field.getType()));
        TooManyEntitiesConfig.instance = AutoConfig.getConfigHolder(TooManyEntitiesConfig.class).getConfig();

        TooManyEntities.initClient();
    }
}
