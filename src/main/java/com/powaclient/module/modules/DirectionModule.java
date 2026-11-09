package com.powaclient.module.modules;

import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class DirectionModule extends Module
{
    public DirectionModule()
    {
        super("Direction", Category.HUD);
        this.hud = true;
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null)
        {
            return;
        }

        String dir;

        switch (mc.thePlayer.getHorizontalFacing())
        {
            case NORTH: dir = "N"; break;
            case SOUTH: dir = "S"; break;
            case EAST: dir = "E"; break;
            default: dir = "W"; break;
        }

        int y = this.hudY;
        mc.fontRendererObj.drawStringWithShadow("Facing", this.hudX, y, Theme.ACCENT);
        mc.fontRendererObj.drawStringWithShadow(dir,
                this.hudX + mc.fontRendererObj.getStringWidth("Facing "), y, Theme.ACCENT2);
    }
}
