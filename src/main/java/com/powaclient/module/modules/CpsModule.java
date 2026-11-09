package com.powaclient.module.modules;

import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Mouse;

public class CpsModule extends Module
{
    private int leftClicks;
    private int rightClicks;
    private int cpsLeft;
    private int cpsRight;
    private long lastSecond = System.currentTimeMillis();

    public CpsModule()
    {
        super("CPS", Category.HUD);
        this.hud = true;
    }

    @Override
    public void onMouseInput()
    {
        int button = Mouse.getEventButton();
        boolean state = Mouse.getEventButtonState();

        if (state)
        {
            if (button == 0) this.leftClicks++;
            else if (button == 1) this.rightClicks++;
        }
    }

    @Override
    public void onTick()
    {
        long now = System.currentTimeMillis();

        if (now - this.lastSecond >= 1000L)
        {
            this.cpsLeft = this.leftClicks;
            this.cpsRight = this.rightClicks;
            this.leftClicks = 0;
            this.rightClicks = 0;
            this.lastSecond = now;
        }
    }

    @Override
    public void onRender(ScaledResolution res)
    {
        Minecraft mc = Minecraft.getMinecraft();
        int y = this.hudY;
        mc.fontRendererObj.drawStringWithShadow("CPS", this.hudX, y, Theme.ACCENT);
        mc.fontRendererObj.drawStringWithShadow(this.cpsLeft + " | " + this.cpsRight,
                this.hudX + mc.fontRendererObj.getStringWidth("CPS "), y, Theme.ACCENT2);
    }
}
