package com.deflatedpickle.combustibleentities.mixin;

import com.deflatedpickle.combustibleentities.CombustibleEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings({"unused", "UnusedMixin"})
@Mixin({
        FlintAndSteelItem.class,
        FireChargeItem.class
})
public class MixinSetFire extends Item {
    public MixinSetFire(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        SoundEvent soundEvent;

        if (stack.getItem() instanceof FlintAndSteelItem) {
            soundEvent = SoundEvents.ITEM_FLINTANDSTEEL_USE;
        } else if (stack.getItem() instanceof FireChargeItem) {
            soundEvent = SoundEvents.ITEM_FIRECHARGE_USE;
        } else {
            soundEvent = SoundEvents.ENTITY_GENERIC_DRINK;
        }

        return CombustibleEntities.INSTANCE.useOnEntity(soundEvent, stack, user, entity, hand);
    }
}
