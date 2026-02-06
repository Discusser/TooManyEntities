package io.github.discusser.toomanyentities.client;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class TooManyEntitiesKeys {
    public TooManyEntitiesKeys() {}

    public static final KeyBinding KEY_TOGGLE_MOD = new KeyBinding(
            "key.too_many_entities.toggle_mod",
            InputUtil.Type.KEYSYM,
            InputUtil.GLFW_KEY_O,
            KeyBinding.Category.MISC
    );
}
