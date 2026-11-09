package com.powaclient.module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Base class for every PowaClient module (what Salwyrr calls a "mod").
 */
public abstract class Module
{
    private final String name;
    private final Category category;
    private boolean enabled;

    // HUD positioning (used when this module is a draggable HUD element)
    public boolean hud = false;
    public int hudX = -1;
    public int hudY = -1;

    public int hudW() { return 90; }

    public int hudH() { return 10; }

    public Module(String name, Category category)
    {
        this.name = name;
        this.category = category;
    }

    public String getName()
    {
        return this.name;
    }

    public Category getCategory()
    {
        return this.category;
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }

    public void toggle()
    {
        this.setEnabled(!this.enabled);
    }

    public void setEnabled(boolean enabled)
    {
        if (this.enabled == enabled)
        {
            return;
        }

        this.enabled = enabled;

        if (enabled)
        {
            this.onEnable();
        }
        else
        {
            this.onDisable();
        }
    }

    protected void onEnable() {}

    protected void onDisable() {}

    /** Called every frame, only while enabled. Draw HUD stuff here. */
    public void onRender(ScaledResolution res) {}

    /** Called every client tick, only while enabled. */
    public void onTick() {}

    /** Called on raw mouse input, only while enabled. */
    public void onMouseInput() {}

    /** Called at the start of a client tick, only while enabled. */
    public void onTickStart() {}

    /** Called on raw key input, only while enabled. */
    public void onKeyInput() {}

    protected static Minecraft mc()
    {
        return Minecraft.getMinecraft();
    }
}
