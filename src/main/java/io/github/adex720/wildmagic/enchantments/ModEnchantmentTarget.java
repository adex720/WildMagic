package io.github.adex720.wildmagic.enchantments;

import io.github.adex720.wildmagic.harvest.HarvestTool;
import io.github.adex720.wildmagic.wand.Wand;
import net.minecraft.item.Item;

/**
 * Enchantment type determines which tools can have the enchantment.
 *
 * @author adex720
 */
public enum ModEnchantmentTarget {

    WAND(){
        @Override
        public boolean isAcceptableItem(Item item) {
            return item instanceof Wand;
        }
    },
    HARVEST_TOOL() {
        @Override
        public boolean isAcceptableItem(Item item) {
            return item instanceof HarvestTool;
        }
    };

    ModEnchantmentTarget() {
    }

    public abstract boolean isAcceptableItem(Item item);
}
