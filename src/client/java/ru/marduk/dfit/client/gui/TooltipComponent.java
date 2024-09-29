package ru.marduk.dfit.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.src.client.renderer.entity.RenderItem;
import net.minecraft.src.game.item.ItemStack;
import ru.marduk.dfit.Dfit;
import ru.marduk.dfit.client.gui.line.ITooltipLineComponent;
import ru.marduk.dfit.client.util.ConfigTheme;
import ru.marduk.dfit.client.util.DrawUtil;

import java.util.*;

public class TooltipComponent {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final RenderItem itemRenderer = new RenderItem();
    private static float sizeX = 0, sizeY = 0, positionX = 0, positionY = 0;
    private static ConfigTheme theme = ConfigTheme.INSTANCE;
    private static final int padding = 4;
    private static final float lerpValue = 0.3f;
    private int width = 0, height = 0;
    private int bg = theme.bg, grad1 = theme.grad1, grad2 = theme.grad2;
    private int bgOpacity = 0, gradOpacity = 0;
    private boolean fadeOut = true, actuallyVisible = false;

    private final List<ITooltipLineComponent> components = new ArrayList<>();

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private int lerp(int a, int b, float t) {
        return (int)((float)a + ((float)b - (float)a) * t);
    }

    private void recomputeSize() {
        Optional<ITooltipLineComponent> value = components.stream().max(Comparator.comparing(ITooltipLineComponent::width));
        width = (value.map(ITooltipLineComponent::width).orElse(22));
        height = (int)(padding * 1.5) + (components.size() * 9);
    }

    private void renderStrings(int baseXOffset, int baseYOffset) {
        for (int i = 0; i < components.size(); i++) {
            ITooltipLineComponent component = components.get(i);

            component.render(baseXOffset, baseYOffset + i * 9, width, 8);
        }
    }

    private void interpolateStuff(int tooltipPositionX, int tooltipPositionY, int iconOffset) {
        int targetWidth = (iconOffset + width + padding);
        int targetHeight = height;

        int targetX = tooltipPositionX - ((iconOffset + width + padding) / 2);

        if (Dfit.config.animateGuis) {
            sizeX = lerp(sizeX, (float) targetWidth, lerpValue);
            sizeY = lerp(sizeY, (float) targetHeight, lerpValue);

            positionX = lerp(positionX, (float) targetX, lerpValue);
            positionY = lerp(positionY, (float) tooltipPositionY, lerpValue);

            bgOpacity = lerp(bgOpacity, (fadeOut ? 0x00 : 0xf0), lerpValue);
            gradOpacity = lerp(gradOpacity, (fadeOut ? 0x00 : 0x50), lerpValue);

            bg = DrawUtil.modifyColorAlpha(bg, (byte) bgOpacity);
            grad1 = DrawUtil.modifyColorAlpha(grad1, (byte) gradOpacity);
            grad2 = DrawUtil.modifyColorAlpha(grad2, (byte) gradOpacity);
            actuallyVisible = (gradOpacity > 15);
        } else {
            if (bg != theme.bg)
                bg = theme.bg;
            if (grad1 != theme.grad1)
                grad1 = theme.grad1;
            if (grad2 != theme.grad2)
                grad2 = theme.grad2;

            if (fadeOut) {
                bgOpacity = 0;
                gradOpacity = 0;
            } else {
                bgOpacity = 0xf0;
                gradOpacity = 0x50;
            }

            sizeX = targetWidth;
            sizeY = targetHeight;

            positionX = targetX;
            positionY = tooltipPositionY;

            actuallyVisible = !fadeOut;
        }
    }

    public void setVisible(boolean visible) {
        this.fadeOut = !visible;
    }

    public void clearComponents() {
        this.components.clear();
    }

    public void addComponent(ITooltipLineComponent component) {
        this.components.add(component);
    }

    public void drawCentered(int tooltipPositionX, int tooltipPositionY, ItemStack itemStack) {
        int iconOffset = itemStack != null ? 22 : padding;
        interpolateStuff(tooltipPositionX, tooltipPositionY, iconOffset);


        if (gradOpacity != 0) {
            DrawUtil.drawTooltipBox((int)positionX, (int)positionY, (int)sizeX, (int)sizeY, bg, grad1, grad2);
            if (actuallyVisible) {
                recomputeSize();

                renderStrings((int)positionX + iconOffset, (int)positionY + padding);
                if (itemStack != null)
                    itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, (int)positionX + padding, (int)positionY + padding);
            }
        }
    }
}
