package io.github.discusser.toomanyentities.fabric;

import net.fabricmc.api.ModInitializer;

import io.github.discusser.toomanyentities.ExampleMod;

public final class ExampleModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        ExampleMod.init();
    }
}