/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.combustibleentities

import net.fabricmc.api.ModInitializer
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

@Suppress("UNUSED")
object CombustibleEntities : ModInitializer {
    private const val MOD_ID = "$[id]"
    private const val NAME = "$[name]"
    private const val GROUP = "$[group]"
    private const val AUTHOR = "$[author]"
    private const val VERSION = "$[version]"

    override fun onInitialize() {
        println(listOf(MOD_ID, NAME, GROUP, AUTHOR, VERSION))
    }

    fun useOnEntity(
        soundEvent: SoundEvent,
        stack: ItemStack,
        user: PlayerEntity,
        entity: LivingEntity,
        hand: Hand,
    ): ActionResult {
        val world = user.world

        world.playSound(
            user,
            entity.blockPos,
            soundEvent,
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
