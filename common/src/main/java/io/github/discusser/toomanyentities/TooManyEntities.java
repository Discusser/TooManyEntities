package io.github.discusser.toomanyentities;

import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.discusser.toomanyentities.client.TooManyEntitiesKeys;
import io.github.discusser.toomanyentities.config.MapGuiProvider;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class TooManyEntities {
    public static final Logger LOGGER = LoggerFactory.getLogger("too_many_entities");
    public static final String MODID = "too_many_entities";
    public static final HashMap<String, Integer> entityCounts = new HashMap<>();
    public static boolean modEnabled = true;


    public static void initClient() {
        AutoConfig.register(TooManyEntitiesConfig.class, GsonConfigSerializer::new);
        GuiRegistry registry = AutoConfig.getGuiRegistry(TooManyEntitiesConfig.class);
        registry.registerPredicateProvider(new MapGuiProvider(), field -> Map.class.isAssignableFrom(field.getType()));
        TooManyEntitiesConfig.instance = AutoConfig.getConfigHolder(TooManyEntitiesConfig.class).getConfig();

        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (TooManyEntitiesKeys.KEY_TOGGLE_MOD.wasPressed()) {
                modEnabled = !modEnabled;
                if (minecraft.player != null) {
                    String key = "text.too_many_entities.mod_" + (modEnabled ? "enabled" : "disabled");
                    minecraft.player.sendMessage(Text.translatable(key).setStyle(Style.EMPTY.withColor(modEnabled ? Formatting.GREEN : Formatting.RED)));
                }
            }
        });
    }

    @ExpectPlatform
    public static boolean isModPresent(String modid) {
        throw new AssertionError();
    }
}
