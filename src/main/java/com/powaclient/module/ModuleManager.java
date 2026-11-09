package com.powaclient.module;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.ScaledResolution;

public class ModuleManager
{
    private final List<Module> modules = new ArrayList<Module>();

    public void register(Module module)
    {
        this.modules.add(module);
    }

    public List<Module> getModules()
    {
        return this.modules;
    }

    public Module byName(String name)
    {
        for (Module module : this.modules)
        {
            if (module.getName().equalsIgnoreCase(name))
            {
                return module;
            }
        }

        return null;
    }

    public void onRender(ScaledResolution res)
    {
        for (Module module : this.modules)
        {
            if (module.isEnabled())
            {
                module.onRender(res);
            }
        }
    }

    public void onTickStart()
    {
        for (Module module : this.modules)
        {
            if (module.isEnabled())
            {
                module.onTickStart();
            }
        }
    }

    public void onKeyInput()
    {
        for (Module module : this.modules)
        {
            if (module.isEnabled())
            {
                module.onKeyInput();
            }
        }
    }

    public void onTick()
    {
        for (Module module : this.modules)
        {
            if (module.isEnabled())
            {
                module.onTick();
            }
        }
    }

    public void onMouseInput()
    {
        for (Module module : this.modules)
        {
            if (module.isEnabled())
            {
                module.onMouseInput();
            }
        }
    }
}
