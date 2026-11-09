package com.powaclient.module.modules;

import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;

public class ToggleSprintModule extends Module
{
    public ToggleSprintModule()
    {
        super("Toggle Sprint", Category.PLAYER);
    }

    @Override
    public void onTick()
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null)
        {
            return;
        }

        if (mc.thePlayer.movementInput.moveForward > 0.0F && !mc.thePlayer.isSneaking())
        {
            mc.thePlayer.setSprinting(true);
        }
    }
}
