package io.github.discusser.toomanyentities.fabric;

import io.github.discusser.toomanyentities.TooManyEntities;
import net.fabricmc.api.ClientModInitializer;

public final class TooManyEntitiesClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TooManyEntities.initClient();
    }
}
