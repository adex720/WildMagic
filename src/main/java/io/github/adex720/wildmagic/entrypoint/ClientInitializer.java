package io.github.adex720.wildmagic.entrypoint;

import io.github.adex720.wildmagic.event.KeyListener;
import io.github.adex720.wildmagic.event.MagicAttributeUpdater;
import io.github.adex720.wildmagic.registry.ModKeyBindings;
import net.fabricmc.api.ClientModInitializer;

/**
 * Initializes everything needed for the client.
 *
 * @author adex720
 */
public class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WildMagic.LOGGER.info("Initializing {} for client", WildMagic.MODID);

        ModKeyBindings.registerKeyBindings();

        MagicAttributeUpdater magicAttributeUpdater = new MagicAttributeUpdater();
        KeyListener keyListener = new KeyListener();

        WildMagic.LOGGER.info("Finished initializing {} for client", WildMagic.MODID);
    }
}
