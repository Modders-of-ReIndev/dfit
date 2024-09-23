package ru.marduk.dfit.client.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.src.client.gui.*;
import net.minecraft.src.client.physics.MovingObjectPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.marduk.dfit.client.gui.TooltipComponent;
import ru.marduk.dfit.client.handler.TooltipHandler;

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

        TooltipHandler.handleTooltip(result, dfit$tooltip);
    }
}