package io.github.adex720.wildmagic.spell;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.github.adex720.wildmagic.Util;
import io.github.adex720.wildmagic.mixin.WorldRendererInvoker;
import io.github.adex720.wildmagic.registry.ModAttributes;
import io.github.adex720.wildmagic.wand.Wand;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Range;

/**
 * Spells are cast by players with wands.
 * Spells cost a set amount of magic to be cast.
 * They can require the caster to have a set amount of intelligence to be cast.
 *
 * @author adex720
 */
public abstract class Spell {

    private static int SPELL_COUNT = 0;

    public final String name;
    public final int id;
    public final int color;

    public final int intelligenceRequirement;
    public final int price;
    public final int consumption;
    public final int cooldown;

    /**
     * @param name                    Name of the spell
     * @param color                   Color of the spell
     * @param intelligenceRequirement Require intelligence to cast the spell
     * @param price                   Price to cast the spell
     * @param consumption             Damage done to the wand
     * @param cooldown                Cooldown on ticks
     */
    protected Spell(String name, int color, int intelligenceRequirement, int price, int consumption, int cooldown) {
        this.name = name;
        this.id = SPELL_COUNT++;
        this.color = color;
        this.intelligenceRequirement = intelligenceRequirement;
        this.price = price;
        this.consumption = consumption;
        this.cooldown = cooldown;
    }

    /**
     * Returns the amount of spells registered.
     */
    public static int getSpellCount() {
        return SPELL_COUNT;
    }

    public boolean canUse(PlayerEntity player) {
        int intelligence = (int) player.getAttributeValue(ModAttributes.INTELLIGENCE);
        if (intelligence < intelligenceRequirement) return false;

        int magic = (int) player.getAttributeValue(ModAttributes.MAGIC);
        if (magic < consumption) {
            player.sendMessage(Text.translatable("wand.wmag.missing_magic"));
            return false;
        }

        return true;
    }

    public float getRed() {
        return ((color >> 16) & 0xFF) * Util.ONE_PER_256;
    }

    public float getGreen() {
        return ((color >> 8) & 0xFF) * Util.ONE_PER_256;
    }

    public float getBlue() {
        return (color & 0xFF) * Util.ONE_PER_256;
    }

    public boolean canUseOnWand(Wand wand) {
        return true;
    }

    public abstract boolean cast(PlayerEntity caster, ClientWorld world, ItemStack wand);

    /**
     * Adds cooldown for the player, reduces magic and damages the wand.
     */
    public void afterUsed(PlayerEntity player, Wand wand, ItemStack itemStack) {
        AttributeContainer attributes = player.getAttributes();

        EntityAttributeInstance cooldownAttribute = attributes.getCustomInstance(ModAttributes.SPELL_COOLDOWN);
        cooldownAttribute.setBaseValue(cooldownAttribute.getValue() + cooldown);

        if (price > 0) {
            EntityAttributeInstance magicAttribute = attributes.getCustomInstance(ModAttributes.MAGIC);
            magicAttribute.setBaseValue(magicAttribute.getValue());
        }

        if (consumption > 0) {
            wand.damage(itemStack, consumption, player.getRandom(), null);
        }
    }

    /**
     * Creates a path of particles between 2 positions.
     * The particles will move towards the end position, but a small randomness is applied.
     *
     * @param world   World
     * @param random  Random to be used
     * @param start   Start of the particle path.
     * @param end     End of the particle path.
     * @param density Amount of particles per meter. (1 m = edge of one block)
     */
    protected void createParticlePath(WorldRendererInvoker world, Random random, Vec3d start, Vec3d end, float density) {
        createParticlePath(world, random, getRed(), getGreen(), getBlue(), start, end, density);
    }


