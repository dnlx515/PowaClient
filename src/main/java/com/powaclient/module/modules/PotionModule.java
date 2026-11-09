package com.powaclient.module.modules;

import java.util.Collection;

import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionModule extends Module
{
    public PotionModule()
    {
        super("Potion Effects", Category.HUD);
        this.hud = true;
    }

    @Override
    public int hudW()
    {
        return 120;
    }

    @Override
    public int hudH()
    {
        return 20;
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null)
        {
            return;
        }

        Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();
        int y = this.hudY;

        for (PotionEffect pe : effects)
        {
            int id = pe.getPotionID();
            Potion potion = (id >= 0 && id < Potion.potionTypes.length) ? Potion.potionTypes[id] : null;
            String name = potion == null ? pe.getEffectName() : potion.getName();

            if (pe.getAmplifier() > 0)
            {
                name = name + " " + (pe.getAmplifier() + 1);
            }

            int secs = pe.getDuration() / 20;
            String duration = (secs / 60) + ":" + String.format("%02d", secs % 60);

            mc.fontRendererObj.drawStringWithShadow(name, this.hudX, y, Theme.ACCENT);
            mc.fontRendererObj.drawStringWithShadow(duration,
                    this.hudX + mc.fontRendererObj.getStringWidth(name + " "), y, Theme.ACCENT2);

            y += 10;
        }
    }
}
