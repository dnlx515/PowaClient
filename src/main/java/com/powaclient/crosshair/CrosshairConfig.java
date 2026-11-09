package com.powaclient.crosshair;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.client.Minecraft;

public class CrosshairConfig
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final CrosshairSettings settings = new CrosshairSettings();
    private static final List<CrosshairPreset> custom = new ArrayList<CrosshairPreset>();

    private CrosshairSettings current;
    private List<CrosshairPreset> presets;

    public static CrosshairSettings getSettings()
    {
        return settings;
    }

    public static List<CrosshairPreset> getCustom()
    {
        return custom;
    }

    private static boolean[] blank()
    {
        return new boolean[256];
    }

    private static void on(boolean[] g, int x, int y)
    {
        if (x >= 0 && x < 16 && y >= 0 && y < 16)
        {
            g[y * 16 + x] = true;
        }
    }

    /** The classic Minecraft plus (Lunar "The Plus"). */
    private static boolean[] plus()
    {
        boolean[] g = blank();
        for (int i = 4; i <= 11; i++)
        {
            on(g, 7, i);
            on(g, 8, i);
            on(g, i, 7);
            on(g, i, 8);
        }
        return g;
    }

    private static boolean[] thinCross()
    {
        boolean[] g = blank();
        for (int i = 3; i <= 12; i++)
        {
            on(g, 7, i);
            on(g, i, 7);
        }
        return g;
    }

    /** A small centre dot (Lunar "Dot"). */
    private static boolean[] dot()
    {
        boolean[] g = blank();
        on(g, 7, 7);
        on(g, 8, 7);
        on(g, 7, 8);
        on(g, 8, 8);
        return g;
    }

    private static boolean[] circle()
    {
        boolean[] g = blank();
        for (int y = 0; y < 16; y++)
        {
            for (int x = 0; x < 16; x++)
            {
                double d = Math.sqrt((x - 7.5) * (x - 7.5) + (y - 7.5) * (y - 7.5));
                if (d >= 4.2D && d <= 5.4D)
                {
                    on(g, x, y);
                }
            }
        }
        return g;
    }

    private static boolean[] circleDot()
    {
        boolean[] g = circle();
        on(g, 7, 7);
        on(g, 8, 7);
        on(g, 7, 8);
        on(g, 8, 8);
        return g;
    }

    /** Boxed cross (Lunar "Boxed Cross"): the plus inside a square. */
    private static boolean[] boxedCross()
    {
        boolean[] g = plus();
        for (int i = 2; i <= 13; i++)
        {
            on(g, i, 2);
            on(g, i, 13);
            on(g, 2, i);
            on(g, 13, i);
        }
        return g;
    }

    public static List<CrosshairPreset> builtins()
    {
        List<CrosshairPreset> list = new ArrayList<CrosshairPreset>();
        list.add(new CrosshairPreset("Plus", plus()));
        list.add(new CrosshairPreset("Thin", thinCross()));
        list.add(new CrosshairPreset("Dot", dot()));
        list.add(new CrosshairPreset("Circle", circle()));
        list.add(new CrosshairPreset("CirDot", circleDot()));
        list.add(new CrosshairPreset("Boxed", boxedCross()));
        return list;
    }

    public static List<CrosshairPreset> all()
    {
        List<CrosshairPreset> list = new ArrayList<CrosshairPreset>(builtins());
        list.addAll(custom);
        return list;
    }

    private static File configFile()
    {
        File dir = new File(Minecraft.getMinecraft().mcDataDir, "config/powaclient");
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        return new File(dir, "crosshair.json");
    }

    public static void load()
    {
        try
        {
            File f = configFile();

            if (!f.exists())
            {
                return;
            }

            CrosshairConfig data = GSON.fromJson(new FileReader(f), CrosshairConfig.class);

            if (data != null && data.current != null)
            {
                settings.apply(data.current);
            }

            custom.clear();

            if (data != null && data.presets != null)
            {
                custom.addAll(data.presets);
            }
        }
        catch (Exception e)
        {
            System.out.println("[PowaClient] crosshair config load failed: " + e);
        }
    }

    public static void save()
    {
        try
        {
            CrosshairConfig data = new CrosshairConfig();
            data.current = settings.copy();
            data.presets = new ArrayList<CrosshairPreset>(custom);

            FileWriter writer = new FileWriter(configFile());
            GSON.toJson(data, writer);
            writer.close();
        }
        catch (Exception e)
        {
            System.out.println("[PowaClient] crosshair config save failed: " + e);
        }
    }
}
