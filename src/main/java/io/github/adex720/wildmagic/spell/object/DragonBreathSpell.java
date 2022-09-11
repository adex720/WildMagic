package io.github.adex720.wildmagic.spell.object;

import io.github.adex720.wildmagic.Util;
import io.github.adex720.wildmagic.mixin.ClientWorldRendererAccessor;
import io.github.adex720.wildmagic.raycast.RaycastResult;
import io.github.adex720.wildmagic.spell.ObjectTargetSpell;
import io.github.adex720.wildmagic.wand.Wand;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Summons dragon breath around an entity or on a block.
 *
 * @author adex720
 */
public class DragonBreathSpell extends ObjectTargetSpell {

    /**
     * @param name                    Name of the spell
     * @param color                   Color of the spell
     * @param range                   Maximum distance to the target
     * @param intelligenceRequirement Require intelligence to cast the spell
     * @param price                   Price to cast the spell
     * @param consumption             Damage done to the wand
     * @param cooldown                Cooldown on ticks
     */
    public DragonBreathSpell(String name, int color, double range, int intelligenceRequirement, int price, int consumption, int cooldown) {
        super(name, color, range, intelligenceRequirement, price, consumption, cooldown);
    }

    @Override
    public boolean onTargetHit(ClientWorld world, PlayerEntity caster, RaycastResult result) {
        Vec3d pos = result.getFixedPos();
        createParticleArea(world, pos.x, pos.y, pos.z, 3d);
        return true;
    }

    /**
     * Creates a circular area filled with dragon breath particles.
     *
     * @param world  World to create particles in
     * @param x      X position of the middle of the area
     * @param y      Y position of the middle of the area
     * @param z      Z position of the middle of the area
     * @param radius Radius of the ring
     */
    public void createParticleArea(World world, double x, double y, double z, double radius) {
        WorldRenderer worldRenderer = ((ClientWorldRendererAccessor) world).getWorldRenderer();

        float frequency = 0.2f;
        int countMultiplier = 1;
        for (float distance = frequency * 0.5f; distance <= radius; distance += frequency) {
            createParticleRing(worldRenderer, world.getRandom(), x, y, z, distance, countMultiplier);
            countMultiplier++;
        }
    }

    /**
     * Creates a ring made of dragon breath particles.
     *
     * @param worldRenderer   World renderer
     * @param random          Random to use when randomizing rotation
     * @param x               X position of the middle of the ring
     * @param y               Y position of the middle of the ring
     * @param z               Z position of the middle of the ring
     * @param radius          Radius of the ring
     * @param countMultiplier Count of particles per quarter
     */
    public void createParticleRing(WorldRenderer worldRenderer, Random random, double x, double y, double z, float radius, int countMultiplier) {
        float degreesDelta = (float) (Util.PI_PER_TWO / countMultiplier);
        for (float rotation = 0; rotation < Util.PI_PER_TWO; rotation += degreesDelta) {
            createFourParticles(worldRenderer, x, y, z, radius, rotation + random.nextFloat() * degreesDelta);
        }
    }

    /**
     * Creates four dragon breath particles with each having equal distance to the center.
     * If I line was drawn from each particle to the center, all radian would be 90°.
     * All particles have equal Y position.
     *
     * @param worldRenderer World renderer
     * @param x             X position of the middle of the particles
     * @param y             Y position of the middle of the particles
     * @param z             Z position of the middle of the particles
     * @param radius        Distance from the middle to a particle
     * @param radians       Rotation of the particles
     */
    public void createFourParticles(WorldRenderer worldRenderer, double x, double y, double z, float radius, float radians) {
        float sin = MathHelper.sin(radians) * radius;
        float cos = MathHelper.cos(radians) * radius;

        createDragonBreathParticle(worldRenderer, x + cos, y, z + sin);
        createDragonBreathParticle(worldRenderer, x - sin, y, z + cos);
        createDragonBreathParticle(worldRenderer, x - cos, y, z - sin);
        createDragonBreathParticle(worldRenderer, x + sin, y, z - cos);
    }

    public void createDragonBreathParticle(WorldRenderer world, double x, double y, double z) {
        world.addParticle(ParticleTypes.DRAGON_BREATH, true, false, x, y, z, 0, 0, 0);
    }

    @Override
    public boolean canUseOnWand(Wand wand) {
        //return wand.getCore() == WandCore.DRAGON;
        return true;
    }
}
