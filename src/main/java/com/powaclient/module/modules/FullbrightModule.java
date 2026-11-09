package com.powaclient.module.modules;

import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;

public class FullbrightModule extends Module
{
    private float previousGamma = 1.0F;

    public FullbrightModule()
    {
        super("Fullbright", Category.MISC);
    }

    @Override
    protected void onEnable()
    {
        Minecraft mc = Minecraft.getMinecraft();
        this.previousGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100.0F;
    }

    @Override
    protected void onDisable()
    {
        Minecraft.getMinecraft().gameSettings.gammaSetting = this.previousGamma;
    }
}
