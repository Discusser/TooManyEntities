package io.github.discusser.toomanyentities;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.discusser.toomanyentities.config.MapGuiProvider;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class TooManyEntities {
    public static final Logger LOGGER = LoggerFactory.getLogger("too_many_entities");
    public static final String MODID = "too_many_entities";
    public static final HashMap<String, Integer> entityCounts = new HashMap<>();

    public static void init() {
    }

    public static void initClient() {
        AutoConfig.register(TooManyEntitiesConfig.class, GsonConfigSerializer::new);
        GuiRegistry registry = AutoConfig.getGuiRegistry(TooManyEntitiesConfig.class);
        registry.registerPredicateProvider(new MapGuiProvider(), field -> Map.class.isAssignableFrom(field.getType()));
        TooManyEntitiesConfig.instance = AutoConfig.getConfigHolder(TooManyEntitiesConfig.class).getConfig();
    }

    @ExpectPlatform
    public static boolean isModPresent(String modid) {
        throw new AssertionError();
    }
}
