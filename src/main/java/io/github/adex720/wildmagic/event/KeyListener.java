package io.github.adex720.wildmagic.event;

import io.github.adex720.wildmagic.registry.ModAttributes;
import io.github.adex720.wildmagic.registry.ModKeyBindings;
import io.github.adex720.wildmagic.registry.ModTags;
import io.github.adex720.wildmagic.wand.Wand;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class KeyListener {

    public KeyListener() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onEndClientTick);
    }

    private void onEndClientTick(MinecraftClient client) {
        if (client.isPaused()) return;
        if (client.player == null) return;
        checkSpellKeys(client.player);
    }

    private boolean checkSpellCooldown(PlayerEntity player) {
        int cooldown = (int) player.getAttributeValue(ModAttributes.SPELL_COOLDOWN);
        if (cooldown > 0) {
            String cooldownString = Float.toString(cooldown * 0.05f + 0.0001f).substring(0, 4);
            player.sendMessage(Text.translatable("wand.wmag.cooldown", cooldownString), true);
            resetPresses();
            return true;
        }
        return false;
    }

    private void checkSpellKeys(PlayerEntity player) {
        ItemStack item = player.getMainHandStack();
        if (!item.isIn(ModTags.WANDS)) return;
        Wand wand = (Wand) item.getItem();

        checkKeyPresses(player, wand, item);
    }

    private void checkKeyPresses(PlayerEntity player, Wand wand, ItemStack itemStack) {
        if (checkKeyPress(ModKeyBindings.WAND_SPELL1, 1, player, wand, itemStack)) return;
        if (checkKeyPress(ModKeyBindings.WAND_SPELL2, 2, player, wand, itemStack)) return;
        if (checkKeyPress(ModKeyBindings.WAND_SPELL3, 3, player, wand, itemStack)) return;
        checkKeyPress(ModKeyBindings.WAND_SPELL4, 4, player, wand, itemStack);
    }

    private boolean checkKeyPress(KeyBinding keyBinding, int slotId, PlayerEntity player, Wand wand, ItemStack itemStack) {
        if (keyBinding.isPressed()) {
            if (checkSpellCooldown(player)) return true;

            wand.onSpellKeyPress(wand, itemStack, slotId, player);
            resetPress(keyBinding);
            return true;
        }
        return false;
    }

    private void resetPresses() {
        resetPress(ModKeyBindings.WAND_SPELL1);
        resetPress(ModKeyBindings.WAND_SPELL2);
        resetPress(ModKeyBindings.WAND_SPELL3);
        resetPress(ModKeyBindings.WAND_SPELL4);
    }

    private void resetPress(KeyBinding keyBinding) {
        while (keyBinding.wasPressed()) ;
    }

}
