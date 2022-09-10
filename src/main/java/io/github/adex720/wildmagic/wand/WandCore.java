package io.github.adex720.wildmagic.wand;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;

public enum WandCore {

    NONE("none", Items.AIR),
    FEATHER("feather", Items.FEATHER),
    SPONGE("sponge", Items.SPONGE),
    STICK("stick", Items.DEAD_BUSH),
    PRISMARINE("prismarine", Items.PRISMARINE_SHARD),
    AMETHYST("amethyst", Items.AMETHYST_SHARD),
    DIAMOND("diamond", Items.DIAMOND),
    EMERALD("emerald", Items.EMERALD),
    DRAGON("dragon", Items.DRAGON_EGG);

    private static final HashMap<String, WandCore> WAND_CORES = new HashMap<>();

    static {
        for (WandCore core : WandCore.values()) {
            WAND_CORES.put(core.name, core);
        }
    }

    public final String name;
    public final Item item;

    WandCore(String name, Item item) {
        this.name = name;
        this.item = item;
    }

    public String getTranslationKey() {
        return "wand.core.wmag." + name;
    }

    public static WandCore getCore(String name) {
        return WAND_CORES.get(name);
    }
}
