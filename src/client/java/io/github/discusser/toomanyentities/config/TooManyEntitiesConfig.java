package io.github.discusser.toomanyentities.config;

import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = TooManyEntitiesClient.MODID)
public class TooManyEntitiesConfig implements ConfigData {
    public Integer maxEntityCount = 0;
    public Boolean applyMaxEntityCountGlobally = true;

    @ConfigEntry.Gui.Excluded
    public static TooManyEntitiesConfig instance = null;
}
