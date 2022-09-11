package io.github.adex720.wildmagic.spell;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * A spell which can be used on blocks.
 *
 * @deprecated
 * @author adex720
 */
public abstract class BlockTargetSpell extends ObjectTargetSpell {

    protected BlockTargetSpell(String name, int color, int range, int intelligenceRequirement, int price, int consumption, int cooldown) {
        super(name, color, range, intelligenceRequirement, price, consumption, cooldown);
    }

    public BlockPos getTargetBlockPos(MinecraftClient client, ItemStack wand) {
        return calculateTarget(client, wand).getBlockPos();
    }

}
