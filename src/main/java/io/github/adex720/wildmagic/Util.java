package io.github.adex720.wildmagic;

import io.github.adex720.wildmagic.entrypoint.WildMagic;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

/**
 * @author adex720
 */
public class Util {

    public static final float ONE_PER_256 = 1.0f / 256;
    public static final double PI_PER_TWO = Math.PI * 0.5d;

    public static Identifier identifier(String path) {
        return Identifier.of(WildMagic.MODID, path);
    }

    public static boolean shouldRegenerateMagic(int magicLevel, Random random) {
        int change;
        if (magicLevel < 5) change = 1;
        else if (magicLevel < 10) change = 2;
        else if (magicLevel < 20) change = 3;
        else if (magicLevel < 30) change = 4;
        else if (magicLevel < 60) change = 5;
        else if (magicLevel < 70) change = 6;
        else if (magicLevel < 80) change = 4;
        else if (magicLevel < 90) change = 7;
        else change = 8;
        return random.nextInt(20) < change;
    }

}
