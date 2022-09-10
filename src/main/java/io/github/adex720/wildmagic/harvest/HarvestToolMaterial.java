package io.github.adex720.wildmagic.harvest;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public enum HarvestToolMaterial implements ToolMaterial {

    EMERALD(0, 6f, 2f, 18, Ingredient.ofItems(Items.EMERALD)),

    DRAGON_HEART(8, 1f, 3.5f, 22, Ingredient.ofItems(Items.OBSIDIAN));

    private final int durability;
    private final float miningSpeedMultiplier;
    private final float attackDamage;
    private final int enchantability;
    private final Ingredient repairIngredient;

    HarvestToolMaterial(int durability, float miningSpeedMultiplier, float attackDamage, int enchantability, Ingredient repairIngredient) {
        this.durability = durability;
        this.miningSpeedMultiplier = miningSpeedMultiplier;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return miningSpeedMultiplier;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }
}
