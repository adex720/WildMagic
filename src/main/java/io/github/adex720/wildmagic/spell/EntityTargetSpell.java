package io.github.adex720.wildmagic.spell;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * A spell which can be used on entities.
 *
 * @deprecated
 * @author adex720
 */
public abstract class EntityTargetSpell extends ObjectTargetSpell {

    protected EntityTargetSpell(String name, int color, double range, int intelligenceRequirement, int price, int consumption, int cooldown) {
        super(name, color, range, intelligenceRequirement, price, consumption, cooldown);
    }

    public Entity getTargetEntity(MinecraftClient client, ItemStack wand) {
        return calculateTarget(client, wand).getEntity();
    }

}
