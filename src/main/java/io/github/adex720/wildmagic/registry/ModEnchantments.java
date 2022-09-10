package io.github.adex720.wildmagic.registry;

import io.github.adex720.wildmagic.Util;
import io.github.adex720.wildmagic.enchantments.EnduranceEnchantment;
import io.github.adex720.wildmagic.enchantments.WandReachEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.registry.Registry;


public class ModEnchantments {

    public static final Enchantment ENDURANCE = new EnduranceEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment REACH = new WandReachEnchantment(Enchantment.Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

    public static void registerEnchantments() {
        register(ENDURANCE, "endurance");
        register(REACH, "reach");
    }

    private static void register(Enchantment enchantment, String path) {
        Registry.register(Registry.ENCHANTMENT, Util.identifier(path), enchantment);
    }

}
