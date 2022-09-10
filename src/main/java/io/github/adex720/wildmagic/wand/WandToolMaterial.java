package io.github.adex720.wildmagic.wand;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public enum WandToolMaterial implements ToolMaterial {

    ACACIA(Ingredient.ofItems(Items.ACACIA_PLANKS), 512, 7),
    BIRCH(Ingredient.ofItems(Items.BIRCH_PLANKS), 512, 15),
    CRIMSON(Ingredient.ofItems(Items.CRIMSON_PLANKS), 768, 10),
    DARK_OAK(Ingredient.ofItems(Items.DARK_OAK_PLANKS), 640, 7),
    JUNGLE(Ingredient.ofItems(Items.JUNGLE_PLANKS), 512, 15),
    MANGROVE(Ingredient.ofItems(Items.MANGROVE_PLANKS), 640, 10),
    OAK(Ingredient.ofItems(Items.OAK_PLANKS), 512, 10),
    SPRUCE(Ingredient.ofItems(Items.SPRUCE_PLANKS), 512, 20),
    WARPED(Ingredient.ofItems(Items.WARPED_PLANKS), 768, 10);

    private final int durability;
    private final float attackDamage;
    private final int enchantability;
    private final Ingredient ingredient;

    WandToolMaterial(Ingredient ingredient, int durability, float attackDamage, int enchantability) {
        this.ingredient = ingredient;
        this.durability = durability;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
    }

    WandToolMaterial(Ingredient ingredient, int durability, int enchantability) {
        this(ingredient, durability, 1f, enchantability);
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 1f;
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
        return ingredient;
    }
}
