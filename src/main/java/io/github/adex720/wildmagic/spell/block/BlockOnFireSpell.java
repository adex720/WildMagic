package io.github.adex720.wildmagic.spell.block;

import io.github.adex720.wildmagic.mixin.ClientWorldRendererAccessor;
import io.github.adex720.wildmagic.mixin.WorldRendererInvoker;
import io.github.adex720.wildmagic.raycast.RaycastResult;
import io.github.adex720.wildmagic.spell.BlockTargetSpell;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

/**
 * The spell sets a block on fire.
 *
 * @author adex720
 */
public class BlockOnFireSpell extends BlockTargetSpell {

    public BlockOnFireSpell(String name, int color, int range, int intelligenceRequirement, int price, int consumption, int cooldown) {
        super(name, color, range, intelligenceRequirement, price, consumption, cooldown);
    }

    @Override
    public boolean onBlockHit(ClientWorld world, PlayerEntity caster, RaycastResult result) {
        BlockPos pos = result.getBlockPos();
        BlockPos firePos = pos.up();
        if (world.getBlockState(pos).isIn(BlockTags.FIRE)) return false; // Prevent fire on top of fire
        if (!world.getBlockState(firePos).isAir()) return false; // Only allow air to be replaced by fire

        BlockState fireBlock = AbstractFireBlock.getState(world, firePos);
        world.setBlockState(firePos, fireBlock, 11);
        world.emitGameEvent(caster, GameEvent.BLOCK_PLACE, firePos);

        WorldRendererInvoker worldRenderer = (WorldRendererInvoker) ((ClientWorldRendererAccessor) world).getWorldRenderer();
        createParticlePath(worldRenderer, world.getRandom(), caster.getEyePos(), result.getHitPos(), 0.5f);
        return true;
    }

}
