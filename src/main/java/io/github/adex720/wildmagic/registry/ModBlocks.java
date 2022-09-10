package io.github.adex720.wildmagic.registry;

import io.github.adex720.wildmagic.Util;
import io.github.adex720.wildmagic.block.CoreCollector;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

/**
 * @author adex720
 */
public class ModBlocks {

    public static final Block PACKED_WOOD = new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F).sounds(BlockSoundGroup.WOOD));


    public static final Block WOODEN_CORE_COLLECTOR = new CoreCollector(FabricBlockSettings.copyOf(PACKED_WOOD), PACKED_WOOD, ModHarvestTools.WOODEN_SCYTHE, ModItems.WOODEN_CORE, 500);
    public static final Block STONE_CORE_COLLECTOR = new CoreCollector(FabricBlockSettings.copyOf(Blocks.STONE), Blocks.STONE, ModHarvestTools.STONE_SCYTHE, ModItems.STONE_CORE, 1000);
    public static final Block IRON_CORE_COLLECTOR = new CoreCollector(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK), Blocks.IRON_BLOCK, ModHarvestTools.IRON_SCYTHE, ModItems.IRON_CORE, 1500);
    public static final Block DIAMOND_CORE_COLLECTOR = new CoreCollector(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK), Blocks.DIAMOND_BLOCK, ModHarvestTools.DIAMOND_SCYTHE, ModItems.DIAMOND_CORE, 2000);
    public static final Block GOLDEN_CORE_COLLECTOR = new CoreCollector(FabricBlockSettings.copyOf(Blocks.GOLD_BLOCK), Blocks.GOLD_BLOCK, ModHarvestTools.GOLDEN_SCYTHE, ModItems.GOLDEN_CORE, 2000);
    public static final Block NETHERITE_CORE_COLLECTOR = new CoreCollector(FabricBlockSettings.copyOf(Blocks.NETHERITE_BLOCK), Blocks.NETHERITE_BLOCK, ModHarvestTools.NETHERITE_SCYTHE, ModItems.NETHERITE_CORE, 2500);
    public static final Block EMERALD_CORE_COLLECTOR = new CoreCollector(FabricBlockSettings.copyOf(Blocks.EMERALD_BLOCK), Blocks.EMERALD_BLOCK, ModHarvestTools.EMERALD_SCYTHE, ModItems.EMERALD_CORE, 2500);

    public static final Block DRAGON_HEART_COLLECTOR = new CoreCollector(FabricBlockSettings.copyOf(Blocks.OBSIDIAN), Blocks.DRAGON_EGG, ModHarvestTools.DRAGON_HEART_SCYTHE, ModItems.DRAGON_HEART, 5000);
    public static final Block BUSH_THORN_COLLECTOR = new CoreCollector(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS), Blocks.DEAD_BUSH, ModHarvestTools.WOODEN_SCYTHE, ModItems.BUSH_THORN, 5000);


    public static final BlockItem PACKED_WOOD_BLOCK_ITEM = new BlockItem(PACKED_WOOD, new FabricItemSettings().group(ModItemGroups.BLOCKS));

    public static final BlockItem WOODEN_CORE_COLLECTOR_BLOCK_ITEM = new BlockItem(WOODEN_CORE_COLLECTOR, new FabricItemSettings().group(ModItemGroups.BLOCKS));
    public static final BlockItem STONE_CORE_COLLECTOR_BLOCK_ITEM = new BlockItem(STONE_CORE_COLLECTOR, new FabricItemSettings().group(ModItemGroups.BLOCKS));
    public static final BlockItem IRON_CORE_COLLECTOR_BLOCK_ITEM = new BlockItem(IRON_CORE_COLLECTOR, new FabricItemSettings().group(ModItemGroups.BLOCKS));
    public static final BlockItem DIAMOND_CORE_COLLECTOR_BLOCK_ITEM = new BlockItem(DIAMOND_CORE_COLLECTOR, new FabricItemSettings().group(ModItemGroups.BLOCKS));
    public static final BlockItem GOLDEN_CORE_COLLECTOR_BLOCK_ITEM = new BlockItem(GOLDEN_CORE_COLLECTOR, new FabricItemSettings().group(ModItemGroups.BLOCKS));
    public static final BlockItem NETHERITE_CORE_COLLECTOR_BLOCK_ITEM = new BlockItem(NETHERITE_CORE_COLLECTOR, new FabricItemSettings().group(ModItemGroups.BLOCKS));
    public static final BlockItem EMERALD_CORE_COLLECTOR_BLOCK_ITEM = new BlockItem(EMERALD_CORE_COLLECTOR, new FabricItemSettings().group(ModItemGroups.BLOCKS));

    public static final BlockItem DRAGON_HEART_COLLECTOR_BLOCK_ITEM = new BlockItem(DRAGON_HEART_COLLECTOR, new FabricItemSettings().group(ModItemGroups.BLOCKS));
    public static final BlockItem BUSH_THORN_COLLECTOR_BLOCK_ITEM = new BlockItem(BUSH_THORN_COLLECTOR, new FabricItemSettings().group(ModItemGroups.BLOCKS));

    public static void registerBlocks() {
        registerBlock(PACKED_WOOD, "packed_wood");

        registerBlock(WOODEN_CORE_COLLECTOR, "wooden_core_collector");
        registerBlock(STONE_CORE_COLLECTOR, "stone_core_collector");
        registerBlock(IRON_CORE_COLLECTOR, "iron_core_collector");
        registerBlock(DIAMOND_CORE_COLLECTOR, "diamond_core_collector");
        registerBlock(GOLDEN_CORE_COLLECTOR, "golden_core_collector");
        registerBlock(NETHERITE_CORE_COLLECTOR, "netherite_core_collector");
        registerBlock(EMERALD_CORE_COLLECTOR, "emerald_core_collector");

        registerBlock(DRAGON_HEART_COLLECTOR, "dragon_heart_collector");
        registerBlock(BUSH_THORN_COLLECTOR, "bush_thorn_collector");

        registerBlockItems();
    }

    private static void registerBlockItems() {
        registerBlockItem(PACKED_WOOD_BLOCK_ITEM, "packed_wood");

        registerBlockItem(WOODEN_CORE_COLLECTOR_BLOCK_ITEM, "wooden_core_collector");
        registerBlockItem(STONE_CORE_COLLECTOR_BLOCK_ITEM, "stone_core_collector");
        registerBlockItem(IRON_CORE_COLLECTOR_BLOCK_ITEM, "iron_core_collector");
        registerBlockItem(DIAMOND_CORE_COLLECTOR_BLOCK_ITEM, "diamond_core_collector");
        registerBlockItem(GOLDEN_CORE_COLLECTOR_BLOCK_ITEM, "golden_core_collector");
        registerBlockItem(NETHERITE_CORE_COLLECTOR_BLOCK_ITEM, "netherite_core_collector");
        registerBlockItem(EMERALD_CORE_COLLECTOR_BLOCK_ITEM, "emerald_core_collector");

        registerBlockItem(DRAGON_HEART_COLLECTOR_BLOCK_ITEM, "dragon_heart_collector");
        registerBlockItem(BUSH_THORN_COLLECTOR_BLOCK_ITEM, "bush_thorn_collector");
    }

    private static void registerBlock(Block block, String path) {
        Registry.register(Registry.BLOCK, Util.identifier(path), block);
    }

    private static void registerBlockItem(BlockItem blockItem, String path) {
        Registry.register(Registry.ITEM, Util.identifier(path), blockItem);
    }

}
