package chronosacaria.mcdar.artefacts;

import chronosacaria.mcdar.Mcdar;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DeathCapMushroomItem extends ArtefactAgilityItem{
    public DeathCapMushroomItem(Settings settings, String id) {
        super(settings);
        Registry.register(Registry.ITEM, new Identifier(Mcdar.MOD_ID, id), this);
    }

    public TypedActionResult<ItemStack> use (World world, PlayerEntity user, Hand hand){
        ItemStack itemStack = user.getStackInHand(hand);

        StatusEffectInstance haste = new StatusEffectInstance(StatusEffects.HASTE, 180, 3);
        StatusEffectInstance swiftness = new StatusEffectInstance(StatusEffects.SPEED, 180, 1);
        user.addStatusEffect(haste);
        user.addStatusEffect(swiftness);
        if (!user.isCreative()){
            itemStack.damage(1, user, (entity) -> entity.sendToolBreakStatus(hand));
        }
        user.getItemCooldownManager().set(this, 600);
        return new TypedActionResult<>(ActionResult.SUCCESS, itemStack);
    }
}