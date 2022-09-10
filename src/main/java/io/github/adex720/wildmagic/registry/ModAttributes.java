package io.github.adex720.wildmagic.registry;

import io.github.adex720.wildmagic.Util;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;

public class ModAttributes {

    public static final EntityAttribute INTELLIGENCE = new ClampedEntityAttribute("attribute.name.wmag.intelligence", 1d, 1d, 100d);
    public static final EntityAttribute MAGIC_LEVEL = new ClampedEntityAttribute("attribute.name.wmag.magic_level", 0d, 0d, 15d);
    public static final EntityAttribute MAGIC_CAP = new ClampedEntityAttribute("attribute.name.wmag.magic_cap", 25d, 25d, 1024d);
    public static final EntityAttribute MAGIC = new ClampedEntityAttribute("attribute.name.wmag.magic", 25d, 0d, 1024d);
    public static final EntityAttribute SPELL_COOLDOWN = new ClampedEntityAttribute("attribute.name.wmag.spell_cooldown", 0d, 0d, 600d);

    public static void registerAttributes() {
        register(INTELLIGENCE, "intelligence");
        register(MAGIC_LEVEL, "magic_level");
        register(MAGIC_CAP, "magic_cap");
        register(MAGIC, "magic");
        register(SPELL_COOLDOWN, "spell_cooldown");
    }

    private static void register(EntityAttribute attribute, String path) {
        Registry.register(Registry.ATTRIBUTE, Util.identifier(path), attribute);
    }

}
