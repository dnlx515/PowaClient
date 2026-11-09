package com.powaclient.module.modules;

import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class MemoryModule extends Module
{
    public MemoryModule()
    {
        super("Memory", Category.HUD);
        this.hud = true;
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();
        Runtime rt = Runtime.getRuntime();
        long used = (rt.totalMemory() - rt.freeMemory()) / 1048576L;
        long max = rt.maxMemory() / 1048576L;

        int y = this.hudY;
        mc.fontRendererObj.drawStringWithShadow("Mem", this.hudX, y, Theme.ACCENT);
        mc.fontRendererObj.drawStringWithShadow(used + " / " + max + " MB",
                this.hudX + mc.fontRendererObj.getStringWidth("Mem "), y, Theme.ACCENT2);
    }
}
