package me.minidigger.bettervehicles.model

import org.bukkit.Location
import org.bukkit.block.BlockState
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Villager
import org.bukkit.material.MaterialData
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector


data class ArmorStandModelEntity(val material: MaterialData,
                                 val size: ArmorStandModelSize,
                                 val visible: Boolean,
                                 val offsetLocation: Vector,
                                 val offsetRotation: Vector) {

    var armorstand: ArmorStand? = null
    var blockstate: BlockState? = null
    var villager: Villager? = null

    fun spawn(rootLoc: Location) {
        val location = rootLoc.clone()
        location.add(offsetLocation)
        // yes, this is magic, please don't ask me what this means, I don't know (line 5307)
        val magic = ((1 - size.scale) / 2) - ((1.25 / 16) * size.scale) - 0.02 + size.yOffset
        location.subtract(0.0, magic, 0.0)

        // configure generic armorstand for sizes that use armorstands
        if (size != ArmorStandModelSize.SOLID && size != ArmorStandModelSize.SMALL) {
            armorstand = location.world.spawn(location, ArmorStand::class.java)
            armorstand?.isVisible = false
            armorstand?.isMarker = true
            armorstand?.setAI(false)
            armorstand?.setGravity(false)
            armorstand?.equipment?.helmet = material.toItemStack(1)
            armorstand?.headPose = EulerAngle(Math.toRadians(offsetRotation.x), Math.toRadians(offsetRotation.y), Math.toRadians(offsetRotation.z))
        }

        when (size) {
            ArmorStandModelSize.SMALL -> {
                villager = location.world.spawn(location, Villager::class.java)
                villager?.isInvulnerable = true
                villager?.setAI(false)
                villager?.setGravity(false)
                villager?.age = -1000000
                villager?.isSilent = true
                villager?.equipment?.helmet = material.toItemStack(1)
                villager?.isCollidable = false
                villager?.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY,9999999,1,false,false))
            }
            ArmorStandModelSize.MEDIUM -> {
                armorstand?.isSmall = true
            }
            ArmorStandModelSize.LARGE -> {
                armorstand?.isSmall = false
            }
            ArmorStandModelSize.SOLID -> {
                // save old state
                blockstate = location.block.state

                // set block
                location.block.state.apply {
                    data = material
                    type = material.itemType
                    update(true)
                }
            }
        }
    }

    fun despawn() {
        armorstand?.remove()
        blockstate?.update(true,false)
        villager?.remove()
    }
}