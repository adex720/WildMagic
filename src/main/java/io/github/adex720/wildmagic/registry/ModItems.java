package io.github.adex720.wildmagic.registry;

import io.github.adex720.wildmagic.Util;
import io.github.adex720.wildmagic.item.CoreItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

/**
 * @author adex720
 */
public class ModItems {

    public static final Item COLLECTOR_CORE = new Item(new FabricItemSettings().group(ModItemGroups.ITEMS).rarity(Rarity.UNCOMMON));

    public static final CoreItem WOODEN_CORE = new CoreItem();
    public static final CoreItem STONE_CORE = new CoreItem();
    public static final CoreItem IRON_CORE = new CoreItem();
    public static final CoreItem DIAMOND_CORE = new CoreItem();
    public static final CoreItem GOLDEN_CORE = new CoreItem();
    public static final CoreItem NETHERITE_CORE = new CoreItem();
    public static final CoreItem EMERALD_CORE = new CoreItem();

    public static final CoreItem DRAGON_HEART = new CoreItem(Rarity.RARE);
    public static final CoreItem BUSH_THORN = new CoreItem();

    public static void registerItems() {
        registerItem(COLLECTOR_CORE, "collector_core");

        registerItem(WOODEN_CORE, "wooden_core");
        registerItem(STONE_CORE, "stone_core");
        registerItem(IRON_CORE, "iron_core");
        registerItem(DIAMOND_CORE, "diamond_core");
        registerItem(GOLDEN_CORE, "golden_core");
        registerItem(NETHERITE_CORE, "netherite_core");
        registerItem(EMERALD_CORE, "emerald_core");

        registerItem(BUSH_THORN, "bush_thorn");
        registerItem(DRAGON_HEART, "dragon_heart");
    }

    private static void registerItem(Item item, String path) {
        Registry.register(Registry.ITEM, Util.identifier(path), item);

    }
}
