package io.github.adex720.wildmagic.mixin;

import io.github.adex720.wildmagic.registry.ModAttributes;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityAttributeCreator {

    @Inject(method = "createPlayerAttributes", at = @At("RETURN"), cancellable = true)
    private static void createPlayerAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue().add(ModAttributes.INTELLIGENCE).add(ModAttributes.MAGIC_LEVEL).add(ModAttributes.MAGIC_CAP).add(ModAttributes.MAGIC).add(ModAttributes.SPELL_COOLDOWN));
    }

}
