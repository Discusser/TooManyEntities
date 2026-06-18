package io.github.discusser.toomanyentities.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class TooManyEntitiesKeys {
    public TooManyEntitiesKeys() {}

    public static final KeyMapping KEY_TOGGLE_MOD = new KeyMapping(
            "key.too_many_entities.toggle_mod",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            KeyMapping.Category.MISC
    );
}
