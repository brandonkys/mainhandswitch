package io.github.brandonkys.mainhandswitch.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Arm;
import org.lwjgl.glfw.GLFW;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class MainHandSwitchClient implements ClientModInitializer {

    private KeyBinding keySwitchArm;

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(this.keySwitchArm = new KeyBinding("keybind.switchArm", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_T, "category.custom"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (this.keySwitchArm.wasPressed()) {
                Arm arm = client.options.getMainArm().getValue();
                if (arm == Arm.LEFT) {
                    client.options.getMainArm().setValue(Arm.RIGHT);
                    return;
                } else if (arm == Arm.RIGHT) {
                    client.options.getMainArm().setValue(Arm.LEFT);
                    return;
                }
                throw new IllegalArgumentException();
            }
        });
    }
}