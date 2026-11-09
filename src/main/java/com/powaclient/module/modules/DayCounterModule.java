package com.powaclient.module.modules;

import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class DayCounterModule extends Module
{
    public DayCounterModule()
    {
        super("Day", Category.HUD);
        this.hud = true;
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.theWorld == null)
        {
            return;
        }

        long day = mc.theWorld.getWorldTime() / 24000L;
        int y = this.hudY;
        mc.fontRendererObj.drawStringWithShadow("Day", this.hudX, y, Theme.ACCENT);
        mc.fontRendererObj.drawStringWithShadow(String.valueOf(day),
                this.hudX + mc.fontRendererObj.getStringWidth("Day "), y, Theme.ACCENT2);
    }
}
