package com.ecdev.multiplicative_enchantment_damage.utils;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.Optional;

public class MultiplicativeEnchantmentUtils {

    public static Optional<Holder.Reference<Enchantment>> getHolderForEnchantment(
            LivingDamageEvent LivingDamageEvent,
            ResourceKey<Enchantment> enchantmentResourceKey
    ) {
        Level level = LivingDamageEvent.getEntity().level();
        return level.registryAccess().holder(enchantmentResourceKey);
    }

    public static int getEnchantmentLevelGivenIncomingDamageEvent(
            LivingDamageEvent LivingDamageEvent,
            ResourceKey<Enchantment> enchantmentResourceKey
    ) {
        return getHolderForEnchantment(LivingDamageEvent, enchantmentResourceKey)
                .map(sharpnessRegistry ->
                        Optional.of(LivingDamageEvent.getContainer())
                                .map(DamageContainer::getSource)
                                .map(DamageSource::getWeaponItem)
                                .map(itemStack ->
                                        itemStack.getEnchantmentLevel(sharpnessRegistry))
                                .orElse(0))
                .orElse(0);
    }

    /**
     * Util to apply given enchantment enhancement to damage multiplicatively regardless of EntityType.
     * (E.g. Sharpness)
     *
     * @param LivingDamageEvent           Event itself that is called when an entity is hit.
     * @param checkForEnchantment                 Enchantment to check for to apply this multiplier.
     * @param configOverwriteDefault              Config value whether to undo vanilla/original implementation.
     * @param configEnabled                       Config value for whether this multiplier is enabled at all.
     * @param multiplyFirstLevelValue             Config value for the multiplier for level 1.
     * @param multiplyAdditionalLevels            Config value for the multiplier added per level following.
     * @param overwriteSubtractFirstLevel         How much to subtract for the first level of disabled original impl.
     * @param overwriteSubtractPerAdditionalLevel How much to subtract per additional level of disabled original impl.
     */
    public static void applyMultiplicativeDamageAnyEntity(
            LivingDamageEvent LivingDamageEvent,
            ResourceKey<Enchantment> checkForEnchantment,
            ForgeConfigSpec.BooleanValue configOverwriteDefault,
            ForgeConfigSpec.BooleanValue configEnabled,
            ForgeConfigSpec.DoubleValue multiplyFirstLevelValue,
            ForgeConfigSpec.DoubleValue multiplyAdditionalLevels,
            float overwriteSubtractFirstLevel,
            float overwriteSubtractPerAdditionalLevel
    ) {
        if (!LivingDamageEvent.isCanceled()) {
            applyMultiplicativeDamage(
                    LivingDamageEvent,
                    checkForEnchantment,
                    configOverwriteDefault,
                    configEnabled,
                    multiplyFirstLevelValue,
                    multiplyAdditionalLevels,
                    overwriteSubtractFirstLevel,
                    overwriteSubtractPerAdditionalLevel);
        }
    }

    /**
     * Util to apply given enchantment enhancement to damage multiplicatively for specific EntityType.
     * (E.g. Smite, Bane of Arthropods)
     *
     * @param LivingDamageEvent           Event itself that is called when an entity is hit.
     * @param checkForEnchantment                 Enchantment to check for to apply this multiplier.
     * @param configOverwriteDefault              Config value whether to undo vanilla/original implementation.
     * @param configEnabled                       Config value for whether this multiplier is enabled at all.
     * @param multiplyFirstLevelValue             Config value for the multiplier for level 1.
     * @param multiplyAdditionalLevels            Config value for the multiplier added per level following.
     * @param overwriteSubtractFirstLevel         How much to subtract for the first level of disabled original impl.
     * @param overwriteSubtractPerAdditionalLevel How much to subtract per additional level of disabled original impl.
     * @param applicableEntityType                E.g. EntityTypeTags.SENSITIVE_TO_SMITE
     */
    public static void applyMultiplicativeDamageForEntityType(
            LivingDamageEvent LivingDamageEvent,
            ResourceKey<Enchantment> checkForEnchantment,
            ForgeConfigSpec.BooleanValue configOverwriteDefault,
            ForgeConfigSpec.BooleanValue configEnabled,
            ForgeConfigSpec.DoubleValue multiplyFirstLevelValue,
            ForgeConfigSpec.DoubleValue multiplyAdditionalLevels,
            float overwriteSubtractFirstLevel,
            float overwriteSubtractPerAdditionalLevel,
            TagKey<EntityType<?>> applicableEntityType
    ) {
        if (!LivingDamageEvent.isCanceled()
                && LivingDamageEvent.getEntity()
                .getType()
                .is(applicableEntityType)
        ) {
            applyMultiplicativeDamage(
                    LivingDamageEvent,
                    checkForEnchantment,
                    configOverwriteDefault,
                    configEnabled, multiplyFirstLevelValue,
                    multiplyAdditionalLevels,
                    overwriteSubtractFirstLevel,
                    overwriteSubtractPerAdditionalLevel);
        }
    }

    /**
     * Util to apply multiplicative damage to a LivingDamageEvent (when an entity takes damage).
     * Given the event, enchantment scaling configuration, and whether to overwrite the flat scaling default or not.
     *
     * @param LivingDamageEvent           Event itself that is called when an entity is hit.
     * @param checkForEnchantment                 Enchantment to check for to apply this multiplier.
     * @param configOverwriteDefault              Config value whether to undo vanilla/original implementation.
     * @param configEnabled                       Config value for whether this multiplier is enabled at all.
     * @param multiplyFirstLevelValue             Config value for the multiplier for level 1.
     * @param multiplyAdditionalLevels            Config value for the multiplier added per level following.
     * @param overwriteSubtractFirstLevel         How much to subtract for the first level of disabled original impl.
     * @param overwriteSubtractPerAdditionalLevel How much to subtract per additional level of disabled original impl.
     */
    private static void applyMultiplicativeDamage(
            LivingDamageEvent LivingDamageEvent,
            ResourceKey<Enchantment> checkForEnchantment,
            ForgeConfigSpec.BooleanValue configOverwriteDefault,
            ForgeConfigSpec.BooleanValue configEnabled,
            ForgeConfigSpec.DoubleValue multiplyFirstLevelValue,
            ForgeConfigSpec.DoubleValue multiplyAdditionalLevels,
            float overwriteSubtractFirstLevel,
            float overwriteSubtractPerAdditionalLevel
    ) {
        int enchantLevel = MultiplicativeEnchantmentUtils.getEnchantmentLevelGivenIncomingDamageEvent(
                LivingDamageEvent,
                checkForEnchantment);
        if (enchantLevel > 0) {
            float baseDamage = LivingDamageEvent.getContainer()
                    .getOriginalDamage();
            float newDamage = LivingDamageEvent.getContainer()
                    .getNewDamage();

            if (enchantLevel > 1) {
                enchantLevel--; // Effective additional multiplier
            }
            if (configOverwriteDefault.isTrue()) {
                float vanillaSmiteSubtract = overwriteSubtractFirstLevel
                        + overwriteSubtractPerAdditionalLevel * (enchantLevel);
                baseDamage = baseDamage - vanillaSmiteSubtract; // baseDamage has Smite added too
                newDamage = newDamage - vanillaSmiteSubtract;
            }
            if (configEnabled.isTrue()) {
                newDamage = (float) (newDamage + baseDamage * (multiplyFirstLevelValue.getAsDouble()
                        + multiplyAdditionalLevels.getAsDouble()
                        * enchantLevel));
            }
            LivingDamageEvent
                    .getContainer()
                    .setNewDamage(Math.max(0.0f, newDamage));
        }
    }

}
