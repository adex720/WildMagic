package io.github.adex720.wildmagic.spell;

import io.github.adex720.wildmagic.enchantments.WandReachEnchantment;
import io.github.adex720.wildmagic.mixin.WorldRendererInvoker;
import io.github.adex720.wildmagic.raycast.RaycastHelper;
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

import javax.annotation.Nullable;

/**
 * A spell which can be used on entities.
 *
 * @author adex720
 */
public abstract class EntityTargetSpell extends Spell {

    public final double range;

    protected EntityTargetSpell(String name, int color, double range, int intelligenceRequirement, int price, int consumption, int cooldown) {
        super(name, color, intelligenceRequirement, price, consumption, cooldown);
        this.range = range;
    }

    public abstract boolean onEntityHit(Entity target, PlayerEntity caster, ClientWorld world);

    @Nullable
    public Entity getTargetEntity(MinecraftClient client, ItemStack wand) {
        int reachLevel = EnchantmentHelper.getLevel(ModEnchantments.REACH, wand);
        float rangeMultiplier = WandReachEnchantment.getRangeMultiplier(reachLevel);

        RaycastHelper raycastHelper = new RaycastHelper();
        if (raycastHelper.raycast(client, 0, range * rangeMultiplier) != HitResult.Type.ENTITY) return null;
        return raycastHelper.getLastEntity();
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
