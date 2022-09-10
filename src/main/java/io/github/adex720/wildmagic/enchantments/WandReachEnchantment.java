package io.github.adex720.wildmagic.enchantments;

import net.minecraft.entity.EquipmentSlot;

/**
 * @author adex720
 */
public class WandReachEnchantment extends CustomEnchantment {

    public WandReachEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, ModEnchantmentTarget.WAND, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinPower(int level) {
        return 50 + level * 30;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 75;
    }

    public static float getRangeMultiplier(int level) {
        if (level == 0) return 1f;
        return 1f + level * 0.1f;
    }
}
