package io.github.discusser.toomanyentities;

import dev.architectury.event.events.client.ClientTickEvent;
import io.github.discusser.toomanyentities.client.TooManyEntitiesKeys;
import io.github.discusser.toomanyentities.config.MapGuiProvider;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
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
        GuiRegistry registry = AutoConfigClient.getGuiRegistry(TooManyEntitiesConfig.class);
        registry.registerPredicateProvider(new MapGuiProvider(), field -> Map.class.isAssignableFrom(field.getType()));
        TooManyEntitiesConfig.instance = AutoConfig.getConfigHolder(TooManyEntitiesConfig.class).getConfig();
        TooManyEntitiesConfig.populateEntityMaxCounts();

        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (TooManyEntitiesKeys.KEY_TOGGLE_MOD.consumeClick()) {
                modEnabled = !modEnabled;
                if (minecraft.player != null) {
                    String key = "text.too_many_entities.mod_" + (modEnabled ? "enabled" : "disabled");
                    minecraft.player.sendSystemMessage(Component.translatable(key)
                            .withStyle(Style.EMPTY.withColor(modEnabled ? ChatFormatting.GREEN : ChatFormatting.RED)));
                }
            }
        });
    }

    public static int getMaxCountForEntity(EntityType<?> type) {
        TooManyEntitiesConfig cfg = TooManyEntitiesConfig.instance;
        String key = type.getDescriptionId();
        int maxCount = cfg.entityMaxCounts.getOrDefault(key, 0);
        if (maxCount != 0) {
            return maxCount;
        }

        boolean isPassive = type.getCategory() != MobCategory.MONSTER;
        if (isPassive && cfg.applyMaxPassiveCount) {
            return cfg.maxPassiveCount;
        } else if (!isPassive && cfg.applyMaxHostileCount) {
            return cfg.maxHostileCount;
        } else if (cfg.applyMaxEntityCount) {
            return cfg.maxEntityCount;
        }

        return 0;
    }
}
