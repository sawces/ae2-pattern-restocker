package com.vaded.ae2patternrestocker;

import net.neoforged.neoforge.common.ModConfigSpec;

public class AE2PatternRestockerConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue AUTO_RESTOCK;
    public static final ModConfigSpec.BooleanValue RETURN_BUTTON;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        AUTO_RESTOCK = builder
                .comment("Automatically pull blank patterns from ME storage when encoding a pattern with an empty blank pattern slot.")
                .define("autoRestock", true);

        RETURN_BUTTON = builder
                .comment("Show a button on the Pattern Encoding Terminal to return an encoded pattern back to blank.")
                .define("returnButton", true);

        SPEC = builder.build();
    }
}
