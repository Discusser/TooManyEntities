package io.github.discusser.toomanyentities.config;

import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class TooManyEntitiesConfigScreen extends Screen {
    private final Screen parent;

    public TooManyEntitiesConfigScreen(final Screen parent) {
        super(Text.translatable("config.too-many-entities.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.translatable("config.too-many-entities.title"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.too-many-entities.general"));
        general.addEntry(TooManyEntitiesConfig.createIntField(
                        entryBuilder,
                        "maxEntityCount",
                        TooManyEntitiesConfig.instance.maxEntityCount,
                        0)
                .setSaveConsumer(value -> TooManyEntitiesConfig.instance.maxEntityCount = value)
                .setMin(0)
                .build());
        general.addEntry(TooManyEntitiesConfig.createBooleanToggle(
                        entryBuilder,
                        "applyMaxEntityCountGlobally",
                        TooManyEntitiesConfig.instance.applyMaxEntityCountGlobally,
                        true)
                .setSaveConsumer(value -> TooManyEntitiesConfig.instance.applyMaxEntityCountGlobally = value)
                .build());
//        general.addEntry(entryBuilder
//                .startIntField(Text.translatable("option.too-many-entities.maxEntityCount"), TooManyEntitiesConfig.maxEntityCount)
//                .setDefaultValue(0)
//                .setTooltip(Text.translatable("option.too-many-entities.maxEntityCount.tooltip"))
//                .setMin(0)
//                .setSaveConsumer(value -> TooManyEntitiesConfig.maxEntityCount = value)
//                .build());
//        general.addEntry(entryBuilder
//                .startBooleanToggle(Text.translatable("option.too-many-entities.applyMaxEntityCountGlobally"), TooManyEntitiesConfig.applyMaxEntityCountGlobally)
//                .setDefaultValue(true)
//                .setTooltip(Text.translatable("option.too-many-entities.applyMaxEntityCountGlobally.tooltip"))
//                .setSaveConsumer(value -> TooManyEntitiesConfig.applyMaxEntityCountGlobally = value)
//                .build());

        ConfigCategory entities = builder.getOrCreateCategory(Text.translatable("category.too-many-entities.entities"));

        builder.setSavingRunnable(() -> {
            // TODO: implement me
        });

        Screen screen = builder.build();
        MinecraftClient.getInstance().setScreen(screen);
    }
}
