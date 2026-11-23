package com.powaclient.module.modules;

import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;

/**
 * Removes the camera shake when taking damage while keeping the red damage flash.
 */
public class NoHurtCamModule extends Module
{
    private static final int MAX_HURT_TIME = 10000;

    public NoHurtCamModule()
    {
        super("No Hurt Cam", Category.RENDER);
    }

    @Override
    public void onTick()
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null)
        {
            return;
        }

        mc.thePlayer.attackedAtYaw = 0.0F;
        mc.thePlayer.maxHurtTime = MAX_HURT_TIME;
    }
}
