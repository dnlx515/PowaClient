package com.powaclient.module.modules;

import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

/**
 * Armor status HUD (helmet down to boots). Draggable like any HUD element.
 */
public class ArmorModule extends Module
{
    public ArmorModule()
    {
        super("Armor", Category.HUD);
        this.hud = true;
    }

    @Override
    public int hudW()
    {
        return 18;
    }

    @Override
    public int hudH()
    {
        return 72;
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null)
        {
            return;
        }

        ItemStack[] armor = mc.thePlayer.inventory.armorInventory;
        int x = this.hudX;
        int y = this.hudY;

        RenderHelper.enableGUIStandardItemLighting();

        for (int i = 3; i >= 0; i--)
        {
            ItemStack stack = armor[i];

            if (stack == null)
            {
                continue;
            }

            mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
            y += 18;
        }

        RenderHelper.disableStandardItemLighting();
    }
}
