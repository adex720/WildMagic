package io.github.adex720.wildmagic.spell;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;

/**
 * A spell which can be used on blocks.
 *
 * @author adex720
 */
public abstract class BlockTargetSpell extends Spell {

    public final int range;

    protected BlockTargetSpell(String name, int color, int range, int intelligenceRequirement, int price, int consumption, int cooldown) {
        super(name, color, intelligenceRequirement, price, consumption, cooldown);
        this.range = range;
    }

    public abstract void onBlockHit(BlockState target, PlayerEntity caster);
}
