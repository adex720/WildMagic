package io.github.adex720.wildmagic.spell;

import io.github.adex720.wildmagic.enchantments.WandReachEnchantment;
import io.github.adex720.wildmagic.mixin.WorldRendererInvoker;
import io.github.adex720.wildmagic.raycast.RaycastHelper;
import io.github.adex720.wildmagic.raycast.RaycastResult;
import io.github.adex720.wildmagic.registry.ModEnchantments;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

/**
 * Spell which can target both blocks and entities.
 *
 * @author adex720
 */
public abstract class ObjectTargetSpell extends Spell {

    public final double range;

    /**
     * @param name                    Name of the spell
     * @param color                   Color of the spell
     * @param range                   Maximum distance to the target
     * @param intelligenceRequirement Require intelligence to cast the spell
     * @param price                   Price to cast the spell
     * @param consumption             Damage done to the wand
     * @param cooldown                Cooldown on ticks
     */
    public ObjectTargetSpell(String name, int color, double range, int intelligenceRequirement, int price, int consumption, int cooldown) {
        super(name, color, intelligenceRequirement, price, consumption, cooldown);
        this.range = range;
    }

    /**
     * Called when a block is hit.
     * Used when hitting block has different result as hitting an entity.
     *
     * @see ObjectTargetSpell#onEntityHit(ClientWorld, PlayerEntity, RaycastResult)
     * @see ObjectTargetSpell#onTargetHit(ClientWorld, PlayerEntity, RaycastResult)
     * @see BlockTargetSpell
     */
    public boolean onBlockHit(ClientWorld world, PlayerEntity caster, RaycastResult result) {
        return false;
    }

    /**
     * Called when an entity is hit.
     * Used when hitting block has different result as hitting an entity.
     *
     * @see ObjectTargetSpell#onBlockHit(ClientWorld, PlayerEntity, RaycastResult)
     * @see ObjectTargetSpell#onTargetHit(ClientWorld, PlayerEntity, RaycastResult)
     * @see EntityTargetSpell
     */
    public boolean onEntityHit(ClientWorld world, PlayerEntity caster, RaycastResult result) {
        return false;
    }

    /**
     * Called when a block or an entity is hit.
     * Used when hitting a block has the same result as hitting an entity.
     *
     * @see ObjectTargetSpell#onBlockHit(ClientWorld, PlayerEntity, RaycastResult)
     * @see ObjectTargetSpell#onTargetHit(ClientWorld, PlayerEntity, RaycastResult)
     */
    public boolean onTargetHit(ClientWorld world, PlayerEntity caster, RaycastResult result) {
        return false;
    }

    public RaycastResult calculateTarget(MinecraftClient client, ItemStack wand) {
        int reachLevel = EnchantmentHelper.getLevel(ModEnchantments.REACH, wand);
        float rangeMultiplier = WandReachEnchantment.getRangeMultiplier(reachLevel);

        RaycastHelper raycastHelper = new RaycastHelper();
        return raycastHelper.raycast(client, 0, range * rangeMultiplier);
    }

    @Override
    public boolean cast(PlayerEntity caster, ClientWorld world, ItemStack wand) {
        RaycastResult result = calculateTarget(MinecraftClient.getInstance(), wand);
        if (result.getType() == HitResult.Type.MISS) { // Nothing was found
            return false;
        }
        onTargetHit(world, caster, result);

        if (result.getType() == HitResult.Type.BLOCK) onBlockHit(world, caster, result);
        else onEntityHit(world, caster, result);
        return true;
    }

    public void createParticlePath(WorldRendererInvoker world, Random random, LivingEntity caster, Entity target, float density) {
        // float casterHeightFixer = caster.getHeight() * 0.5f;
        // Vec3d casterPos = caster.getPos().add(0, casterHeightFixer, 0);
        Vec3d casterPos = caster.getEyePos();
        float targetHeightFixer = target.getHeight() * 0.5f;
        Vec3d targetPos = target.getPos().add(0, targetHeightFixer, 0);

        super.createParticlePath(world, random, casterPos, targetPos, density);
    }
}
