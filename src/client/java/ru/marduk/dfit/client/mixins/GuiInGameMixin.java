package ru.marduk.dfit.client.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.src.client.gui.*;
import net.minecraft.src.client.physics.MovingObjectPosition;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.entity.EntityLiving;
import net.minecraft.src.game.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.marduk.dfit.client.gui.TooltipComponent;
import ru.marduk.dfit.client.gui.line.TooltipTextComponent;
import ru.marduk.dfit.client.gui.line.TooltipTextHealthComponent;

@Mixin(GuiIngame.class)
public class GuiInGameMixin extends Gui {
    @Unique
    private static final TooltipComponent dfit$tooltip = new TooltipComponent();

    @Shadow private Minecraft mc;

    public GuiInGameMixin() {
        super();
    }

    @Inject(method = "renderHUD", at = @At("RETURN"))
    public void renderHUD(int x, int y, float deltaTicks, CallbackInfo ci) {
        MovingObjectPosition result = this.mc.objectMouseOver;

        if (result == null) {return;}
        ScaledResolution sr = ScaledResolution.instance;

        if (result.entityHit == null) {
            Block block = Block.blocksList[this.mc.theWorld.getBlockId(result.blockX, result.blockY, result.blockZ)];
            ItemStack stack = new ItemStack(block);

            dfit$tooltip.clearComponents();

            dfit$tooltip.addComponent(new TooltipTextComponent(stack.getDisplayName()));
            dfit$tooltip.addComponent(new TooltipTextComponent("§9Minecraft", true));

            dfit$tooltip.drawCentered(sr.getScaledWidth() / 2, 12, stack);
        } else {
            EntityLiving entityHit = (EntityLiving)result.entityHit;

            dfit$tooltip.clearComponents();

            dfit$tooltip.addComponent(new TooltipTextComponent(StringTranslate.getInstance().translateKey(entityHit.getEntityName())));
            dfit$tooltip.addComponent(new TooltipTextHealthComponent(entityHit.health));
            dfit$tooltip.addComponent(new TooltipTextComponent("§9Minecraft", true));

            dfit$tooltip.drawCentered(sr.getScaledWidth() / 2, 12, null);
        }
    }
}