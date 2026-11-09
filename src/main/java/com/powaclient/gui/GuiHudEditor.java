package com.powaclient.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.powaclient.ModuleConfig;
import com.powaclient.PowaClient;
import com.powaclient.Theme;
import com.powaclient.module.Module;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Drag HUD elements around. Positions are saved on close.
 */
public class GuiHudEditor extends GuiScreen
{
    private final List<Module> elements = new ArrayList<Module>();
    private Module dragging;
    private int grabX;
    private int grabY;

    @Override
    public void initGui()
    {
        this.elements.clear();

        for (Module m : PowaClient.moduleManager.getModules())
        {
            if (m.hud)
            {
                this.elements.add(m);
            }
        }

        this.buttonList.clear();
        this.buttonList.add(new ModernButton(900, this.width / 2 - 40, this.height - 28, 80, 20, "Done"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        RenderUtil.dim(this.width, this.height, 0x8C);

        ScaledResolution res = new ScaledResolution(this.mc);

        // Draw the enabled elements so you see them for real
        for (Module m : this.elements)
        {
            if (m.isEnabled())
            {
                m.onRender(res);
            }
        }

        // Outline every element (draggable)
        for (Module m : this.elements)
        {
            int x = m.hudX;
            int y = m.hudY;
            int w = m.hudW();
            int h = m.hudH();
            int c = m.isEnabled() ? Theme.ACCENT : Theme.ACCENT_DARK;

            RenderUtil.rect(x - 1, y - 1, w + 2, 1, c);
            RenderUtil.rect(x - 1, y + h, w + 2, 1, c);
            RenderUtil.rect(x - 1, y - 1, 1, h + 2, c);
            RenderUtil.rect(x + w, y - 1, 1, h + 2, c);
        }

        fontRendererObj.drawStringWithShadow("Drag the elements   |   H to open   |   Done / Esc to close",
                6, 6, Theme.TEXT);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (mouseButton == 0)
        {
            for (int i = this.elements.size() - 1; i >= 0; i--)
            {
                Module m = this.elements.get(i);

                if (mouseX >= m.hudX - 1 && mouseX <= m.hudX + m.hudW()
                        && mouseY >= m.hudY - 1 && mouseY <= m.hudY + m.hudH())
                {
                    this.dragging = m;
                    this.grabX = mouseX - m.hudX;
                    this.grabY = mouseY - m.hudY;
                    break;
                }
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        if (this.dragging != null)
        {
            this.dragging.hudX = Math.max(0, Math.min(this.width - this.dragging.hudW(), mouseX - this.grabX));
            this.dragging.hudY = Math.max(0, Math.min(this.height - this.dragging.hudH(), mouseY - this.grabY));
        }

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        this.dragging = null;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 900)
        {
            ModuleConfig.save();
            this.mc.displayGuiScreen((GuiScreen) null);
            return;
        }

        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            ModuleConfig.save();
            this.mc.displayGuiScreen((GuiScreen) null);
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void onGuiClosed()
    {
        ModuleConfig.save();
    }
}
