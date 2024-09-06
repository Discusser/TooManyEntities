package io.github.discusser.toomanyentities.fabric;

import io.github.discusser.toomanyentities.TooManyEntities;
import io.github.discusser.toomanyentities.client.TooManyEntitiesKeys;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public final class TooManyEntitiesClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TooManyEntities.initClient();

        KeyBindingHelper.registerKeyBinding(TooManyEntitiesKeys.KEY_TOGGLE_MOD);
    }
}
