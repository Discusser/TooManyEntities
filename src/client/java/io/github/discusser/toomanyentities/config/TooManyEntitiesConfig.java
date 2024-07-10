package io.github.discusser.toomanyentities.config;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntFieldBuilder;
import net.minecraft.text.Text;

public class TooManyEntitiesConfig {
    public static Integer maxEntityCount = 0;
    public static Boolean applyMaxEntityCountGlobally = true;

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
