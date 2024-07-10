package io.github.discusser.toomanyentities;

import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TooManyEntitiesClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("too-many-entities");
	public static final String MODID = "too-many-entities";

	@Override
	public void onInitializeClient() {
		AutoConfig.register(TooManyEntitiesConfig.class, GsonConfigSerializer::new);
		TooManyEntitiesConfig.instance = AutoConfig.getConfigHolder(TooManyEntitiesConfig.class).getConfig();
	}
}