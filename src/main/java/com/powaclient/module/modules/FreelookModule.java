package com.powaclient.module.modules;

import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import org.lwjgl.input.Keyboard;

/**
 * Hold the key to look around; release to snap the view back.
 */
public class FreelookModule extends Module
{
    private final KeyBinding key;
    private boolean active;
    private float savedYaw;
    private float savedPitch;

    public FreelookModule()
    {
        super("Freelook", Category.PLAYER);

        this.key = new KeyBinding("Freelook", Keyboard.KEY_V, "PowaClient");
        ClientRegistry.registerKeyBinding(this.key);
    }

    @Override
    public void onTick()
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null)
        {
            return;
        }

        boolean down = this.key.isKeyDown();

        if (down && !this.active)
        {
            this.savedYaw = mc.thePlayer.rotationYaw;
            this.savedPitch = mc.thePlayer.rotationPitch;
            this.active = true;
        }
        else if (!down && this.active)
        {
            mc.thePlayer.rotationYaw = this.savedYaw;
            mc.thePlayer.rotationPitch = this.savedPitch;
            this.active = false;
        }
    }

    @Override
    protected void onDisable()
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (this.active && mc.thePlayer != null)
        {
            mc.thePlayer.rotationYaw = this.savedYaw;
            mc.thePlayer.rotationPitch = this.savedPitch;
        }

        this.active = false;
    }
}
