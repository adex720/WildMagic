package io.github.adex720.wildmagic.registry;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {

    public static final KeyBinding WAND_SPELL1 = new KeyBinding("key.wmag.spell1", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y, "category.wmag.spell");
    public static final KeyBinding WAND_SPELL2 = new KeyBinding("key.wmag.spell2", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_U, "category.wmag.spell");
    public static final KeyBinding WAND_SPELL3 = new KeyBinding("key.wmag.spell3", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "category.wmag.spell");
    public static final KeyBinding WAND_SPELL4 = new KeyBinding("key.wmag.spell4", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J, "category.wmag.spell");

    public static void registerKeyBindings(){
        register(WAND_SPELL1);
        register(WAND_SPELL2);
        register(WAND_SPELL3);
        register(WAND_SPELL4);
    }

    private static void register(KeyBinding keyBinding){
        KeyBindingHelper.registerKeyBinding(keyBinding);
    }

}
