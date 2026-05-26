package io.github.discusser.toomanyentities.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class TooManyEntitiesKeys {
    public TooManyEntitiesKeys() {}

    public static final KeyMapping KEY_TOGGLE_MOD = new KeyMapping(
            "key.too_many_entities.toggle_mod",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_O,
            KeyMapping.Category.MISC
    );
}
