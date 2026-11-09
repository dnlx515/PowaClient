package com.powaclient.module.modules;

import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ServerModule extends Module
{
    public ServerModule()
    {
        super("Server", Category.HUD);
        this.hud = true;
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();
        String address = mc.isSingleplayer() || mc.getCurrentServerData() == null
                ? "Singleplayer"
                : mc.getCurrentServerData().serverIP;

        int y = this.hudY;
        mc.fontRendererObj.drawStringWithShadow("Server", this.hudX, y, Theme.ACCENT);
        mc.fontRendererObj.drawStringWithShadow(address,
                this.hudX + mc.fontRendererObj.getStringWidth("Server "), y, Theme.ACCENT2);
    }
}
