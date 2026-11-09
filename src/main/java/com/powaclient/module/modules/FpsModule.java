package com.powaclient.module.modules;

import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class FpsModule extends Module
{
    public FpsModule()
    {
        super("FPS", Category.HUD);
        this.hud = true;
        this.setEnabled(true);
    }

    @Override
    public int hudW()
    {
        return 64;
    }

    @Override
    public int hudH()
    {
        return 18;
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();
        mc.fontRendererObj.drawStringWithShadow("PowaClient", this.hudX, this.hudY, Theme.ACCENT);
        mc.fontRendererObj.drawStringWithShadow(Minecraft.getDebugFPS() + " FPS", this.hudX, this.hudY + 10, Theme.ACCENT2);
    }
}
