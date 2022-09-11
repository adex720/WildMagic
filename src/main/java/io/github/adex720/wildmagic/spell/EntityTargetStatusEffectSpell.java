package io.github.adex720.wildmagic.spell;

import io.github.adex720.wildmagic.mixin.ClientWorldRendererAccessor;
import io.github.adex720.wildmagic.mixin.WorldRendererInvoker;
import io.github.adex720.wildmagic.raycast.RaycastResult;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * A spell which gives a status effect and can be used on other living entities.
 *
 * @author adex720
 */
public class EntityTargetStatusEffectSpell extends EntityTargetSpell {

    public final StatusEffect statusEffect;
    public final int duration;
    public final int amplifier;

    public EntityTargetStatusEffectSpell(StatusEffect effect, int duration, int amplifier,
                                         String name, double range, int intelligenceRequirement, int price, int consumption, int cooldown) {

        super(name, effect.getColor(), range, intelligenceRequirement, price, consumption, cooldown);
        this.statusEffect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public EntityTargetStatusEffectSpell(StatusEffect effect, int duration, int amplifier, String name, double range, int intelligenceRequirement, int price, int consumption) {
        this(effect, duration, amplifier, name, range, intelligenceRequirement, price, consumption, duration);
    }


    @Override
    public boolean onEntityHit(ClientWorld world, PlayerEntity caster, RaycastResult result) {
        Entity target = result.getEntity();
        if (!target.isLiving()) return false;
        ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(statusEffect, duration, amplifier));

        WorldRendererInvoker worldRenderer = (WorldRendererInvoker) ((ClientWorldRendererAccessor) world).getWorldRenderer();
        createParticlePath(worldRenderer, world.getRandom(), caster, target, 4f);
        return true;
    }
}
