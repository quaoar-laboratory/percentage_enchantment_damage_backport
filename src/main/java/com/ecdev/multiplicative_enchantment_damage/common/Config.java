package com.ecdev.multiplicative_enchantment_damage.common;

import net.minecraftforge.common.ForgeConfigSpec;

import static com.ecdev.multiplicative_enchantment_damage.common.Const.*;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    /**
     * Util to generate Enabled flag config for given enchantment
     */
    private static ForgeConfigSpec.BooleanValue buildEnabledConfig(String enchantmentName) {
        return BUILDER
                .comment(String.format(CONFIG_DESC_ENABLED, enchantmentName))
                .define(String.format(CONFIG_KEY_ENABLED, enchantmentName), true);
    }

    /**
     * Util to generate Enabled flag config for given enchantment
     */
    private static ForgeConfigSpec.BooleanValue buildOverwriteConfig(String enchantmentName) {
        return BUILDER
                .comment(String.format(CONFIG_DESC_OVERWRITE_DEFAULT, enchantmentName))
                .define(String.format(CONFIG_KEY_OVERWRITE_DEFAULT_ENABLED, enchantmentName), true);
    }

    /**
     * Util to generate first level scaling config for given enchantment
     */
    private static ForgeConfigSpec.DoubleValue buildFirstLevelConfig(
            String enchantmentName,
            double defaultVal
    ) {
        return BUILDER
                .comment(String.format(CONFIG_DESC_MULTIPLY_FIRST_LEVEL, enchantmentName, defaultVal))
                .defineInRange(String.format(CONFIG_KEY_LEVEL_ONE_SCALING, enchantmentName),
                        defaultVal, 0.01, 99999.99);
    }

    /**
     * Util to generate additional level scaling per level for given enchantment
     */
    private static ForgeConfigSpec.DoubleValue buildAdditionalLevelConfig(
            String enchantmentName,
            double defaultVal
    ) {
        return BUILDER
                .comment(String.format(CONFIG_DESC_ADDITIONAL_SCALING, enchantmentName, defaultVal))
                .defineInRange(String.format(CONFIG_KEY_ADDITIONAL_SCALING, enchantmentName),
                        defaultVal, 0.01, 99999.99);
    }

    public static final ForgeConfigSpec.BooleanValue SHARPNESS_ENABLED
            = buildEnabledConfig(SHARPNESS);
    public static final ForgeConfigSpec.BooleanValue SHARPNESS_OVERWRITE
            = buildOverwriteConfig(SHARPNESS);
    public static final ForgeConfigSpec.DoubleValue SHARPNESS_MULTIPLY_FIRST_LEVEL
            = buildFirstLevelConfig(SHARPNESS, 0.10);
    public static final ForgeConfigSpec.DoubleValue SHARPNESS_MULTIPLY_ADDITIONAL_LEVELS
            = buildAdditionalLevelConfig(SHARPNESS, 0.05);

    public static final ForgeConfigSpec.BooleanValue BANE_OF_ARTHROPODS_ENABLED
            = buildEnabledConfig(BANE_OF_ARTHROPODS);
    public static final ForgeConfigSpec.BooleanValue BANE_OF_ARTHROPODS_OVERWRITE
            = buildOverwriteConfig(BANE_OF_ARTHROPODS);
    public static final ForgeConfigSpec.DoubleValue BANE_OF_ARTHROPODS_MULTIPLY_FIRST_LEVEL
            = buildFirstLevelConfig(BANE_OF_ARTHROPODS, 0.15);
    public static final ForgeConfigSpec.DoubleValue BANE_OF_ARTHROPODS_MULTIPLY_ADDITIONAL_LEVELS
            = buildAdditionalLevelConfig(BANE_OF_ARTHROPODS, 0.075);

    public static final ForgeConfigSpec.BooleanValue SMITE_ENABLED
            = buildEnabledConfig(SMITE);
    public static final ForgeConfigSpec.BooleanValue SMITE_OVERWRITE
            = buildOverwriteConfig(SMITE);
    public static final ForgeConfigSpec.DoubleValue SMITE_MULTIPLY_FIRST_LEVEL
            = buildFirstLevelConfig(SMITE, 0.15);
    public static final ForgeConfigSpec.DoubleValue SMITE_MULTIPLY_ADDITIONAL_LEVELS
            = buildAdditionalLevelConfig(SMITE, 0.075);

    public static final ForgeConfigSpec.BooleanValue IMPALING_ENABLED
            = buildEnabledConfig(IMPALING);
    public static final ForgeConfigSpec.BooleanValue IMPALING_OVERWRITE
            = buildOverwriteConfig(IMPALING);
    public static final ForgeConfigSpec.DoubleValue IMPALING_MULTIPLY_FIRST_LEVEL
            = buildFirstLevelConfig(IMPALING, 0.15);
    public static final ForgeConfigSpec.DoubleValue IMPALING_MULTIPLY_ADDITIONAL_LEVELS
            = buildAdditionalLevelConfig(IMPALING, 0.075);

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
