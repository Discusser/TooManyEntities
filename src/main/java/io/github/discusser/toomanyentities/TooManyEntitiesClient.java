package io.github.discusser.toomanyentities;

import io.github.discusser.toomanyentities.client.TooManyEntitiesKeys;
import io.github.discusser.toomanyentities.config.MapGuiProvider;
import io.github.discusser.toomanyentities.config.TooManyEntitiesConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class TooManyEntitiesClient implements ClientModInitializer {
    public static final String MODID = "too_many_entities";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final HashMap<String, Integer> toRenderCount = new HashMap<>();
    public static final HashMap<String, Integer> renderedCount = new HashMap<>();
    public static boolean modEnabled = true;

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

    @Override
    public void onInitializeClient() {
        AutoConfig.register(TooManyEntitiesConfig.class, GsonConfigSerializer::new);
        GuiRegistry registry = AutoConfigClient.getGuiRegistry(TooManyEntitiesConfig.class);
        registry.registerPredicateProvider(new MapGuiProvider(), field -> Map.class.isAssignableFrom(field.getType()));
        TooManyEntitiesConfig.instance = AutoConfig.getConfigHolder(TooManyEntitiesConfig.class).getConfig();
        TooManyEntitiesConfig.populateEntityMaxCounts();

        KeyMapping toggleModKey = KeyMappingHelper.registerKeyMapping(TooManyEntitiesKeys.KEY_TOGGLE_MOD);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleModKey.consumeClick()) {
                modEnabled = !modEnabled;
                if (client.player != null) {
                    String key = "text.too_many_entities.mod_" + (modEnabled ? "enabled" : "disabled");
                    client.player.sendSystemMessage(Component.translatable(key)
                            .setStyle(Style.EMPTY.withColor(modEnabled ? ChatFormatting.GREEN : ChatFormatting.RED)));
                }
            }
        });
    }
}
