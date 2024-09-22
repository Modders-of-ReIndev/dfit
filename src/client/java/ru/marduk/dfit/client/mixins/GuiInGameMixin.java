package ru.marduk.dfit.client.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.src.client.gui.*;
import net.minecraft.src.client.physics.MovingObjectPosition;
import net.minecraft.src.client.renderer.entity.RenderItem;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.marduk.dfit.client.util.DrawUtil;

@Mixin(GuiIngame.class)
public class GuiInGameMixin extends Gui {
    @Unique
    private final static RenderItem dfit$itemRenderer = new RenderItem();


    @Shadow private Minecraft mc;
    @Shadow private double scoreSecondaryY;

    public GuiInGameMixin() {
        super();
    }

    @Inject(method = "renderHUD", at = @At("RETURN"))
    public void renderHUD(int x, int y, float deltaTicks, CallbackInfo ci) {
        MovingObjectPosition result = this.mc.objectMouseOver;

        if (result == null) {return;}
        ScaledResolution sr = ScaledResolution.instance;

        int tooltipPositionY = (int)(this.scoreSecondaryY * 12.0);
        int padding = 4;

        if (result.entityHit == null) {
            /*System.out.print(result.blockX + " ");
            System.out.print(result.blockY + " ");
            System.out.print(result.blockZ + "\n");*/
            Block block = Block.blocksList[this.mc.theWorld.getBlockId(result.blockX, result.blockY, result.blockZ)];
            ItemStack stack = new ItemStack(block);

            String blockName = StringTranslate.getInstance().translateKey(block.getBlockName() + ".name");
            String blockSource = "§9Minecraft";
            int nameWidth = this.mc.fontRenderer.getStringWidth(blockName);
            int sourceWidth = this.mc.fontRenderer.getStringWidth(blockSource);

            int width = Math.max(nameWidth, sourceWidth);
            int tooltipPositionX = (sr.getScaledWidth() / 2) - ((22 + width + padding) / 2);

            DrawUtil.drawTooltipBox(tooltipPositionX, tooltipPositionY, 22 + width + padding, 24, 0xf0100010, 0x505000ff, 0x5028007F);
            this.mc.fontRenderer.drawString(blockName, tooltipPositionX + 22, tooltipPositionY + padding, 16777215);
            this.mc.fontRenderer.drawStringWithShadow(blockSource, tooltipPositionX + 22, tooltipPositionY + 13, 16777215);
            dfit$itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, stack, tooltipPositionX + padding, tooltipPositionY + padding);
        }
    }
}