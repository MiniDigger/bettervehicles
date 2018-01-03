package me.minidigger.bettervehicles.model

import me.minidigger.bettervehicles.BetterVehicles
import org.bukkit.DyeColor
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.material.Wool
import org.bukkit.scheduler.BukkitRunnable


data class ArmorStandModel(var entities: MutableList<ArmorStandModelEntity>) {

    private lateinit var rootLocation: Location

    fun spawn(loc: Location) {
        // normalize
        rootLocation = loc.block.location
        rootLocation.add(0.5, 0.0, 0.5)

        entities.forEach { it.spawn(rootLocation) }

        //TODO debug
        object : BukkitRunnable() {
            override fun run() {
                rootLocation.world.spawnParticle(Particle.BLOCK_DUST, rootLocation, 1, 0.0, 0.0, 0.0, 0.0, Wool(DyeColor.LIME))
            }
        }.runTaskTimer(BetterVehicles.instance, 1, 1)
    }

    fun despawn() {
        entities.forEach { it.despawn() }
    }
}