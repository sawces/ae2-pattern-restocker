package com.vaded.ae2patternrestocker;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(AE2PatternRestocker.MOD_ID)
public final class AE2PatternRestocker {
    public static final String MOD_ID = "ae2_pattern_restocker";

    public AE2PatternRestocker(ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, AE2PatternRestockerConfig.SPEC);
    }
}
