package io.github.discusser.toomanyentities.forge.config;

import io.github.discusser.toomanyentities.TooManyEntities;
import me.shedaniel.autoconfig.gui.registry.api.GuiProvider;
import me.shedaniel.autoconfig.gui.registry.api.GuiRegistryAccess;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MapGuiProvider implements GuiProvider {
    private static final ConfigEntryBuilder ENTRY_BUILDER = ConfigEntryBuilder.create();

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<AbstractConfigListEntry> get(String i18n, Field field, Object config, Object defaults, GuiRegistryAccess registry) {
        try {
            Map<Object, Object> map = Utils.getUnsafely(field, config);
            if (map == null) {
                map = (Map<Object, Object>) field.getType().getDeclaredConstructor().newInstance();
            }
            Map<Object, Object> defaultMap = Utils.getUnsafely(field, defaults);
            List<AbstractConfigListEntry> entries = new ArrayList<>();

            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                if (!(entry.getValue() instanceof Integer)) {
                    TooManyEntities.LOGGER.error("No GUI provider registered for field '{}'! Only Integer values are supported.", field);
                    continue;
                }

                entries.add(ENTRY_BUILDER
                        .startIntField(Text.translatable((String) entry.getKey()), (Integer) entry.getValue())
                        .setDefaultValue(() -> (Integer) defaultMap.get(entry.getKey()))
                        .setSaveConsumer(entry::setValue)
                        .build());
            }

            return entries;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            TooManyEntities.LOGGER.error("Field '{}' was not found in config object and an instance of type '{}' could not be cast to Map<Object, Object>", field, field.getType());
            return Collections.emptyList();
        }
    }
}
