package com.powaclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

/**
 * Real background blur via the game's post-processing shader.
 * While a GUI is open (in a world) the world behind gets blurred; the UI stays sharp.
 */
public final class Blur
{
    private static final ResourceLocation BLUR = new ResourceLocation("shaders/post/blur.json");
    private static boolean started;
    private static int failures;

    private Blur() {}

    public static void update(boolean enabled)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc == null || mc.entityRenderer == null || !OpenGlHelper.shadersSupported)
        {
            return;
        }

        boolean want = enabled && mc.theWorld != null && mc.currentScreen != null;

        if (want)
        {
            if (!mc.entityRenderer.isShaderActive() && failures < 5)
            {
                mc.entityRenderer.loadShader(BLUR);

                if (!mc.entityRenderer.isShaderActive())
                {
                    failures++;
                }
            }

            started = mc.entityRenderer.isShaderActive();
        }
        else if (started)
        {
            stop();
        }
    }

    public static void stop()
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc != null && mc.entityRenderer != null)
        {
            mc.entityRenderer.stopUseShader();
        }

        started = false;
    }
}
