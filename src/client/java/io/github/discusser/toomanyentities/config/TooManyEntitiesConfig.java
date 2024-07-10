package io.github.discusser.toomanyentities.config;

import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Config(name = TooManyEntitiesClient.MODID)
public class TooManyEntitiesConfig implements ConfigData {


    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Integer maxEntityCount = 32;
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Boolean applyMaxEntityCountGlobally = true;

    @ConfigEntry.Category(value = "entities")
    public Map<String, Integer> entityMaxCounts = new TreeMap<>();

    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Excluded
    public static TooManyEntitiesConfig instance = null;

    public TooManyEntitiesConfig() {
        Registries.ENTITY_TYPE.stream().forEach(entityType -> entityMaxCounts.put(entityType.getTranslationKey(), 0));
    }

    @Override
    public void validatePostLoad() {
        if (maxEntityCount < 0) {
            maxEntityCount = 0;
        }

        for (Map.Entry<String, Integer> entry : entityMaxCounts.entrySet()) {
            if (entry.getValue() < 0) {
                entry.setValue(0);
            }
        }
    }
}
