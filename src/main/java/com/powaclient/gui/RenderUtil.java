package com.powaclient.gui;

import net.minecraft.client.gui.Gui;

/**
 * Rectangle helpers. Minecraft only draws hard rectangles, so we fake
 * rounded corners by filling a body rect plus approximated circle rows.
 */
public final class RenderUtil
{
    private RenderUtil() {}

    public static void rect(int x, int y, int w, int h, int color)
    {
        Gui.drawRect(x, y, x + w, y + h, color);
    }

    public static void roundedRect(int x, int y, int w, int h, int r, int color)
    {
        if (w <= 0 || h <= 0)
        {
            return;
        }

        if (r < 1)
        {
            Gui.drawRect(x, y, x + w, y + h, color);
            return;
        }

        if (r > w / 2) r = w / 2;
        if (r > h / 2) r = h / 2;

        // Body
        Gui.drawRect(x, y + r, x + w, y + h - r, color);
        Gui.drawRect(x + r, y, x + w - r, y + h, color);

        // Corners
        for (int j = 0; j < r; j++)
        {
            int dy = r - j;
            int inset = r - (int) Math.round(Math.sqrt((double) (r * r - dy * dy)));
            Gui.drawRect(x + inset, y + j, x + w - inset, y + j + 1, color);
            Gui.drawRect(x + inset, y + h - 1 - j, x + w - inset, y + h - j, color);
        }
    }

    public static void roundedRectOutline(int x, int y, int w, int h, int r, int border, int fill)
    {
        roundedRect(x, y, w, h, r, border);
        roundedRect(x + 1, y + 1, w - 2, h - 2, Math.max(r - 1, 0), fill);
    }

    /** A circle, drawn as a square with radius = half its size. */
    public static void circle(int x, int y, int diameter, int color)
    {
        roundedRect(x, y, diameter, diameter, diameter / 2, color);
    }

    /** Full-screen translucent dim, the "focus" layer behind a modal. */
    public static void dim(int width, int height, int alpha)
    {
        Gui.drawRect(0, 0, width, height, alpha << 24);
    }
}
