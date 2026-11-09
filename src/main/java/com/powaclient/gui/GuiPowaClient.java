package com.powaclient.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.powaclient.ModuleConfig;
import com.powaclient.PowaClient;
import com.powaclient.Theme;
import com.powaclient.module.Category;
import com.powaclient.module.Module;
import com.powaclient.module.modules.CrosshairModule;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiPowaClient extends GuiScreen
{
    private static final int PANEL_W = 460;
    private static final int HEADER_H = 34;
    private static final int ROW_H = 24;
    private static final int PAD = 14;
    private static final int COL_GAP = 12;

    private int panelH;
    private int panelX;
    private int panelY;
    private int rows;

    private int filter = -1; // -1 = all, else Category ordinal
    private List<Module> shown = new ArrayList<Module>();

    @Override
    public void initGui()
    {
        this.shown = visible();
        this.rows = (this.shown.size() + 1) / 2;
        this.panelH = HEADER_H + 6 + 18 + 10 + this.rows * ROW_H + 30;
        this.panelX = (this.width - PANEL_W) / 2;
        this.panelY = (this.height - panelH) / 2;

        this.buttonList.clear();

        int innerW = PANEL_W - PAD * 2;
        int gap = 6;
        int bw = (innerW - 4 * gap) / 5;
        String[] labels = { "All", "HUD", "Player", "Render", "Misc" };

        for (int i = 0; i < 5; i++)
        {
            ModernButton b = new ModernButton(400 + i, this.panelX + PAD + i * (bw + gap),
                    this.panelY + HEADER_H + 6, bw, 18, labels[i]);
            b.highlight = (this.filter == i - 1);
            this.buttonList.add(b);
        }

        this.buttonList.add(new ModernButton(500, this.panelX + PANEL_W - PAD - 84,
                this.panelY + this.panelH - 22, 84, 18, "Edit HUD"));
    }

    private List<Module> visible()
    {
        List<Module> out = new ArrayList<Module>();

        for (Module m : PowaClient.moduleManager.getModules())
        {
            if (this.filter < 0 || m.getCategory() == Category.values()[this.filter])
            {
                out.add(m);
            }
        }

        return out;
    }

    private int colWidth()
    {
        return (PANEL_W - PAD * 2 - COL_GAP) / 2;
    }

    private int rowX(int index)
    {
        int col = index < this.rows ? 0 : 1;
        return this.panelX + PAD + col * (colWidth() + COL_GAP);
    }

    private int rowY(int index)
    {
        int row = index < this.rows ? index : index - this.rows;
        return this.panelY + HEADER_H + 6 + 18 + 10 + row * ROW_H;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        RenderUtil.dim(this.width, this.height, 0x9E);

        RenderUtil.roundedRect(panelX + 5, panelY + 5, PANEL_W, panelH, 12, 0x77000000);
        RenderUtil.roundedRectOutline(panelX, panelY, PANEL_W, panelH, 12, Theme.BORDER, Theme.PANEL);
        RenderUtil.rect(panelX + 20, panelY + 2, PANEL_W - 40, 1, 0x33C3CDEF);

        Icons.draw("eyes", panelX + 14, panelY + 11, 10);

        String title = "POWACLIENT";
        fontRendererObj.drawStringWithShadow(title,
                panelX + PANEL_W - 16 - fontRendererObj.getStringWidth(title),
                panelY + 14, Theme.ACCENT_BRIGHT);
        RenderUtil.rect(panelX + 14, panelY + HEADER_H - 4, PANEL_W - 28, 1, Theme.ACCENT2);

        int rowW = colWidth();
        int rowH = ROW_H - 6;
        int index = 0;

        for (Module module : this.shown)
        {
            int x = rowX(index);
            int y = rowY(index);
            boolean hover = mouseX >= x && mouseX <= x + rowW && mouseY >= y && mouseY <= y + rowH;

            RenderUtil.roundedRect(x, y, rowW, rowH, 6, hover ? Theme.ROW_HOVER : Theme.ROW);

            RenderUtil.circle(x + 8, y + (rowH - 8) / 2, 8,
                    module.isEnabled() ? module.getCategory().color() : Theme.ACCENT_DARK);

            fontRendererObj.drawStringWithShadow(module.getName(), x + 22, y + (rowH - 8) / 2,
                    module.isEnabled() ? Theme.TEXT : Theme.TEXT_DISABLED);

            int swW = 30;
            int swH = 14;
            int sx = x + rowW - 8 - swW;
            int sy = y + (rowH - swH) / 2;

            RenderUtil.roundedRect(sx, sy, swW, swH, swH / 2, module.isEnabled() ? Theme.ACCENT : 0xFF3A3F52);

            int knob = swH - 4;
            int kx = module.isEnabled() ? sx + swW - knob - 2 : sx + 2;
            RenderUtil.circle(kx, sy + 2, knob, module.isEnabled() ? Theme.BG_SOLID : Theme.TEXT_DISABLED);

            index++;
        }

        fontRendererObj.drawStringWithShadow("click = toggle   |   destro su Crosshair = editor",
                panelX + PAD, panelY + panelH - 14, Theme.TEXT_DISABLED);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id >= 400 && button.id <= 404)
        {
            this.filter = button.id - 401;
            this.initGui();
            return;
        }

        if (button.id == 500)
        {
            this.mc.displayGuiScreen(new GuiHudEditor());
            return;
        }

        super.actionPerformed(button);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        int rowW = colWidth();
        int rowH = ROW_H - 6;
        int index = 0;

        for (Module module : this.shown)
        {
            int x = rowX(index);
            int y = rowY(index);

            if (mouseX >= x && mouseX <= x + rowW && mouseY >= y && mouseY <= y + rowH)
            {
                if (mouseButton == 0)
                {
                    module.toggle();
                    ModuleConfig.save();
                }
                else if (mouseButton == 1 && module instanceof CrosshairModule)
                {
                    this.mc.displayGuiScreen(new GuiCrosshairEditor(this));
                }

                break;
            }

            index++;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
