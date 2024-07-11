package io.github.discusser.toomanyentities;

import io.github.discusser.toomanyentities.config.MapGuiProvider;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TooManyEntitiesClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("too-many-entities");
	public static final String MODID = "too-many-entities";
	public static final HashMap<String, Integer> entityCounts = new HashMap<>();

	@Override
	public void onInitializeClient() {
		// Register our config class to AutoConfig
		AutoConfig.register(TooManyEntitiesConfig.class, GsonConfigSerializer::new);
		// Register our custom GUI providers and transformers
		GuiRegistry registry = AutoConfig.getGuiRegistry(TooManyEntitiesConfig.class);
		registry.registerPredicateProvider(new MapGuiProvider(), field -> Map.class.isAssignableFrom(field.getType()));

		TooManyEntitiesConfig.instance = AutoConfig.getConfigHolder(TooManyEntitiesConfig.class).getConfig();
	}
}