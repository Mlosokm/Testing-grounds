package com.piremastered.client;

import com.piremastered.menu.BoilerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BoilerScreen extends AbstractContainerScreen<BoilerMenu> {
    public BoilerScreen(BoilerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 208;
        imageHeight = 140;
        titleLabelX = 8;
        titleLabelY = 6;
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(Button.builder(Component.literal("IGNITE"), button -> {
            if (minecraft != null && minecraft.gameMode != null) {
                minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 0);
            }
        }).bounds(leftPos + 152, topPos + 118, 48, 16).build());
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;

        // Main panel
        graphics.fill(x, y, x + imageWidth, y + imageHeight, 0xFF14181C);
        graphics.fill(x + 1, y + 1, x + imageWidth - 1, y + imageHeight - 1, 0xFF1E252C);

        // Header
        graphics.fill(x + 3, y + 3, x + imageWidth - 3, y + 16, 0xFF2A3340);

        int water = clamp(menu.getData(1), 0, 16_000);
        int steam = clamp(menu.getData(2), 0, 16_000);
        int burnTicks = menu.getData(4);
        int totalBurn = menu.getData(5);
        int burnPct = totalBurn <= 0 ? 0 : clamp(burnTicks * 100 / totalBurn, 0, 100);
        int temperature = clamp(menu.getData(6), 20, 1_200);

        drawMeter(graphics, x, y, 18, water * 52 / 16_000, 0xFF3AA0E0);
        drawMeter(graphics, x, y, 64, burnPct * 52 / 100, 0xFFFF8C2A);
        drawMeter(graphics, x, y, 110, (temperature - 20) * 52 / 1_180, 0xFFE85040);
        drawMeter(graphics, x, y, 156, steam * 52 / 16_000, 0xFFD8E0DE);

        // Temperature readout
        String tempText = temperature + "°C";
        graphics.drawString(font, tempText, x + 102, y + 100, 0xFFFFC070, false);

        // Fuel remaining
        if (totalBurn > 0) {
            graphics.drawString(font, burnPct + "%", x + 62, y + 100, 0xFFFFB060, false);
        }
    }

    private void drawMeter(GuiGraphics graphics, int x, int y, int meterX, int filledHeight, int color) {
        int bottom = y + 92;
        graphics.fill(x + meterX, y + 34, x + meterX + 14, bottom + 2, 0xFF0A0E12);
        graphics.fill(x + meterX + 2, y + 36, x + meterX + 12, bottom, 0xFF12161A);
        if (filledHeight > 0) {
            graphics.fill(x + meterX + 2, bottom - filledHeight, x + meterX + 12, bottom, color);
        }
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, title, titleLabelX, titleLabelY, 0xFFE8EEF2, false);

        graphics.drawString(font, "WATER", 12, 22, 0xFFA8D4E8, false);
        graphics.drawString(font, "FUEL", 60, 22, 0xFFFFB878, false);
        graphics.drawString(font, "TEMP", 106, 22, 0xFFFF9080, false);
        graphics.drawString(font, "STEAM", 150, 22, 0xFFE0E8E4, false);

        String status = switch (menu.getData(0)) {
            case 2 -> "§aRUNNING";
            case 1 -> "§eFORMED - IDLE";
            default -> "§cINCOMPLETE";
        };
        graphics.drawString(font, status, 8, 122, 0xFFFFFFFF, false);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
