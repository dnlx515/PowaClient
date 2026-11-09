package com.powaclient.module.modules;

import com.powaclient.Theme;
import com.powaclient.gui.RenderUtil;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Mouse;

public class KeystrokesModule extends Module
{
    public KeystrokesModule()
    {
        super("Keystrokes", Category.HUD);
        this.hud = true;
    }

    @Override
    public int hudW()
    {
        return 81;
    }

    @Override
    public int hudH()
    {
        return 63;
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();

        int size = 18;
        int gap = 3;
        int x = this.hudX;
        int y = this.hudY;

        drawKey(mc, x + size + gap, y, size, size, "W", mc.gameSettings.keyBindForward.isKeyDown());
        drawKey(mc, x, y + size + gap, size, size, "A", mc.gameSettings.keyBindLeft.isKeyDown());
        drawKey(mc, x + size + gap, y + size + gap, size, size, "S", mc.gameSettings.keyBindBack.isKeyDown());
        drawKey(mc, x + (size + gap) * 2, y + size + gap, size, size, "D", mc.gameSettings.keyBindRight.isKeyDown());

        int wide = size * 2 + gap;
        drawKey(mc, x, y + (size + gap) * 2, wide, size, "LMB", Mouse.isButtonDown(0));
        drawKey(mc, x + wide + gap, y + (size + gap) * 2, wide, size, "RMB", Mouse.isButtonDown(1));
    }

    private void drawKey(Minecraft mc, int x, int y, int w, int h, String label, boolean pressed)
    {
        RenderUtil.roundedRect(x, y, w, h, 4, pressed ? Theme.ACCENT : 0x80000000);
        mc.fontRendererObj.drawStringWithShadow(label,
                x + w / 2 - mc.fontRendererObj.getStringWidth(label) / 2,
                y + h / 2 - 4,
                pressed ? Theme.BG_SOLID : Theme.TEXT);
    }
}
