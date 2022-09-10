package io.github.adex720.wildmagic.enchantments;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.random.Random;

/**
 * @author adex720
 */
public class EnduranceEnchantment extends CustomEnchantment {

    public EnduranceEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, ModEnchantmentTarget.HARVEST_TOOL, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinPower(int level) {
        return 15 + level * 25;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 50;
    }

    public static boolean shouldPreventDamage(int level, Random random) {
        if (level == 0) return false;
        return random.nextInt(20) < level;
    }
}
