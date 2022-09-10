package io.github.adex720.wildmagic.registry;

import io.github.adex720.wildmagic.Util;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class ModTags {

    public static final TagKey<Item> WANDS = TagKey.of(Registry.ITEM_KEY, Util.identifier("wands"));
    public static final TagKey<Item> HARVEST_TOOLS = TagKey.of(Registry.ITEM_KEY, Util.identifier("harvest_tools"));

    public static final TagKey<Block> SPELL_TABLES = TagKey.of(Registry.BLOCK_KEY, Util.identifier("spell_tables"));


    public static void registerTags() {
    }
}
