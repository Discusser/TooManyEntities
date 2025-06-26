package io.github.discusser.toomanyentities;

import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.discusser.toomanyentities.client.TooManyEntitiesKeys;
import io.github.discusser.toomanyentities.config.MapGuiProvider;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class TooManyEntities {
    public static final String MODID = "too_many_entities";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final HashMap<String, Integer> toRenderCount = new HashMap<>();
    public static final HashMap<String, Integer> renderedCount = new HashMap<>();
    public static boolean modEnabled = true;

    public static void initClient() {
        AutoConfig.register(TooManyEntitiesConfig.class, GsonConfigSerializer::new);
        GuiRegistry registry = AutoConfig.getGuiRegistry(TooManyEntitiesConfig.class);
        registry.registerPredicateProvider(new MapGuiProvider(), field -> Map.class.isAssignableFrom(field.getType()));
        TooManyEntitiesConfig.instance = AutoConfig.getConfigHolder(TooManyEntitiesConfig.class).getConfig();
        TooManyEntitiesConfig.populateEntityMaxCounts();

        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (TooManyEntitiesKeys.KEY_TOGGLE_MOD.wasPressed()) {
                modEnabled = !modEnabled;
                if (minecraft.player != null) {
                    String key = "text.too_many_entities.mod_" + (modEnabled ? "enabled" : "disabled");
                    minecraft.player.sendMessage(Text.translatable(key)
                            .setStyle(Style.EMPTY.withColor(modEnabled ? Formatting.GREEN : Formatting.RED)), false);
                }
            }
        });
    }

    @ExpectPlatform
    public static boolean isModPresent(String modid) {
        throw new AssertionError();
    }

    public static int getMaxCountForEntity(Entity entity) {
        TooManyEntitiesConfig cfg = TooManyEntitiesConfig.instance;
        String key = entity.getType().getTranslationKey();
        int maxCount = cfg.entityMaxCounts.getOrDefault(key, 0);
        if (maxCount != 0) {
            return maxCount;
        }
        if (entity instanceof PassiveEntity && cfg.applyMaxPassiveCount) {
            return cfg.maxPassiveCount;
        } else if (entity instanceof HostileEntity && cfg.applyMaxHostileCount) {
            return cfg.maxHostileCount;
        } else if (cfg.applyMaxEntityCount) {
            return cfg.maxEntityCount;
        }

        return 0;
    }
}
