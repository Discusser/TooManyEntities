package io.github.discusser.toomanyentities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public final class TooManyEntities {
    public static final Logger LOGGER = LoggerFactory.getLogger("too-many-entities");
    public static final String MODID = "too-many-entities";
    public static final HashMap<String, Integer> entityCounts = new HashMap<>();

    public static void init() {
    }

    public static void initClient() {

    }
}
