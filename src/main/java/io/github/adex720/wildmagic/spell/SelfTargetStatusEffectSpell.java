package io.github.adex720.wildmagic.spell;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class SelfTargetStatusEffectSpell extends SelfTargetSpell {

    public final StatusEffect statusEffect;
    public final int duration;
    public final int amplifier;

    public SelfTargetStatusEffectSpell(StatusEffect effect, int duration, int amplifier, String name, int intelligenceRequirement, int price, int consumption, int cooldown) {

        super(name, effect.getColor(), intelligenceRequirement, price, consumption, cooldown);
        this.statusEffect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public SelfTargetStatusEffectSpell(StatusEffect effect, int duration, int amplifier, String name, int intelligenceRequirement, int price, int consumption) {
        this(effect, duration, amplifier, name, intelligenceRequirement, price, consumption, duration);
    }

    @Override
    public boolean cast(PlayerEntity caster, ClientWorld world, ItemStack wand) {
        caster.addStatusEffect(new StatusEffectInstance(statusEffect, duration, amplifier));
        createParticlesOnUser(caster, world);
        return true;
    }
}
