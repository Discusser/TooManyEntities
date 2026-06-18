package io.github.discusser.toomanyentities.config;

import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

@Config(name = TooManyEntitiesClient.MODID)
public class TooManyEntitiesConfig implements ConfigData {
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Excluded
    public static TooManyEntitiesConfig instance = null;
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Integer maxEntityCount = 64;
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Integer maxHostileCount = 64;
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Integer maxPassiveCount = 64;
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Boolean applyMaxEntityCount = false;
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Boolean applyMaxHostileCount = false;
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Boolean applyMaxPassiveCount = false;
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Boolean useEntityCulling = false;
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Boolean hideBasedOnDistance = true;
    @ConfigEntry.Category(value = "entities")
    public TreeMap<String, Integer> entityMaxCounts = new TreeMap<>(Comparator.naturalOrder());

    public TooManyEntitiesConfig() {
    }

    public static void populateEntityMaxCounts() {
        BuiltInRegistries.ENTITY_TYPE.stream().forEach(
                entityType -> {
                    String key = entityType.getDescriptionId();
                    if (!TooManyEntitiesConfig.instance.entityMaxCounts.containsKey(key)) {
                        TooManyEntitiesConfig.instance.entityMaxCounts.put(key, 0);
                    }
                });
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
