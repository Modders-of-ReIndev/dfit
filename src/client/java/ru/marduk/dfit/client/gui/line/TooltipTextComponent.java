package ru.marduk.dfit.client.gui.line;

import net.minecraft.client.Minecraft;

public class TooltipTextComponent implements ITooltipLineComponent{
    public final int renderedTextWidth;
    public final String text;
    public final boolean shadow;

    public TooltipTextComponent(String str) {
        this(str, false);
    }

    public TooltipTextComponent(String str, boolean shadow) {
        this.text = str;
        this.renderedTextWidth = Minecraft.getInstance().fontRenderer.getStringWidth(str);
        this.shadow = shadow;
    }

    public void render(int x, int y, int width, int height) {
        Minecraft.getInstance().fontRenderer.drawString(this.text, x, y, 16777215);
    }

    @Override
    public int width() {
        return renderedTextWidth;
    }
}
