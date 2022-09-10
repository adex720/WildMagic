package io.github.adex720.wildmagic.harvest;

import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;

/**
 * Harvest tools are used to collect {@link io.github.adex720.wildmagic.item.CoreItem}s
 * from {@link io.github.adex720.wildmagic.block.CoreCollector}s.
 *
 * @author adex720
 */
public class HarvestTool extends ToolItem {

    public HarvestTool(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

}
