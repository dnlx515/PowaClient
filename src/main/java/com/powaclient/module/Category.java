package com.powaclient.module;

import com.powaclient.Theme;

public enum Category
{
    HUD("HUD"),
    PLAYER("Player"),
    RENDER("Render"),
    MISC("Misc");

    private final String label;

    Category(String label)
    {
        this.label = label;
    }

    public String label()
    {
        return this.label;
    }

    public int color()
    {
        switch (this)
        {
            case HUD: return Theme.CAT_HUD;
            case PLAYER: return Theme.CAT_PLAYER;
            case RENDER: return Theme.CAT_RENDER;
            default: return Theme.CAT_MISC;
        }
    }
}
