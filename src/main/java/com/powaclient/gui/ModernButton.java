package com.powaclient.gui;

import com.powaclient.Theme;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/**
 * Rounded, glassy button used across the client. Optional small icon on the left.
 */
public class ModernButton extends GuiButton
{
    private final String icon;
    public boolean highlight;

    public ModernButton(int id, int x, int y, int width, int height, String text)
    {
        this(id, x, y, width, height, text, null);
    }

    public ModernButton(int id, int x, int y, int width, int height, String text, String icon)
    {
        super(id, x, y, width, height, text);
        this.icon = icon;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (!this.visible)
        {
            return;
        }

        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition
                && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

        int bg;
        int border;

        if (!this.enabled)
        {
            bg = 0x8020222E;
            border = Theme.ACCENT_DARK;
        }
        else if (this.highlight)
        {
            bg = 0xE63A4060;
            border = Theme.ACCENT_BRIGHT;
        }
        else if (this.hovered)
        {
            bg = 0xF0373C52;
            border = Theme.ACCENT;
        }
        else
        {
            bg = 0xD0262A38;
            border = Theme.ACCENT2;
        }

        RenderUtil.roundedRectOutline(this.xPosition, this.yPosition, this.width, this.height, 6, border, bg);

        int textCx = this.xPosition + this.width / 2;

        if (this.icon != null && !this.icon.isEmpty())
        {
            Icons.draw(this.icon, this.xPosition + 10, this.yPosition + (this.height - 10) / 2, 10);
            textCx += 8;
        }

        int textColor = !this.enabled ? Theme.TEXT_DISABLED : (this.hovered ? Theme.ACCENT_BRIGHT : Theme.TEXT);
        this.drawCenteredString(mc.fontRendererObj, this.displayString,
                textCx, this.yPosition + (this.height - 8) / 2, textColor);
    }
}
