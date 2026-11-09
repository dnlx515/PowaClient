package com.powaclient.gui;

import com.powaclient.Theme;

/**
 * Tiny procedural icons (no texture assets needed). Drawn with rectangles.
 */
public final class Icons
{
    private Icons() {}

    public static void draw(String kind, int x, int y, int s)
    {
        if (kind == null)
        {
            return;
        }

        if (kind.equals("grass"))
        {
            RenderUtil.rect(x, y + s / 2, s, s - s / 2, 0xFF6B4A2F);
            RenderUtil.rect(x, y, s, s / 2, 0xFF6FBF5A);
            RenderUtil.rect(x, y, s, 1, 0xFF86D06E);
        }
        else if (kind.equals("globe"))
        {
            RenderUtil.circle(x, y, s, Theme.ACCENT);
            RenderUtil.rect(x + s / 2 - 1, y + 1, 2, s - 2, Theme.BG_SOLID);
            RenderUtil.rect(x + 1, y + s / 2 - 1, s - 2, 2, Theme.BG_SOLID);
        }
        else if (kind.equals("stack"))
        {
            RenderUtil.rect(x, y, s, s / 3, Theme.ACCENT);
            RenderUtil.rect(x + 2, y + s / 3, s - 4, s / 3, Theme.ACCENT_BRIGHT);
            RenderUtil.rect(x, y + 2 * (s / 3), s, s / 3, Theme.ACCENT2);
        }
        else if (kind.equals("gear"))
        {
            RenderUtil.rect(x + s / 2 - 1, y - 1, 2, 3, Theme.ACCENT);
            RenderUtil.rect(x + s / 2 - 1, y + s - 2, 2, 3, Theme.ACCENT);
            RenderUtil.rect(x - 1, y + s / 2 - 1, 3, 2, Theme.ACCENT);
            RenderUtil.rect(x + s - 2, y + s / 2 - 1, 3, 2, Theme.ACCENT);
            RenderUtil.circle(x, y, s, Theme.ACCENT);
            RenderUtil.circle(x + s / 4, y + s / 4, s / 2, Theme.BG_SOLID);
        }
        else if (kind.equals("power"))
        {
            RenderUtil.circle(x, y, s, Theme.ACCENT2);
            RenderUtil.circle(x + s / 4, y + s / 4, s / 2, Theme.BG_SOLID);
            RenderUtil.rect(x + s / 2 - 1, y - 2, 2, s / 2 + 1, Theme.ACCENT2);
        }
        else if (kind.equals("eyes"))
        {
            RenderUtil.circle(x, y + 1, s - 2, Theme.ACCENT);
            RenderUtil.circle(x + s / 2 + 1, y + 1, s - 2, Theme.ACCENT);
        }
    }
}
