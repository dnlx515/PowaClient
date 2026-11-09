package com.powaclient.module.modules;

import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;

public class PingModule extends Module
{
    public PingModule()
    {
        super("Ping", Category.HUD);
        this.hud = true;
    }

    private int ping()
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.isSingleplayer() || mc.getNetHandler() == null || mc.thePlayer == null)
        {
            return -1;
        }

        NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID());
        return info == null ? -1 : info.getResponseTime();
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();
        int p = ping();
        int y = this.hudY;

        if (p < 0)
        {
            return;
        }

        mc.fontRendererObj.drawStringWithShadow("Ping", this.hudX, y, Theme.ACCENT);
        mc.fontRendererObj.drawStringWithShadow(p + " ms",
                this.hudX + mc.fontRendererObj.getStringWidth("Ping "), y, Theme.ACCENT2);
    }
}
