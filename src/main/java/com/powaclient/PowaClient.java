package com.powaclient;

import com.powaclient.crosshair.CrosshairConfig;
import com.powaclient.gui.GuiHudEditor;
import com.powaclient.gui.GuiPowaClient;
import com.powaclient.gui.GuiPowaClientMenu;
import com.powaclient.module.Module;
import com.powaclient.module.ModuleManager;
import com.powaclient.module.modules.ArmorModule;
import com.powaclient.module.modules.ClockModule;
import com.powaclient.module.modules.CoordinatesModule;
import com.powaclient.module.modules.CpsModule;
import com.powaclient.module.modules.CrosshairModule;
import com.powaclient.module.modules.DayCounterModule;
import com.powaclient.module.modules.DirectionModule;
import com.powaclient.module.modules.FpsModule;
import com.powaclient.module.modules.FreelookModule;
import com.powaclient.module.modules.FullbrightModule;
import com.powaclient.module.modules.KeystrokesModule;
import com.powaclient.module.modules.MemoryModule;
import com.powaclient.module.modules.MenuBlurModule;
import com.powaclient.module.modules.PingModule;
import com.powaclient.module.modules.PotionModule;
import com.powaclient.module.modules.ServerModule;
import com.powaclient.module.modules.SpeedModule;
import com.powaclient.module.modules.ToggleSneakModule;
import com.powaclient.module.modules.ToggleSprintModule;
import com.powaclient.module.modules.ZoomModule;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import org.lwjgl.input.Keyboard;

@Mod(modid = PowaClient.MODID, version = PowaClient.VERSION)
public class PowaClient
{
    public static final String MODID = "powaclient";
    public static final String VERSION = "1.0.0";

    public static ModuleManager moduleManager;
    public static KeyBinding guiKey;
    public static KeyBinding hudKey;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        CrosshairConfig.load();
        moduleManager = new ModuleManager();

        // HUD elements (draggable, stack on the left by default)
        moduleManager.register(new FpsModule());
        moduleManager.register(new CpsModule());
        moduleManager.register(new KeystrokesModule());
        moduleManager.register(new CoordinatesModule());
        moduleManager.register(new ClockModule());
        moduleManager.register(new DirectionModule());
        moduleManager.register(new MemoryModule());
        moduleManager.register(new SpeedModule());
        moduleManager.register(new ServerModule());
        moduleManager.register(new DayCounterModule());
        moduleManager.register(new PotionModule());
        moduleManager.register(new ArmorModule());

        // Player / render / misc
        moduleManager.register(new FullbrightModule());
        moduleManager.register(new ToggleSprintModule());
        moduleManager.register(new ToggleSneakModule());
        moduleManager.register(new CrosshairModule());
        moduleManager.register(new FreelookModule());
        moduleManager.register(new ZoomModule());
        moduleManager.register(new MenuBlurModule());

        ModuleConfig.load();
        assignHudDefaults();

        guiKey = new KeyBinding("Apri PowaClient", Keyboard.KEY_RSHIFT, "PowaClient");
        hudKey = new KeyBinding("HUD editor", Keyboard.KEY_H, "PowaClient");
        ClientRegistry.registerKeyBinding(guiKey);
        ClientRegistry.registerKeyBinding(hudKey);

        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);

        System.out.println("PowaClient loaded!");
    }

    private void assignHudDefaults()
    {
        int y = 3;

        for (Module m : moduleManager.getModules())
        {
            if (m.hud && m.hudX < 0)
            {
                m.hudX = 3;
                m.hudY = y;
                y += m.hudH() + 2;
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
        if (event.gui != null && event.gui.getClass() == GuiMainMenu.class)
        {
            event.gui = new GuiPowaClientMenu();
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event)
    {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL)
        {
            return;
        }

        moduleManager.onRender(event.resolution);
    }

    @SubscribeEvent
    public void onRenderOverlayPre(RenderGameOverlayEvent.Pre event)
    {
        if (event.type != RenderGameOverlayEvent.ElementType.CROSSHAIRS)
        {
            return;
        }

        CrosshairModule crosshair = (CrosshairModule) moduleManager.byName("Crosshair");

        if (crosshair != null && crosshair.isEnabled())
        {
            crosshair.drawCrosshair(event.resolution);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START)
        {
            moduleManager.onTickStart();
            return;
        }

        moduleManager.onTick();

        MenuBlurModule blur = (MenuBlurModule) moduleManager.byName("Menu Blur");
        Blur.update(blur != null && blur.isEnabled());
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event)
    {
        moduleManager.onMouseInput();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        moduleManager.onKeyInput();

        if (hudKey.isPressed())
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiHudEditor());
            return;
        }

        if (guiKey.isPressed())
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPowaClient());
        }
    }
}
