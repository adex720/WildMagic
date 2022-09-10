package io.github.adex720.wildmagic.registry;

import io.github.adex720.wildmagic.Util;
import io.github.adex720.wildmagic.wand.Wand;
import io.github.adex720.wildmagic.wand.WandToolMaterial;
import net.minecraft.util.registry.Registry;

public class ModWands {

    public static final Wand ACACIA_WAND = new Wand(WandToolMaterial.ACACIA);
    public static final Wand BIRCH_WAND = new Wand(WandToolMaterial.BIRCH);
    public static final Wand CRIMSON_WAND = new Wand(WandToolMaterial.CRIMSON);
    public static final Wand DARK_OAK_WAND = new Wand(WandToolMaterial.DARK_OAK);
    public static final Wand JUNGLE_WAND = new Wand(WandToolMaterial.JUNGLE);
    public static final Wand MANGROVE_WAND = new Wand(WandToolMaterial.MANGROVE);
    public static final Wand OAK_WAND = new Wand(WandToolMaterial.OAK);
    public static final Wand SPRUCE_WAND = new Wand(WandToolMaterial.SPRUCE);
    public static final Wand WARPED_WAND = new Wand(WandToolMaterial.WARPED);

    public static void registerWands() {
        register(ACACIA_WAND, "acacia_wand");
        register(BIRCH_WAND, "birch_wand");
        register(CRIMSON_WAND, "crimson_wand");
        register(DARK_OAK_WAND, "dark_oak_wand");
        register(JUNGLE_WAND, "jungle_wand");
        register(MANGROVE_WAND, "mangrove_wand");
        register(OAK_WAND, "oak_wand");
        register(SPRUCE_WAND, "spruce_wand");
        register(WARPED_WAND, "warped_wand");
    }

    private static void register(Wand wand, String id) {
        Registry.register(Registry.ITEM, Util.identifier(id), wand);
    }

}
