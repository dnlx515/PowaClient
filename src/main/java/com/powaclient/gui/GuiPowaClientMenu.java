package com.powaclient.gui;

import java.io.IOException;
import java.util.Random;

import com.powaclient.PowaClient;
import com.powaclient.Theme;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;

public class GuiPowaClientMenu extends GuiScreen
{
    private static final String[] SPLASHES = {
        "Sunset skies",
        "Soft and clean",
        "PowaClient 1.8.9",
        "RShift opens the menu",
        "Custom crosshair inside",
        "Hold V to freelook",
        "Hold C to zoom"
    };

    private static final int CARD_W = 260;
    private static final int CARD_H = 272;

    private String splash;
    private int cardX;
    private int cardY;

    @Override
    public void initGui()
    {
        this.splash = SPLASHES[new Random().nextInt(SPLASHES.length)];
        this.cardX = (this.width - CARD_W) / 2;
        this.cardY = (this.height - CARD_H) / 2;

        int x = this.width / 2 - 100;
        int y = cardY + 92;

        this.buttonList.add(new ModernButton(1, x, y, 200, 22, "Singleplayer", "grass"));
        this.buttonList.add(new ModernButton(2, x, y + 26, 200, 22, "Multiplayer", "globe"));
        this.buttonList.add(new ModernButton(6, x, y + 52, 200, 22, "Mods", "stack"));
        this.buttonList.add(new ModernButton(100, x, y + 78, 200, 22, "PowaClient", "eyes"));
        this.buttonList.add(new ModernButton(0, x, y + 110, 97, 22, "Options", "gear"));
        this.buttonList.add(new ModernButton(4, x + 103, y + 110, 97, 22, "Quit", "power"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        // Background image (Minecraft sunset over a hill)
        mc.getTextureManager().bindTexture(new net.minecraft.util.ResourceLocation("powaclient", "textures/gui/background.png"));
        net.minecraft.client.renderer.GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        net.minecraft.client.renderer.GlStateManager.enableBlend();
        net.minecraft.client.gui.Gui.drawModalRectWithCustomSizedTexture(
                0, 0, 0.0F, 0.0F, this.width, this.height, 1920.0F, 1080.0F);

        // Dark veil so the UI stays readable
        drawGradientRect(0, 0, this.width, this.height, 0x2E000000, 0x8C000000);

        // Glass card
        RenderUtil.roundedRect(cardX + 6, cardY + 6, CARD_W, CARD_H, 16, 0x88000000);
        RenderUtil.roundedRectOutline(cardX, cardY, CARD_W, CARD_H, 16, Theme.BORDER, 0xB81E2029);
        RenderUtil.rect(cardX + 20, cardY + 2, CARD_W - 40, 1, 0x33C3CDEF);

        // Title dots + title
        RenderUtil.circle(this.width / 2 - 16, cardY + 26, 10, Theme.ACCENT);
        RenderUtil.circle(this.width / 2 + 6, cardY + 26, 10, Theme.ACCENT);

        drawCenteredString(fontRendererObj, "POWACLIENT", this.width / 2, cardY + 44, Theme.ACCENT_BRIGHT);
        drawCenteredString(fontRendererObj, "1.8.9", this.width / 2, cardY + 58, Theme.TEXT_DISABLED);

        super.drawScreen(mouseX, mouseY, partialTicks);

        drawCenteredString(fontRendererObj, this.splash, this.width / 2, cardY + CARD_H - 22, Theme.ACCENT2);

        fontRendererObj.drawStringWithShadow("PowaClient " + PowaClient.VERSION, 4, this.height - 12, Theme.ACCENT);
        fontRendererObj.drawStringWithShadow("Minecraft 1.8.9",
                this.width - 4 - fontRendererObj.getStringWidth("Minecraft 1.8.9"),
                this.height - 12, Theme.TEXT_DISABLED);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 4:
                this.mc.shutdown();
                break;
            case 6:
                this.mc.displayGuiScreen(new net.minecraftforge.fml.client.GuiModList(this));
                break;
            case 100:
                this.mc.displayGuiScreen(new GuiPowaClient());
                break;
            default:
                break;
        }
    }
}
