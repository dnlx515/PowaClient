package com.powaclient.module.modules;

import com.powaclient.crosshair.CrosshairConfig;
import com.powaclient.crosshair.CrosshairRenderer;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.gui.ScaledResolution;

public class CrosshairModule extends Module
{
    public CrosshairModule()
    {
        super("Crosshair", Category.RENDER);
    }

    public void drawCrosshair(ScaledResolution res)
    {
        CrosshairRenderer.render(CrosshairConfig.getSettings(),
                res.getScaledWidth() / 2, res.getScaledHeight() / 2, 1.0F);
    }
}
