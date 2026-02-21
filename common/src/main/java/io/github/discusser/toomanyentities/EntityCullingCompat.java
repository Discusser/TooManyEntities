package io.github.discusser.toomanyentities;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.tr7zw.entityculling.versionless.EntityCullingVersionlessBase;

public class EntityCullingCompat {
    @ExpectPlatform
    public static EntityCullingVersionlessBase getInstance() {
        throw new AssertionError();
    }
}
