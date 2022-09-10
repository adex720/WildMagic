package io.github.adex720.wildmagic.item;

import io.github.adex720.wildmagic.registry.ModItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class CoreItem extends Item {

    public CoreItem(Rarity rarity) {
        super(new FabricItemSettings().group(ModItemGroups.ITEMS).maxCount(1).rarity(rarity));
    }

    public CoreItem() {
        this(Rarity.UNCOMMON);
    }
}
