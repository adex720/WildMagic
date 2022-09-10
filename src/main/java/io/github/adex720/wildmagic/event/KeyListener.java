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

/**
 * Registers and handles key listeners.
 * Key press statuses are only accessible from the client.
 *
 * @author adex720
 */
public class KeyListener {

    public KeyListener() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onEndClientTick);
    }

    private void onEndClientTick(MinecraftClient client) {
        if (client.isPaused()) return; // Not counting presses on menus, etc.
        if (client.player == null) return; // Player can be null during initialization
        checkSpellKeys(client.player);
    }

    /**
     * Returns true if the player has spell usability on cooldown.
     * Displays amount of cooldown left for the player if it exists.
     */
    private boolean checkSpellCooldown(PlayerEntity player) {
        int cooldown = (int) player.getAttributeValue(ModAttributes.SPELL_COOLDOWN);
        if (cooldown > 0) {
            String cooldownString = Float.toString(cooldown * 0.05f + 0.0001f).substring(0, 4);
            player.sendMessage(Text.translatable("wand.wmag.cooldown", cooldownString), true);
            resetSpellKeyPresses();
            return true;
        }
        return false;
    }

    /**
     * Checks if the player is allowed to use spells and checks for key presses if yes.
     */
    private void checkSpellKeys(PlayerEntity player) {
        ItemStack item = player.getMainHandStack();
        if (!item.isIn(ModTags.WANDS)) return;
        Wand wand = (Wand) item.getItem();

        checkSpellKeyPresses(player, wand, item);
    }

    /**
     * Checks all spell key presses for the player.
     * If a spell is cast, rest of the keys are ignored.
     *
     * @param player    Player to check key bindings from
     * @param wand      The wand the player is holding
     * @param itemStack The ItemStack of the wand
     */
    private void checkSpellKeyPresses(PlayerEntity player, Wand wand, ItemStack itemStack) {
        if (checkSpellKeyPress(ModKeyBindings.WAND_SPELL1, 1, player, wand, itemStack)) return;
        if (checkSpellKeyPress(ModKeyBindings.WAND_SPELL2, 2, player, wand, itemStack)) return;
        if (checkSpellKeyPress(ModKeyBindings.WAND_SPELL3, 3, player, wand, itemStack)) return;
        checkSpellKeyPress(ModKeyBindings.WAND_SPELL4, 4, player, wand, itemStack);
    }

    /**
     * Checks spell key press for one slot.
     *
     * @param keyBinding KeyBinding to check
     * @param slotId     Id of the spell slot
     * @param player     Player to check key bindings from
     * @param wand       The wand the player is holding
     * @param itemStack  The ItemStack of the wand
     * @return true if a spell was cast
     */
    private boolean checkSpellKeyPress(KeyBinding keyBinding, int slotId, PlayerEntity player, Wand wand, ItemStack itemStack) {
        if (keyBinding.isPressed()) {
            if (checkSpellCooldown(player)) return true;

            wand.onSpellKeyPress(wand, itemStack, slotId, player);
            resetPress(keyBinding);
            return true;
        }
        return false;
    }

    /**
     * Resets all queued key presses for all spell keys.
     */
    private void resetSpellKeyPresses() {
        resetPress(ModKeyBindings.WAND_SPELL1);
        resetPress(ModKeyBindings.WAND_SPELL2);
        resetPress(ModKeyBindings.WAND_SPELL3);
        resetPress(ModKeyBindings.WAND_SPELL4);
    }

    /**
     * Resets all queued key presses from the key binding.
     */
    private void resetPress(KeyBinding keyBinding) {
        while (keyBinding.wasPressed()) ;
    }

}
