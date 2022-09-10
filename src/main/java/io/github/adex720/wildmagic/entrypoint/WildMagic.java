package io.github.adex720.wildmagic.entrypoint;

import io.github.adex720.wildmagic.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Initializes everything needed for both client and server.
 *
 * @author adex720
 */
public class WildMagic implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(WildMagic.class);

    public static final String MODID = "wmag";

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {}", MODID);

        ModItems.registerItems();
        ModHarvestTools.registerHarvestTools();
        ModWands.registerWands();

        ModBlocks.registerBlocks();

        ModAttributes.registerAttributes();

        ModEnchantments.registerEnchantments();

        ModTags.registerTags();

        ModSpells.registerWands();
        LOGGER.info("Finished initializing {}", MODID);
    }

}
