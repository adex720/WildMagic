package io.github.adex720.wildmagic.registry;

import io.github.adex720.wildmagic.spell.EntityTargetStatusEffectSpell;
import io.github.adex720.wildmagic.spell.SelfTargetStatusEffectSpell;
import io.github.adex720.wildmagic.spell.Spell;
import io.github.adex720.wildmagic.spell.block.BlockOnFireSpell;
import io.github.adex720.wildmagic.spell.object.DragonBreathSpell;
import net.minecraft.entity.effect.StatusEffects;

/**
 * @author adex720
 */
public class ModSpells {

    public static final Spell SPEED = new SelfTargetStatusEffectSpell(StatusEffects.SPEED, 100, 1, "spell_speed", 0, 0, 0);

    public static final Spell GLOWING = new EntityTargetStatusEffectSpell(StatusEffects.GLOWING, 160, 1, "spell_glowing", 20, 0, 3, 0);
    public static final Spell SLOWNESS = new EntityTargetStatusEffectSpell(StatusEffects.SLOWNESS, 100, 1, "spell_slowness", 5, 1, 5, 0);
    public static final Spell MINING_FATIGUE = new EntityTargetStatusEffectSpell(StatusEffects.MINING_FATIGUE, 75, 1, "spell_mining_fatigue", 5, 3, 12, 0);
    public static final Spell WEAKNESS = new EntityTargetStatusEffectSpell(StatusEffects.WEAKNESS, 75, 1, "spell_weakness", 5, 3, 12, 0);

    public static final Spell SPARK = new BlockOnFireSpell("spark", 0xd1ad0d, 25, 0, 5, 0, 20);

    public static final Spell DRAGON_BREATH = new DragonBreathSpell("dragon_breath", 0x6a0b8f, 15, 0,50, 1, 30); //TODO: remove debug values

    private static final Spell[] SPELLS = new Spell[Spell.getSpellCount()];

    public static void registerWands() {
        register(SPEED);

        register(GLOWING);
        register(SLOWNESS);
        register(MINING_FATIGUE);
        register(WEAKNESS);

        register(SPARK);

        register(DRAGON_BREATH);
    }

    private static void register(Spell spell) {
        SPELLS[spell.id] = spell;
    }

    public static Spell get(int id) {
        return SPELLS[id];
    }

}
