package io.github.discusser.toomanyentities.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfigClient;

public class TooManyEntitiesModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfigClient.getConfigScreen(TooManyEntitiesConfig.class, parent).get();
    }
}
