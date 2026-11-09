package com.powaclient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.powaclient.module.Module;

import net.minecraft.client.Minecraft;

/**
 * Persists which modules are enabled and where HUD elements are placed.
 */
public class ModuleConfig
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public Map<String, Boolean> enabled = new HashMap<String, Boolean>();
    public Map<String, int[]> pos = new HashMap<String, int[]>();

    private static File file()
    {
        File dir = new File(Minecraft.getMinecraft().mcDataDir, "config/powaclient");
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        return new File(dir, "modules.json");
    }

    public static void load()
    {
        try
        {
            File f = file();

            if (!f.exists())
            {
                return;
            }

            ModuleConfig data = GSON.fromJson(new FileReader(f), ModuleConfig.class);

            if (data == null)
            {
                return;
            }

            for (Module m : PowaClient.moduleManager.getModules())
            {
                if (data.enabled != null && data.enabled.containsKey(m.getName()))
                {
                    m.setEnabled(data.enabled.get(m.getName()).booleanValue());
                }

                if (data.pos != null && data.pos.containsKey(m.getName()))
                {
                    int[] p = data.pos.get(m.getName());

                    if (p != null && p.length == 2)
                    {
                        m.hudX = p[0];
                        m.hudY = p[1];
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("[PowaClient] module config load failed: " + e);
        }
    }

    public static void save()
    {
        try
        {
            ModuleConfig data = new ModuleConfig();

            for (Module m : PowaClient.moduleManager.getModules())
            {
                data.enabled.put(m.getName(), Boolean.valueOf(m.isEnabled()));

                if (m.hud)
                {
                    data.pos.put(m.getName(), new int[] { m.hudX, m.hudY });
                }
            }

            FileWriter writer = new FileWriter(file());
            GSON.toJson(data, writer);
            writer.close();
        }
        catch (Exception e)
        {
            System.out.println("[PowaClient] module config save failed: " + e);
        }
    }
}
