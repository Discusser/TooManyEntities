package io.github.discusser.toomanyentities;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class ModChecker {
    @ExpectPlatform
    public static boolean isModPresent(String modid) {
        throw new AssertionError();
    }
}
