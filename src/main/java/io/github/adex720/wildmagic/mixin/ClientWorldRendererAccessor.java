package io.github.adex720.wildmagic.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Allows access to {@link WorldRenderer} field in {@link ClientWorld}.
 *
 * @author adex720
 */
@Mixin(ClientWorld.class)
public interface ClientWorldRendererAccessor {
    @Accessor
    WorldRenderer getWorldRenderer();
}
