package com.powaclient.crosshair;

public class CrosshairPreset
{
    public String name;
    public boolean[] cells;

    public CrosshairPreset() {}

    public CrosshairPreset(String name, boolean[] cells)
    {
        this.name = name;
        this.cells = cells;
    }
}
