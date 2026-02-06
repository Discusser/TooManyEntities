package io.github.discusser.toomanyentities.compat.fabric;

import dev.tr7zw.entityculling.EntityCullingModBase;
import dev.tr7zw.entityculling.versionless.EntityCullingVersionlessBase;

public class EntityCullingCompatImpl {
    public static EntityCullingVersionlessBase getInstance() {
        return EntityCullingModBase.instance;
    }
}
