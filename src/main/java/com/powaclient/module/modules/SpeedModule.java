package com.powaclient.module.modules;

import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class SpeedModule extends Module
{
    private double lastX;
    private double lastZ;
    private boolean init;
    private double bps;

    public SpeedModule()
    {
        super("Speed", Category.HUD);
        this.hud = true;
    }

    @Override
    public void onTick()
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null)
        {
            return;
        }

        if (!this.init)
        {
            this.lastX = mc.thePlayer.posX;
            this.lastZ = mc.thePlayer.posZ;
            this.init = true;
            return;
        }

        double dx = mc.thePlayer.posX - this.lastX;
        double dz = mc.thePlayer.posZ - this.lastZ;
        this.bps = Math.sqrt(dx * dx + dz * dz) * 20.0D;
        this.lastX = mc.thePlayer.posX;
        this.lastZ = mc.thePlayer.posZ;
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();
        int y = this.hudY;
        mc.fontRendererObj.drawStringWithShadow("Speed", this.hudX, y, Theme.ACCENT);
        mc.fontRendererObj.drawStringWithShadow(String.format("%.1f b/s", this.bps),
                this.hudX + mc.fontRendererObj.getStringWidth("Speed "), y, Theme.ACCENT2);
    }
}
