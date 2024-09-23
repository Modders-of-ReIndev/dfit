package ru.marduk.dfit.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.src.client.gui.ScaledResolution;
import net.minecraft.src.client.gui.StringTranslate;
import net.minecraft.src.client.physics.MovingObjectPosition;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.tileentity.TileEntity;
import net.minecraft.src.game.entity.EntityLiving;
import net.minecraft.src.game.item.ItemStack;
import ru.marduk.dfit.client.gui.TooltipComponent;
import ru.marduk.dfit.client.gui.line.TooltipTextComponent;
import ru.marduk.dfit.client.gui.line.TooltipTextHealthComponent;

import java.util.HashMap;

public class TooltipHandler {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final HashMap<Class<?>, ITooltipResultHandler> providers = new HashMap<>();
    public static ItemStack stack = null;

    public static void handleTooltip(MovingObjectPosition result, TooltipComponent pTooltip) {
        ScaledResolution sr = ScaledResolution.instance;


        if (result != null) {
            pTooltip.setVisible(true);
            pTooltip.clearComponents();

            if (result.entityHit == null) {
                Block block = Block.blocksList[mc.theWorld.getBlockId(result.blockX, result.blockY, result.blockZ)];
                TileEntity tileEntity = mc.theWorld.getBlockTileEntity(result.blockX, result.blockY, result.blockZ);
                stack = new ItemStack(block);

                if (tileEntity != null) {
                    // providers.get(tileEntity.getClass()).handle(tileEntity.getClass(), pTooltip);
                    // providers.get(tileEntity.getClass());
                }

                pTooltip.addComponent(new TooltipTextComponent(stack.getDisplayName()));
                pTooltip.addComponent(new TooltipTextComponent("§9Minecraft", true));
            } else {
                stack = null;
                pTooltip.addComponent(new TooltipTextComponent(StringTranslate.getInstance().translateKey(result.entityHit.getEntityName())));

                // handlers.get(result.entityHit.getClass()).handle(result.entityHit);
                if (result.entityHit instanceof EntityLiving) {
                    EntityLiving entityHit = (EntityLiving)result.entityHit;
                    pTooltip.addComponent(new TooltipTextHealthComponent(entityHit.health));
                }

                pTooltip.addComponent(new TooltipTextComponent("§9Minecraft", true));
            }
        } else {
            pTooltip.setVisible(false);
        }

        pTooltip.drawCentered(sr.getScaledWidth() / 2, 12, stack);
    }
}
