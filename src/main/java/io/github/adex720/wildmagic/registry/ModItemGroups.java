package io.github.adex720.wildmagic.registry;

import io.github.adex720.wildmagic.Util;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {

    public static final ItemGroup WANDS = FabricItemGroupBuilder.build(
            Util.identifier("wands"),
            () -> new ItemStack(ModWands.OAK_WAND));

    public static final ItemGroup ITEMS = FabricItemGroupBuilder.build(
            Util.identifier("items"),
            () -> new ItemStack(ModWands.OAK_WAND));

    public static final ItemGroup BLOCKS = FabricItemGroupBuilder.build(
            Util.identifier("blocks"),
            () -> new ItemStack(ModWands.OAK_WAND));

    public static final ItemGroup TOOLS = FabricItemGroupBuilder.build(
            Util.identifier("tools"),
            () -> new ItemStack(ModWands.OAK_WAND));

}
