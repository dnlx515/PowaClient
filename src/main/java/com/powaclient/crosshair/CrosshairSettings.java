package com.powaclient.crosshair;

import java.awt.Color;

/**
 * A crosshair is a 16x16 pixel grid,
 * plus a global scale and colour.
 */
public class CrosshairSettings
{
    public boolean[] cells = new boolean[256]; // 16 x 16, row-major
    public int scale = 1;                       // screen pixels per grid cell
    public boolean outline = true;              // dark outline for visibility
    public boolean rainbow = false;
    public float hue = 220.0F;
    public float sat = 0.55F;
    public float bri = 1.0F;

    public CrosshairSettings copy()
    {
        CrosshairSettings s = new CrosshairSettings();
        s.apply(this);
        return s;
    }

    public void apply(CrosshairSettings o)
    {
        System.arraycopy(o.cells, 0, this.cells, 0, 256);
        this.scale = o.scale;
        this.outline = o.outline;
        this.rainbow = o.rainbow;
        this.hue = o.hue;
        this.sat = o.sat;
        this.bri = o.bri;
    }

    public void set(int x, int y, boolean v)
    {
        if (x >= 0 && x < 16 && y >= 0 && y < 16)
        {
            this.cells[y * 16 + x] = v;
        }
    }

    public boolean get(int x, int y)
    {
        return this.cells[y * 16 + x];
    }

    public void clear()
    {
        for (int i = 0; i < 256; i++)
        {
            this.cells[i] = false;
        }
    }

    public int color()
    {
        float h = this.rainbow ? (float) ((System.currentTimeMillis() / 8L) % 360L) : this.hue;
        return 0xFF000000 | (Color.HSBtoRGB(h / 360.0F, this.sat, this.bri) & 0xFFFFFF);
    }
}
