package io.github.discusser.toomanyentities;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfigScreen;

public class TooManyEntitiesModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return TooManyEntitiesConfigScreen::new;
    }
}
