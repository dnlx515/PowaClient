package com.powaclient.crosshair;

import com.powaclient.gui.RenderUtil;

/**
 * Draws a 16x16 pixel crosshair. Used on the HUD (zoom 1) and in the editor preview.
 */
public final class CrosshairRenderer
{
    private CrosshairRenderer() {}

    public static void render(CrosshairSettings s, int cx, int cy, float zoom)
    {
        int cell = Math.max(1, Math.round(s.scale * zoom));
        int originX = cx - 8 * cell;
        int originY = cy - 8 * cell;
        int color = s.color();

        if (s.outline)
        {
            for (int y = 0; y < 16; y++)
            {
                for (int x = 0; x < 16; x++)
                {
                    if (s.cells[y * 16 + x])
                    {
                        RenderUtil.rect(originX + x * cell - 1, originY + y * cell - 1, cell + 2, cell + 2, 0x80000000);
                    }
                }
            }
        }

        for (int y = 0; y < 16; y++)
        {
            for (int x = 0; x < 16; x++)
            {
                if (s.cells[y * 16 + x])
                {
                    RenderUtil.rect(originX + x * cell, originY + y * cell, cell, cell, color);
                }
            }
        }
    }
}
