package io.github.discusser.toomanyentities.config;

import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.registry.Registries;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

@Config(name = TooManyEntitiesClient.MODID)
public class TooManyEntitiesConfig implements ConfigData {
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Integer maxEntityCount = 64;
    @ConfigEntry.Category(value = "general")
    @ConfigEntry.Gui.Tooltip()
    public Boolean applyMaxEntityCountGlobally = true;

    @ConfigEntry.Category(value = "entities")
    public TreeMap<String, Integer> entityMaxCounts = new TreeMap<>(Comparator.naturalOrder());

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
