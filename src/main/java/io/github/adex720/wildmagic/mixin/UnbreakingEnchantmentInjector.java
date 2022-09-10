package io.github.adex720.wildmagic.mixin;

import io.github.adex720.wildmagic.registry.ModTags;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes {@link UnbreakingEnchantment} unavailable to prevent damage on {@link io.github.adex720.wildmagic.harvest.HarvestTool}.
 *
 * @author adex720
 */
@Mixin(UnbreakingEnchantment.class)
public class UnbreakingEnchantmentInjector {

    @Inject(method = "shouldPreventDamage", at = @At("RETURN"), cancellable = true)
    private static void shouldPreventDamage(ItemStack item, int level, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (item.isIn(ModTags.HARVEST_TOOLS)) cir.setReturnValue(false);
    }

}
