package io.github.adex720.wildmagic.event;

import io.github.adex720.wildmagic.Util;
import io.github.adex720.wildmagic.registry.ModAttributes;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.util.math.random.Random;

/**
 * Updates magic related attributes for each player on each tick.
 *
 * @author adex720
 */
public class MagicAttributeUpdater {

    public MagicAttributeUpdater() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onEndClientTick);
    }

    private void onEndClientTick(MinecraftClient client) {
        if(client.isPaused()) return;
        if (client.player == null) return;
        AttributeContainer attributes = client.player.getAttributes();

        increaseMagic(attributes, client.world.random);
        reduceSpellCooldown(attributes);
    }

    private void increaseMagic(AttributeContainer attributes, Random random) {
        double magic = attributes.getValue(ModAttributes.MAGIC);
        double maxMagic = attributes.getValue(ModAttributes.MAGIC_CAP);

        if (magic >= maxMagic) return;
        System.out.println(magic);

        int magicLevel = (int) attributes.getValue(ModAttributes.MAGIC_LEVEL);
        if (Util.shouldRegenerateMagic(magicLevel, random)) {
            System.out.println("INCREMENT");
            attributes.getCustomInstance(ModAttributes.MAGIC).setBaseValue(magic + 1);
        }
    }

    private void reduceSpellCooldown(AttributeContainer attributes) {
        EntityAttributeInstance cooldownAttribute = attributes.getCustomInstance(ModAttributes.SPELL_COOLDOWN);
        double currentCooldown = cooldownAttribute.getValue();
        if (currentCooldown == 0d) return;
        cooldownAttribute.setBaseValue(currentCooldown - 1d);
    }
}
