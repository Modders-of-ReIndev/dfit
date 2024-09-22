package ru.marduk.dfit.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.src.client.renderer.entity.RenderItem;
import net.minecraft.src.game.item.ItemStack;
import ru.marduk.dfit.client.gui.line.ITooltipLineComponent;
import ru.marduk.dfit.client.util.DrawUtil;

import java.util.*;

public class TooltipComponent {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final RenderItem itemRenderer = new RenderItem();

    private static final int padding = 4;
    private int width = 0;
    private int height = 0;
    private final List<ITooltipLineComponent> components = new ArrayList<>();

    private void recomputeSize() {
        width = components.stream().max(Comparator.comparing(ITooltipLineComponent::width)).get().width();
        height = (int)(padding * 1.5) + (components.size() * 9);
    }

    private void renderStrings(int baseXOffset, int baseYOffset) {
        for (int i = 0; i < components.size(); i++) {
            ITooltipLineComponent component = components.get(i);

            component.render(baseXOffset, baseYOffset + i * 9, width, 8);
        }
    }

    public void clearComponents() {
        this.components.clear();
    }

    public void addComponent(ITooltipLineComponent component) {
        this.components.add(component);
    }

    public void drawCentered(int tooltipPositionX, int tooltipPositionY, ItemStack itemStack) {
        recomputeSize();
        int iconOffset = itemStack != null ? 22 : padding;
        tooltipPositionX = tooltipPositionX - ((iconOffset + width + padding) / 2);

        DrawUtil.drawTooltipBox(tooltipPositionX, tooltipPositionY, iconOffset + width + padding, height, 0xf0100010, 0x505000ff, 0x5028007F);
        renderStrings(tooltipPositionX + iconOffset, tooltipPositionY + padding);
        if (itemStack != null)
            itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, tooltipPositionX + padding, tooltipPositionY + padding);
    }
}
