package com.powaclient.gui;

import com.powaclient.Theme;

import net.minecraft.client.Minecraft;

public class Slider
{
    public final String label;
    public final int x;
    public final int y;
    public final int w;
    public final float min;
    public final float max;
    public float value;
    public boolean dragging;

    public Slider(String label, int x, int y, int w, float min, float max, float value)
    {
        this.label = label;
        this.x = x;
        this.y = y;
        this.w = w;
        this.min = min;
        this.max = max;
        this.value = value;
    }

    private int barY()
    {
        return this.y + 12;
    }

    public void draw(Minecraft mc, int mouseX, int mouseY)
    {
        boolean hover = mouseX >= this.x - 4 && mouseX <= this.x + this.w + 4
                && mouseY >= this.y && mouseY <= this.y + 20;

        mc.fontRendererObj.drawStringWithShadow(this.label, this.x, this.y, Theme.TEXT);

        String v = String.format("%.0f", this.value);
        mc.fontRendererObj.drawStringWithShadow(v, this.x + this.w - mc.fontRendererObj.getStringWidth(v), this.y, Theme.TEXT_DISABLED);

        int by = barY();
        RenderUtil.roundedRect(this.x, by, this.w, 6, 3, 0xFF2A2E3D);

        float f = (this.value - this.min) / (this.max - this.min);
        int fw = Math.round(f * this.w);
        RenderUtil.roundedRect(this.x, by, Math.max(4, fw), 6, 3, hover || this.dragging ? Theme.ACCENT_BRIGHT : Theme.ACCENT);

        RenderUtil.circle(this.x + fw - 5, by - 2, 10, Theme.ACCENT_BRIGHT);
    }

    public boolean mouseClicked(int mouseX, int mouseY)
    {
        if (mouseX >= this.x - 4 && mouseX <= this.x + this.w + 4 && mouseY >= this.y && mouseY <= this.y + 22)
        {
            this.dragging = true;
            this.setFromMouse(mouseX);
            return true;
        }

        return false;
    }

    public void mouseDragged(int mouseX)
    {
        if (this.dragging)
        {
            this.setFromMouse(mouseX);
        }
    }

    private void setFromMouse(int mouseX)
    {
        float f = (float) (mouseX - this.x) / (float) this.w;
        f = Math.max(0.0F, Math.min(1.0F, f));
        this.value = this.min + f * (this.max - this.min);
    }
}
