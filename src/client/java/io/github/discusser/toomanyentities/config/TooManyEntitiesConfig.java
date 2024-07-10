package io.github.discusser.toomanyentities.config;

import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntFieldBuilder;
import net.minecraft.text.Text;

@Config(name = TooManyEntitiesClient.MODID)
public class TooManyEntitiesConfig implements ConfigData {
    public Integer maxEntityCount = 0;
    public Boolean applyMaxEntityCountGlobally = true;

    @ConfigEntry.Gui.Excluded
    public static TooManyEntitiesConfig instance = null;

    public static IntFieldBuilder createIntField(ConfigEntryBuilder entryBuilder, String fieldName, Integer field, int defaultValue) {
        return entryBuilder
                .startIntField(Text.translatable("option.too-many-entities." + fieldName), field)
                .setTooltip(Text.translatable("option.too-many-entities." + fieldName + ".tooltip"))
                .setDefaultValue(defaultValue);
    }

    public static BooleanToggleBuilder createBooleanToggle(ConfigEntryBuilder entryBuilder, String fieldName, Boolean field, boolean defaultValue) {
        return entryBuilder
                .startBooleanToggle(Text.translatable("option.too-many-entities." + fieldName), field)
                .setTooltip(Text.translatable("option.too-many-entities." + fieldName + ".tooltip"))
                .setDefaultValue(defaultValue);
    }
}
