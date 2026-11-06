package com.piremastered.client;

import com.piremastered.PIRemastered;
import com.piremastered.menu.BoilerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BoilerScreen extends AbstractContainerScreen<BoilerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PIRemastered.MOD_ID, "textures/gui/boiler_gui.png");

    public BoilerScreen(BoilerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 208;
        imageHeight = 132;
        titleLabelX = 8;
        titleLabelY = 7;
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(Button.builder(Component.literal("IGNITE"), button -> {
            if (minecraft != null && minecraft.gameMode != null) {
                minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 0);
            }
        }).bounds(leftPos + 153, topPos + 112, 46, 14).build());
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;
        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);

        int water = clamp(menu.getData(1), 0, 16_000);
        int steam = clamp(menu.getData(2), 0, 16_000);
        int burn = menu.getData(5) <= 0 ? 0
                : clamp(menu.getData(4) * 100 / menu.getData(5), 0, 100);
        int temperature = clamp(menu.getData(6), 20, 1_200);

        drawMeter(graphics, x, y, 20, water * 54 / 16_000, 0xFF3299D1);
        drawMeter(graphics, x, y, 66, burn * 54 / 100, 0xFFFF922E);
        drawMeter(graphics, x, y, 112, (temperature - 20) * 54 / 1_180, 0xFFE84A36);
        drawMeter(graphics, x, y, 158, steam * 54 / 16_000, 0xFFE0E7E6);

        String temperatureText = temperature + " C";
        graphics.drawString(font, temperatureText, x + 104, y + 103, 0xFFFFB36B, false);
    }

    private void drawMeter(GuiGraphics graphics, int x, int y, int meterX, int filledHeight, int color) {
        int bottom = y + 93;
        graphics.fill(x + meterX + 2, y + 38, x + meterX + 10, bottom, 0xFF101719);
        if (filledHeight > 0) {
            graphics.fill(x + meterX + 2, bottom - filledHeight,
                    x + meterX + 10, bottom, color);
        }
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, "WATER", 14, 24, 0xFFB9D9E5, false);
        graphics.drawString(font, "FUEL", 63, 24, 0xFFFFC27D, false);
        graphics.drawString(font, "TEMP", 109, 24, 0xFFFFA08B, false);
        graphics.drawString(font, "STEAM", 153, 24, 0xFFE4E8E4, false);

        String status = switch (menu.getData(0)) {
            case 2 -> "RUNNING";
            case 1 -> "FORMED - IDLE";
            default -> "STRUCTURE INCOMPLETE";
        };
        graphics.drawString(font, status, 10, 117, 0xFFE7E2D5, false);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }
}