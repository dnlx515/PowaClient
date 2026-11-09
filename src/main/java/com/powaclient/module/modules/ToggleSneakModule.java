package com.powaclient.module.modules;

import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

/**
 * Tap the sneak key to toggle sneaking (like Lunar's Toggle Sneak).
 */
public class ToggleSneakModule extends Module
{
    private boolean sneaking;

    public ToggleSneakModule()
    {
        super("Toggle Sneak", Category.PLAYER);
    }

    @Override
    public void onKeyInput()
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.gameSettings == null)
        {
            return;
        }

        if (Keyboard.getEventKey() == mc.gameSettings.keyBindSneak.getKeyCode() && Keyboard.getEventKeyState())
        {
            this.sneaking = !this.sneaking;
        }
    }

    @Override
    public void onTick()
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.gameSettings == null)
        {
            return;
        }

        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), this.sneaking);
    }

    @Override
    protected void onDisable()
    {
        this.sneaking = false;
    }
}