    /**
     * Creates a path of particles between 2 positions.
     * The particles will move towards the end position, but a small randomness is applied.
     *
     * @param world   World
     * @param random  Random to be used
     * @param r       Amount of red on the particle color (0 to 1)
     * @param g       Amount of green on the particle color (0 to 1)
     * @param b       Amount of blue on the particle color (0 to 1)
     * @param start   Start of the particle path.
     * @param end     End of the particle path.
     * @param density Amount of particles per meter. (1 m = edge of one block)
     */
    protected void createParticlePath(WorldRendererInvoker world, Random random, @Range(from = 0, to = 1) float r, @Range(from = 0, to = 1) float g, @Range(from = 0, to = 1) float b, Vec3d start, Vec3d end, float density) {
        if (start.x == end.x && start.y == end.y && start.z == end.z) return;

        Vec3d path = end.subtract(start);

        int particleCount = (int) (path.length() * density);

        Vec3d firstPos = path.multiply(1f / particleCount);

        double x = start.x;
        double y = start.y;
        double z = start.z;

        double dX = firstPos.x;
        double dY = firstPos.y;
        double dZ = firstPos.z;

        for (int i = 0; i < particleCount; i++) {
            int rotationNoise = random.nextInt(0xFFF);
            final float rotationNoiseX = calculateRotationNoise(rotationNoise >> 8);
            final float rotationNoiseY = calculateRotationNoise(rotationNoise >> 4);
            final float rotationNoiseZ = calculateRotationNoise(rotationNoise);

            createParticle(world, r, g, b, x, y, z, (float) (dX * rotationNoiseX), (float) (dY * rotationNoiseY), (float) (dZ * rotationNoiseZ));
            x += dX;
            y += dY;
            z += dZ;
        }
    }

    /**
     * Applies small randomness to particle movement.
     *
     * @param i Random integer between 0-15
     * @return Multiplier for particle movement
     */
    private float calculateRotationNoise(int i) {
        return ((i & 0xF) - 0.75f) * 0.05f;
    }

    /**
     * Creates a particle.
     *
     * @param world World to create the particle
     * @param r     Amount of red on the particle color (0 to 1)
     * @param g     Amount of green on the particle color (0 to 1)
     * @param b     Amount of blue on the particle color (0 to 1)
     * @param x     X position of the particle
     * @param y     Y position of the particle
     * @param z     Z position of the particle
     * @param dx    Movement speed of the particle on X-axis.
     * @param dY    Movement speed of the particle on Y-axis.
     * @param dz    Movement speed of the particle on Z-axis.
     * @return The particle
     */
    @CanIgnoreReturnValue
    protected Particle createParticle(WorldRendererInvoker world, @Range(from = 0, to = 1) float r, @Range(from = 0, to = 1) float g, @Range(from = 0, to = 1) float b, double x, double y, double z, float dx, float dY, float dz) {
        Particle particle = world.invokeSpawnParticle(ParticleTypes.EFFECT, true, false, x, y, z, dx, dY, dz);
        particle.setColor(r, g, b);
        return particle;
    }

    /**
     * Creates a particle with a Y velocity of 4.
     *
     * @param world World to create the particle
     * @param x     X position of the particle
     * @param y     Y position of the particle
     * @param z     Z position of the particle
     * @param dx    Movement speed of the particle on X-axis. The particle will be created dx away from the x position.
     * @param dz    Movement speed of the particle on Z-axis. The particle will be created dz away from the z position.
     * @return The particle
     */
    @CanIgnoreReturnValue
    protected Particle createParticle(WorldRendererInvoker world, double x, double y, double z, float dx, float dz) {
        return createParticle(world, getRed(), getGreen(), getBlue(), x, y, z, dx, dz);
    }

    /**
     * Creates a particle with a Y velocity of 4.
     *
     * @param world World to create the particle
     * @param r     Amount of red on the particle color (0 to 1)
     * @param g     Amount of green on the particle color (0 to 1)
     * @param b     Amount of blue on the particle color (0 to 1)
     * @param x     X position of the particle
     * @param y     Y position of the particle
     * @param z     Z position of the particle
     * @param dx    Movement speed of the particle on X-axis. The particle will be created dx away from the x position.
     * @param dz    Movement speed of the particle on Z-axis. The particle will be created dz away from the z position.
     * @return The particle
     */
    @CanIgnoreReturnValue
    protected Particle createParticle(WorldRendererInvoker world, @Range(from = 0, to = 1) float r, @Range(from = 0, to = 1) float g, @Range(from = 0, to = 1) float b, double x, double y, double z, float dx, float dz) {
        return createParticle(world, r, g, b, x + dx, y, z + dz, dx, 4.0f, dz);
    }
}
