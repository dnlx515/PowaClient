package com.powaclient.module.modules;

import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import org.lwjgl.input.Keyboard;

/**
 * Hold the key to zoom in (reduces the FOV); release to restore.
 */
public class ZoomModule extends Module
{
    private final KeyBinding key;
    private boolean active;
    private float savedFov;

    public ZoomModule()
    {
        super("Zoom", Category.PLAYER);

        this.key = new KeyBinding("Zoom", Keyboard.KEY_C, "PowaClient");
        ClientRegistry.registerKeyBinding(this.key);
    }

    @Override
    public void onTick()
    {
        Minecraft mc = Minecraft.getMinecraft();
        boolean down = this.key.isKeyDown();

        if (down && !this.active)
        {
            this.savedFov = mc.gameSettings.fovSetting;
            this.active = true;
            mc.gameSettings.fovSetting = Math.max(10.0F, this.savedFov / 4.0F);
        }
        else if (!down && this.active)
        {
            mc.gameSettings.fovSetting = this.savedFov;
            this.active = false;
        }
    }

    @Override
    protected void onDisable()
    {
        if (this.active)
        {
            Minecraft.getMinecraft().gameSettings.fovSetting = this.savedFov;
            this.active = false;
        }
    }
}
