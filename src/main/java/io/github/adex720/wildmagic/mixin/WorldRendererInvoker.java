package io.github.adex720.wildmagic.mixin;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Allows access to {@link WorldRenderer}#spawnParticle(ParticleEffect, boolean, double, double, double, double, double, double).
 */
@Mixin(WorldRenderer.class)
public interface WorldRendererInvoker {
    @Invoker("spawnParticle")
    Particle invokeSpawnParticle(ParticleEffect parameters, boolean alwaysSpawn, boolean canSpawnOnMinimal, double x, double y, double z, double velocityX, double velocityY, double velocityZ);


}
