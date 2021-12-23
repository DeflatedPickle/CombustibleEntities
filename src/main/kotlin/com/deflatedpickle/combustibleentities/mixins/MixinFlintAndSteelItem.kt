/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("unused")

package com.deflatedpickle.combustibleentities.mixins

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.FlintAndSteelItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import org.spongepowered.asm.mixin.Mixin

@Mixin(FlintAndSteelItem::class)
abstract class MixinFlintAndSteelItem : Item(
    Settings()
        .maxDamage(64)
        .group(ItemGroup.TOOLS)
) {
    override fun useOnEntity(
        stack: ItemStack,
        user: PlayerEntity,
        entity: LivingEntity,
        hand: Hand,
    ): ActionResult {
        val world = user.world

        world.playSound(
            user,
            entity.blockPos,
            SoundEvents.ITEM_FLINTANDSTEEL_USE,
            SoundCategory.BLOCKS,
            1.0f,
            world.getRandom().nextFloat() * 0.4f + 0.8f
        )

        if (!world.isClient) {
            if (!entity.isFireImmune) {
                entity.fireTicks = entity.fireTicks + 1
                if (entity.fireTicks == 0) {
                    entity.setOnFireFor(8)
                }
                entity.damage(DamageSource.IN_FIRE, 1f)
            }
        }

        stack.damage(1, user) { p: PlayerEntity -> p.sendToolBreakStatus(hand) }

        return ActionResult.success(world.isClient())
    }
}
