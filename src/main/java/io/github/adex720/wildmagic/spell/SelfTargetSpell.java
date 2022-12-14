package io.github.adex720.wildmagic.spell;

import io.github.adex720.wildmagic.mixin.ClientWorldRendererAccessor;
import io.github.adex720.wildmagic.mixin.WorldRendererInvoker;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * A spell which is used on the caster.
 *
 * @author adex720
 */
public abstract class SelfTargetSpell extends Spell {

    protected SelfTargetSpell(String name, int color, int intelligenceRequirement, int price, int consumption, int cooldown) {
        super(name, color, intelligenceRequirement, price, consumption, cooldown);
    }

    /**
     * Creates particles around a player.
     */
    protected void createParticlesOnPlayer(PlayerEntity player, ClientWorld world) {
        Vec3d pos = player.getPos();
        double x = pos.x;
        double y = pos.y;
        double z = pos.z;

        WorldRendererInvoker worldRenderer = (WorldRendererInvoker) ((ClientWorldRendererAccessor) world).getWorldRenderer();

        float goal = (float) (Math.PI * 0.5f);
        int particlesPerQuarter = 6;
        float increment = goal / particlesPerQuarter;

        for (float direction = 0; direction < goal; direction += increment) {
            float sin = MathHelper.sin(direction);
            float cos = MathHelper.cos(direction);

            for (double dy = 0.0d; dy < 3.0d; dy += 0.5d) {
                double newY = y + dy;
                createParticle(worldRenderer, x, newY, z, sin, cos);
                createParticle(worldRenderer, x, newY, z, cos, -sin);
                createParticle(worldRenderer, x, newY, z, -sin, -cos);
                createParticle(worldRenderer, x, newY, z, -cos, sin);
            }
        }
    }

}

