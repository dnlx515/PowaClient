package com.powaclient.module.modules;

import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class CoordinatesModule extends Module
{
    public CoordinatesModule()
    {
        super("Coordinates", Category.HUD);
        this.hud = true;
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();

        int y = this.hudY;

        if (mc.thePlayer == null)
        {
            return;
        }

        String value = String.format("%.1f  %.1f  %.1f", mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);

        mc.fontRendererObj.drawStringWithShadow("XYZ", this.hudX, y, Theme.ACCENT);
        mc.fontRendererObj.drawStringWithShadow(value,
                this.hudX + mc.fontRendererObj.getStringWidth("XYZ "), y, Theme.ACCENT2);
    }
}
