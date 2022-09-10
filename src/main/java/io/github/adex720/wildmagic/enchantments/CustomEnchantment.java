package io.github.adex720.wildmagic.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class CustomEnchantment extends Enchantment {

    public final ModEnchantmentTarget enchantmentTarget;

    protected CustomEnchantment(Rarity weight, ModEnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, EnchantmentTarget.FISHING_ROD, slotTypes); // https://github.com/KingVampyre/DeepTrenches/blob/master/src/main/java/github/KingVampyre/DeepTrenches/core/mixin/MixinEnchantmentHelper.java
        enchantmentTarget = type;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return enchantmentTarget.isAcceptableItem(stack.getItem());
    }
}
