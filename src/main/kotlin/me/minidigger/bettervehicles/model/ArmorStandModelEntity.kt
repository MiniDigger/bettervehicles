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

        // adjust location to after rotation
        val offset = getRotOffset(size, Vector(offsetRotation.x, -offsetRotation.y, -offsetRotation.z))
        println(offset)
        location.add(offset)

        // configure generic armorstand for sizes that use armorstands
        if (size != ArmorStandModelSize.SOLID && size != ArmorStandModelSize.SMALL) {
            armorstand = location.world.spawn(location, ArmorStand::class.java)
            armorstand?.isVisible = false
            //armorstand?.isMarker = true
            armorstand?.setAI(false)
            armorstand?.setGravity(false)
            armorstand?.equipment?.helmet = material.toItemStack(1)
            armorstand?.headPose = EulerAngle(offsetRotation.x, -offsetRotation.y, -offsetRotation.z)
        }

        when (size) {
            ArmorStandModelSize.SMALL -> {
                // we use pitch and yaw for rotation here //TODO make this actually work
                location.pitch = -offsetRotation.y.toFloat()
                location.yaw = offsetRotation.x.toFloat()
                // surprise, the small size is actually a villager!
                villager = location.world.spawn(location, Villager::class.java)
                villager?.isInvulnerable = true
                villager?.setAI(false)
                villager?.setGravity(false)
                villager?.age = -1000000
                villager?.isSilent = true
                villager?.equipment?.helmet = material.toItemStack(1)
                villager?.isCollidable = false //TODO fix collision
                villager?.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1, false, false))
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

    private fun getRotOffset(size: ArmorStandModelSize, rot: Vector): Vector = when (size) {
        ArmorStandModelSize.SMALL -> {
            if (rot.x == 0.0 && rot.z == 0.0) {
                Vector(0.0, -0.021973, 0.0)
            } else if (rot.x == 0.0) {
                Vector(0.124557, 0.036109, 0.0)
            } else {
                Vector(0.0, 0.036109, -0.124557)
            }
        }
        ArmorStandModelSize.MEDIUM -> {
            if (rot.x == 0.0 && rot.z == 0.0) {
                Vector(0.0, 0.0437, 0.0)
            } else if (rot.x == 0.0) {
                Vector(0.134058, 0.106262, 0.0)
            } else {
                Vector(0.0, 0.106262, -0.134058)
            }
        }
        ArmorStandModelSize.LARGE -> {
            if (rot.x == 0.0 && rot.z == 0.0) {
                Vector(0.0, 0.0625, 0.0)
            } else if (rot.x == 0.0) {
                Vector(0.191511, 0.151803, 0.0)
            } else {
                Vector(0.0, 0.151803, -0.191511)
            }
        }
        ArmorStandModelSize.SOLID -> Vector(0.0, -0.078125, 0.0)
    }

    fun despawn() {
        armorstand?.remove()
        blockstate?.update(true, false)
        villager?.remove()
    }
}