package io.github.adex720.wildmagic.spell;

import io.github.adex720.wildmagic.enchantments.WandReachEnchantment;
import io.github.adex720.wildmagic.raycast.RaycastHelper;
import io.github.adex720.wildmagic.registry.ModEnchantments;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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

    @Override
    public boolean cast(PlayerEntity caster, ClientWorld world, ItemStack wand) {
        BlockPos targetPos = getTargetBlockPos(MinecraftClient.getInstance(), wand);
        if (targetPos == null) return false;
        return onBlockHit(world, caster, targetPos);
    }

    public abstract boolean onBlockHit(World world, PlayerEntity caster, BlockPos pos);

    @Nullable
    public BlockPos getTargetBlockPos(MinecraftClient client, ItemStack wand) {
        int reachLevel = EnchantmentHelper.getLevel(ModEnchantments.REACH, wand);
        float rangeMultiplier = WandReachEnchantment.getRangeMultiplier(reachLevel);

        RaycastHelper raycastHelper = new RaycastHelper();
        if (raycastHelper.raycast(client, 0, range * rangeMultiplier) != HitResult.Type.BLOCK) return null;
        return raycastHelper.getLastBlockPos();
    }
}
