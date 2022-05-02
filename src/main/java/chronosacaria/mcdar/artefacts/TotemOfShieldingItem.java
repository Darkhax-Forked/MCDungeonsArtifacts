package chronosacaria.mcdar.artefacts;

import chronosacaria.mcdar.api.EnchantmentHelper;
import chronosacaria.mcdar.enums.DefenciveArtefactID;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static chronosacaria.mcdar.api.AOECloudHelper.spawnShieldingCloudAtPos;

public class TotemOfShieldingItem extends ArtefactDefenciveItem{
    public TotemOfShieldingItem(DefenciveArtefactID artefactID) {
        super(artefactID);
    }

    public ActionResult useOnBlock (ItemUsageContext itemUsageContext){
        World world = itemUsageContext.getWorld();

        if (world.isClient){
            return ActionResult.SUCCESS;
        } else {
            PlayerEntity itemUsageContextPlayer = itemUsageContext.getPlayer();
            BlockPos itemUseContextBlockPos = itemUsageContext.getBlockPos();

            BlockPos blockPos;
            if (world.getBlockState(itemUseContextBlockPos).getCollisionShape(world, itemUseContextBlockPos).isEmpty()){
                blockPos = itemUseContextBlockPos;
            } else {
                blockPos = itemUseContextBlockPos.offset(itemUsageContext.getPlayerFacing());
            }
            if (itemUsageContextPlayer != null){

                spawnShieldingCloudAtPos(itemUsageContextPlayer,blockPos, 500);
                if (!itemUsageContextPlayer.isCreative()){
                    itemUsageContext.getStack().damage(1, itemUsageContextPlayer,
                            (entity) -> entity.sendToolBreakStatus(itemUsageContext.getHand()));
                }
                EnchantmentHelper.cooldownHelper(itemUsageContextPlayer, this, 600);
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext tooltipContext){
        tooltip.add(new TranslatableText("tooltip_info_item.mcdar.totem_of_shielding_1").formatted(Formatting.ITALIC));
        tooltip.add(new TranslatableText("tooltip_info_item.mcdar.totem_of_shielding_2").formatted(Formatting.ITALIC));
    }
}
