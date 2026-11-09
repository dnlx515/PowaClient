package com.powaclient.module.modules;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ClockModule extends Module
{
    private final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");

    public ClockModule()
    {
        super("Clock", Category.HUD);
        this.hud = true;
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();
        int y = this.hudY;
        mc.fontRendererObj.drawStringWithShadow("Time", this.hudX, y, Theme.ACCENT);
        mc.fontRendererObj.drawStringWithShadow(this.fmt.format(new Date()),
                this.hudX + mc.fontRendererObj.getStringWidth("Time "), y, Theme.ACCENT2);
    }
}
