package io.github.adex720.wildmagic.registry;

import io.github.adex720.wildmagic.Util;
import io.github.adex720.wildmagic.harvest.HarvestTool;
import io.github.adex720.wildmagic.harvest.HarvestToolMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModHarvestTools {

    public static final HarvestTool WOODEN_SCYTHE = new HarvestTool(ToolMaterials.WOOD, new FabricItemSettings().group(ModItemGroups.TOOLS).rarity(Rarity.COMMON));
    public static final HarvestTool STONE_SCYTHE = new HarvestTool(ToolMaterials.STONE, new FabricItemSettings().group(ModItemGroups.TOOLS).rarity(Rarity.COMMON));
    public static final HarvestTool IRON_SCYTHE = new HarvestTool(ToolMaterials.IRON, new FabricItemSettings().group(ModItemGroups.TOOLS).rarity(Rarity.COMMON));
    public static final HarvestTool DIAMOND_SCYTHE = new HarvestTool(ToolMaterials.DIAMOND, new FabricItemSettings().group(ModItemGroups.TOOLS).rarity(Rarity.COMMON));
    public static final HarvestTool GOLDEN_SCYTHE = new HarvestTool(ToolMaterials.GOLD, new FabricItemSettings().group(ModItemGroups.TOOLS).rarity(Rarity.COMMON));
    public static final HarvestTool NETHERITE_SCYTHE = new HarvestTool(ToolMaterials.NETHERITE, new FabricItemSettings().group(ModItemGroups.TOOLS).rarity(Rarity.COMMON));
    public static final HarvestTool EMERALD_SCYTHE = new HarvestTool(HarvestToolMaterial.EMERALD, new FabricItemSettings().group(ModItemGroups.TOOLS).rarity(Rarity.COMMON));



    public static final HarvestTool DRAGON_HEART_SCYTHE = new HarvestTool(HarvestToolMaterial.DRAGON_HEART, new FabricItemSettings().group(ModItemGroups.TOOLS).maxDamage(5).rarity(Rarity.UNCOMMON));

    public static void registerHarvestTools() {
        register(WOODEN_SCYTHE, "wooden_scythe");
        register(STONE_SCYTHE, "stone_scythe");
        register(IRON_SCYTHE, "iron_scythe");
        register(DIAMOND_SCYTHE, "diamond_scythe");
        register(GOLDEN_SCYTHE, "golden_scythe");
        register(NETHERITE_SCYTHE, "netherite_scythe");
        register(EMERALD_SCYTHE, "emerald_scythe");

        register(DRAGON_HEART_SCYTHE, "dragon_heart_scythe");
    }

    private static void register(HarvestTool harvestTool, String path){
        Registry.register(Registry.ITEM, Util.identifier(path), harvestTool);
    }

}
