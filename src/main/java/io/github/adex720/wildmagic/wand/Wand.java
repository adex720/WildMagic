package io.github.adex720.wildmagic.wand;

import io.github.adex720.wildmagic.registry.ModEnchantments;
import io.github.adex720.wildmagic.registry.ModItemGroups;
import io.github.adex720.wildmagic.registry.ModSpells;
import io.github.adex720.wildmagic.registry.ModTags;
import io.github.adex720.wildmagic.spell.Spell;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Wands are used to cast spells.
 * A wand can hold 4 different spells at the same time.
 * The spells are used by pressing a key on keyboard.
 *
 * Each wand has one core. The core is used when crafting the wand.
 *
 * @author adex720
 */
public class Wand extends ToolItem {

    public final WandToolMaterial wandMaterial;
    private WandCore core;

    public Wand(WandToolMaterial wandMaterial) {
        super(wandMaterial, new FabricItemSettings()
                .rarity(Rarity.UNCOMMON)
                .maxCount(1)
                .group(ModItemGroups.WANDS));

        this.wandMaterial = wandMaterial;
        this.core = WandCore.NONE;
    }

    public void setCore(WandCore core) {
        this.core = core;
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockState target = context.getWorld().getBlockState(context.getBlockPos());
        if (target.isIn(ModTags.SPELL_TABLES)) {
            ItemStack wand = context.getStack();
            setSpell(wand, 1, ModSpells.GLOWING);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public void setSpell(ItemStack wand, int slotId, Spell spell) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("wmag.slot" + slotId, spell.id);
        wand.setNbt(nbt);
    }

    public boolean hasSpells(ItemStack wand) {
        NbtCompound nbt = wand.getNbt();
        if (nbt == null) return false;

        return nbt.contains("wmag.slot1") || nbt.contains("wmag.slot2") || nbt.contains("wmag.slot3") || nbt.contains("wmag.slot4");
    }

    public boolean hasSpellInSlot(ItemStack wand, int slotId) {
        NbtCompound nbt = wand.getNbt();
        if (nbt == null) return false;
        return nbt.contains("wmag.slot" + slotId);
    }

    public Spell getSpellInSlot(ItemStack wand, int slotId) {
        NbtCompound nbt = wand.getNbt();
        if (nbt == null) return null;
        String key = "wmag.slot" + slotId;
        if (!nbt.contains(key)) return null;
        int spellId = nbt.getInt(key);
        return ModSpells.get(spellId);
    }

    public void onSpellKeyPress(Wand wand, ItemStack itemStack, int slotId, PlayerEntity player) {
        if (!hasSpellInSlot(itemStack, slotId)) return;
        Spell spell = getSpellInSlot(itemStack, slotId);
        if (!spell.canUse(player)) return;
        if (!spell.cast(player, (ClientWorld) player.world, itemStack)) return; // Spell failed to cast (ex. No target found)
        spell.afterUsed(player, wand, itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.wmag.wand.core", Text.translatable(core.getTranslationKey())));

        if (hasSpells(stack)) {
            for (int i = 1; i <= 4; i++) {
                if (!hasSpellInSlot(stack, i)) continue;
                Spell spell = getSpellInSlot(stack, i);
                tooltip.add(Text.translatable("tooltip.wmag.wand.spell", i, Text.translatable("spell.wmag." + spell.name)));
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public boolean damage(ItemStack wand, int amount, Random random, @Nullable ServerPlayerEntity player) {
        if (amount <= 0) return false;
        int enduranceLevel = EnchantmentHelper.getLevel(ModEnchantments.ENDURANCE, wand);

        if (enduranceLevel > 0) {
            int preventedDamage = 0;
            for (int i = 0; i < amount; i++) {
                if (UnbreakingEnchantment.shouldPreventDamage(wand, enduranceLevel, random)) {
                    preventedDamage++;
                }
            }
            if (preventedDamage >= amount) return false;
            amount -= preventedDamage;
        }

        if (player != null) {
            Criteria.ITEM_DURABILITY_CHANGED.trigger(player, wand, wand.getDamage() + amount);
        }

        int newDamage = wand.getDamage() + amount;
        wand.setDamage(newDamage);
        return newDamage >= wand.getMaxDamage();
    }
}
