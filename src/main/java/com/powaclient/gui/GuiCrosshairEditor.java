package com.powaclient.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.powaclient.Theme;
import com.powaclient.crosshair.CrosshairConfig;
import com.powaclient.crosshair.CrosshairPreset;
import com.powaclient.crosshair.CrosshairRenderer;
import com.powaclient.crosshair.CrosshairSettings;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiCrosshairEditor extends GuiScreen
{
    private static final int PW = 470;
    private static final int PH = 360;
    private static final int CELL = 13;
    private static final int GRID = 16;

    private final GuiScreen parent;
    private final CrosshairSettings s = CrosshairConfig.getSettings();
    private final List<Slider> sliders = new ArrayList<Slider>();

    private List<CrosshairPreset> presets;

    private int px;
    private int py;
    private int rx;
    private int gridX;
    private int gridY;

    private Slider scaleSl;
    private Slider hueSl;
    private Slider briSl;

    private boolean painting;
    private boolean paintValue;

    public GuiCrosshairEditor(GuiScreen parent)
    {
        this.parent = parent;
    }

    @Override
    public void initGui()
    {
        this.px = (this.width - PW) / 2;
        this.py = (this.height - PH) / 2;
        this.rx = this.px + 180;
        this.gridX = this.rx;
        this.gridY = this.py + 118;

        this.sliders.clear();
        this.buttonList.clear();

        this.scaleSl = new Slider("Scale", this.px + 14, this.py + 200, 150, 1, 4, this.s.scale);
        this.hueSl = new Slider("Hue", this.px + 14, this.py + 224, 150, 0, 360, this.s.hue);
        this.briSl = new Slider("Brightness", this.px + 14, this.py + 248, 150, 0.1F, 1.0F, this.s.bri);
        this.sliders.add(this.scaleSl);
        this.sliders.add(this.hueSl);
        this.sliders.add(this.briSl);

        this.buttonList.add(new ModernButton(202, this.px + 14, this.py + 276, 72, 18, "Out " + onOff(this.s.outline)));
        this.buttonList.add(new ModernButton(203, this.px + 92, this.py + 276, 72, 18, "Rbow " + onOff(this.s.rainbow)));

        // Presets (Lunar-style bitmap presets + your saved ones)
        this.presets = CrosshairConfig.all();
        int cols = 3;
        int cw = 90;
        int ch = 18;
        int gx = 5;
        int gy = 5;

        for (int i = 0; i < this.presets.size() && i < 9; i++)
        {
            int col = i % cols;
            int row = i / cols;
            this.buttonList.add(new ModernButton(300 + i, this.rx + col * (cw + gx),
                    this.py + 52 + row * (ch + gy), cw, ch, this.presets.get(i).name));
        }

        // Grid actions
        this.buttonList.add(new ModernButton(220, this.rx, this.py + 332, 100, 20, "Clear"));
        this.buttonList.add(new ModernButton(221, this.rx + 104, this.py + 332, 104, 20, "Save preset"));
        this.buttonList.add(new ModernButton(212, this.px + 14, this.py + 332, 150, 22, "Done"));
    }

    private static String onOff(boolean b)
    {
        return b ? "ON" : "OFF";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        applySliders();

        RenderUtil.dim(this.width, this.height, 0xB0);

        RenderUtil.roundedRect(this.px + 6, this.py + 6, PW, PH, 14, 0x88000000);
        RenderUtil.roundedRectOutline(this.px, this.py, PW, PH, 14, Theme.BORDER, Theme.PANEL);
        RenderUtil.rect(this.px + 20, this.py + 2, PW - 40, 1, 0x33C3CDEF);

        RenderUtil.circle(this.px + 16, this.py + 12, 8, Theme.ACCENT);
        RenderUtil.circle(this.px + 28, this.py + 12, 8, Theme.ACCENT);

        String title = "CROSSHAIR";
        fontRendererObj.drawStringWithShadow(title,
                this.px + PW - 16 - fontRendererObj.getStringWidth(title), this.py + 14, Theme.ACCENT_BRIGHT);
        RenderUtil.rect(this.px + 14, this.py + 28, PW - 28, 1, Theme.ACCENT2);

        // Preview
        int bx = this.px + 14;
        int by = this.py + 40;
        int bw = 150;
        int bh = 150;
        RenderUtil.roundedRectOutline(bx, by, bw, bh, 10, Theme.ACCENT_DARK, 0xFF1E2029);
        RenderUtil.rect(bx + 4, by + 4, bw - 8, 1, 0x22FFFFFF);
        CrosshairRenderer.render(this.s, bx + bw / 2, by + bh / 2, 6.0F / Math.max(1, this.s.scale));

        for (Slider slider : this.sliders)
        {
            slider.draw(this.mc, mouseX, mouseY);
        }

        fontRendererObj.drawStringWithShadow("Presets", this.rx, this.py + 40, Theme.TEXT);
        fontRendererObj.drawStringWithShadow("Draw your own  (left = pixel, right = erase)",
                this.rx, this.py + 104, Theme.TEXT_DISABLED);

        // Drawable grid
        for (int y = 0; y < GRID; y++)
        {
            for (int x = 0; x < GRID; x++)
            {
                int c = this.s.get(x, y) ? Theme.ACCENT : 0x22FFFFFF;
                RenderUtil.rect(this.gridX + x * CELL, this.gridY + y * CELL, CELL - 1, CELL - 1, c);
            }
        }

        int hx = (mouseX - this.gridX) / CELL;
        int hy = (mouseY - this.gridY) / CELL;

        if (hx >= 0 && hx < GRID && hy >= 0 && hy < GRID
                && mouseX >= this.gridX && mouseY >= this.gridY
                && mouseX < this.gridX + GRID * CELL && mouseY < this.gridY + GRID * CELL)
        {
            RenderUtil.rect(this.gridX + hx * CELL - 1, this.gridY + hy * CELL - 1, CELL + 1, 1, Theme.ACCENT_BRIGHT);
            RenderUtil.rect(this.gridX + hx * CELL - 1, this.gridY + (hy + 1) * CELL - 1, CELL + 1, 1, Theme.ACCENT_BRIGHT);
            RenderUtil.rect(this.gridX + hx * CELL - 1, this.gridY + hy * CELL - 1, 1, CELL + 1, Theme.ACCENT_BRIGHT);
            RenderUtil.rect(this.gridX + (hx + 1) * CELL - 1, this.gridY + hy * CELL - 1, 1, CELL + 1, Theme.ACCENT_BRIGHT);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void applyAt(int mouseX, int mouseY)
    {
        int gx = (mouseX - this.gridX) / CELL;
        int gy = (mouseY - this.gridY) / CELL;

        if (gx >= 0 && gx < GRID && gy >= 0 && gy < GRID)
        {
            this.s.set(gx, gy, this.paintValue);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 202)
        {
            this.s.outline = !this.s.outline;
            this.initGui();
        }
        else if (button.id == 203)
        {
            this.s.rainbow = !this.s.rainbow;
            this.initGui();
        }
        else if (button.id == 220)
        {
            this.s.clear();
        }
        else if (button.id == 221)
        {
            boolean[] copy = new boolean[256];
            System.arraycopy(this.s.cells, 0, copy, 0, 256);
            CrosshairConfig.getCustom().add(new CrosshairPreset(
                    "Mine " + (CrosshairConfig.getCustom().size() + 1), copy));
            CrosshairConfig.save();
            this.initGui();
        }
        else if (button.id == 212)
        {
            CrosshairConfig.save();
            this.mc.displayGuiScreen(this.parent);
        }
        else if (button.id >= 300 && button.id < 300 + this.presets.size())
        {
            System.arraycopy(this.presets.get(button.id - 300).cells, 0, this.s.cells, 0, 256);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        boolean inGrid = mouseX >= this.gridX && mouseY >= this.gridY
                && mouseX < this.gridX + GRID * CELL && mouseY < this.gridY + GRID * CELL;

        if (inGrid && mouseButton != 0 || inGrid && mouseButton == 0)
        {
            this.painting = true;
            this.paintValue = (mouseButton == 0);
            applyAt(mouseX, mouseY);
            return;
        }

        for (Slider slider : this.sliders)
        {
            if (slider.mouseClicked(mouseX, mouseY))
            {
                return;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        if (this.painting)
        {
            applyAt(mouseX, mouseY);
        }

        for (Slider slider : this.sliders)
        {
            slider.mouseDragged(mouseX);
        }

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        this.painting = false;

        for (Slider slider : this.sliders)
        {
            slider.dragging = false;
        }

        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            CrosshairConfig.save();
            this.mc.displayGuiScreen(this.parent);
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void onGuiClosed()
    {
        CrosshairConfig.save();
    }

    private void applySliders()
    {
        this.s.scale = Math.max(1, Math.round(this.scaleSl.value));
        this.s.hue = this.hueSl.value;
        this.s.bri = this.briSl.value;
    }
}
