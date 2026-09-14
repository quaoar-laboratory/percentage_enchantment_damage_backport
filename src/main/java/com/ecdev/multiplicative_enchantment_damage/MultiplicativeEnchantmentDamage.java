package com.ecdev.multiplicative_enchantment_damage;

import com.ecdev.multiplicative_enchantment_damage.common.Config;
import com.ecdev.multiplicative_enchantment_damage.utils.MultiplicativeEnchantmentUtils;
import com.mojang.logging.LogUtils;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.neoforge.common.NeoForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.neoforge.event.server.ServerStartingEvent;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.slf4j.Logger;

import static com.ecdev.multiplicative_enchantment_damage.common.Config.*;

@Mod(MultiplicativeEnchantmentDamage.MOD_ID)
public class MultiplicativeEnchantmentDamage {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "multiplicativeenchantmentdamage";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like SubscribeEvent or ModContainer and pass them in automatically.
    public MultiplicativeEnchantmentDamage(SubscribeEvent modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (MultiplicativeEnchantmentDamage) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @SubscribeEvent
    public void multiplicativeSharpness(LivingDamageEvent LivingDamageEvent) {
        MultiplicativeEnchantmentUtils.applyMultiplicativeDamageAnyEntity(
                LivingDamageEvent,
                Enchantments.SHARPNESS,
                SHARPNESS_OVERWRITE,
                SHARPNESS_ENABLED,
                SHARPNESS_MULTIPLY_FIRST_LEVEL,
                SHARPNESS_MULTIPLY_ADDITIONAL_LEVELS,
                1.0f,
                0.5f
        );
    }

    @SubscribeEvent
    public void multiplicativeBaneOfArthropods(LivingDamageEvent LivingDamageEvent) {
        MultiplicativeEnchantmentUtils.applyMultiplicativeDamageForEntityType(
                LivingDamageEvent,
                Enchantments.BANE_OF_ARTHROPODS,
                BANE_OF_ARTHROPODS_OVERWRITE,
                BANE_OF_ARTHROPODS_ENABLED,
                BANE_OF_ARTHROPODS_MULTIPLY_FIRST_LEVEL,
                BANE_OF_ARTHROPODS_MULTIPLY_ADDITIONAL_LEVELS,
                2.5f,
                2.5f,
                EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS
        );
    }

    @SubscribeEvent
    public void multiplicativeSmite(LivingDamageEvent LivingDamageEvent) {
        MultiplicativeEnchantmentUtils.applyMultiplicativeDamageForEntityType(
                LivingDamageEvent,
                Enchantments.SMITE,
                SMITE_OVERWRITE,
                SMITE_ENABLED,
                SMITE_MULTIPLY_FIRST_LEVEL,
                SMITE_MULTIPLY_ADDITIONAL_LEVELS,
                2.5f,
                2.5f,
                EntityTypeTags.SENSITIVE_TO_SMITE
        );
    }

    @SubscribeEvent
    public void multiplicativeImpaling(LivingDamageEvent LivingDamageEvent) {
        MultiplicativeEnchantmentUtils.applyMultiplicativeDamageForEntityType(
                LivingDamageEvent,
                Enchantments.IMPALING,
                IMPALING_OVERWRITE,
                IMPALING_ENABLED,
                IMPALING_MULTIPLY_FIRST_LEVEL,
                IMPALING_MULTIPLY_ADDITIONAL_LEVELS,
                2.5f,
                2.5f,
                EntityTypeTags.SENSITIVE_TO_IMPALING
        );
    }

}
