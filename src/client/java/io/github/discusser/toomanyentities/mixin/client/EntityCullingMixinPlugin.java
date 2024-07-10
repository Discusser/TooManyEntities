package io.github.discusser.toomanyentities.mixin.client;

import com.google.common.collect.ImmutableMap;
import io.github.discusser.toomanyentities.TooManyEntitiesClient;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class EntityCullingMixinPlugin implements IMixinConfigPlugin {
    private static final Supplier<Boolean> entityCullingLoaded = () -> FabricLoader.getInstance().isModLoaded("entityculling");
    private static final Supplier<Boolean> entityCullingNotLoaded = () -> !FabricLoader.getInstance().isModLoaded("entityculling");

    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.of(
            "io.github.discusser.toomanyentities.mixin.client.DebugHudMixin", entityCullingLoaded,
            "io.github.discusser.toomanyentities.mixin.client.EntityCullingWorldRendererMixin", entityCullingLoaded,
            "io.github.discusser.toomanyentities.mixin.client.WorldRendererMixin", entityCullingNotLoaded
    );

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return CONDITIONS.getOrDefault(mixinClassName, () -> true).get();
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
