package me.minidigger.bettervehicles

import me.minidigger.bettervehicles.model.ModelHandler
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class BetterVehicles : JavaPlugin(), Listener {

    val modelHandler = ModelHandler(dataFolder)

    override fun onEnable() {
        instance = this
        server.pluginManager.registerEvents(this, this)
        super.onEnable()
    }

    //TODO replace with acf
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size == 2 && args[0] == "load" && sender is Player) {
            val fileModel = modelHandler.loadModel(args[1])
            val model = modelHandler.toArmorStandModel(fileModel)
            model.spawn(sender.location)

            object : BukkitRunnable(){
                override fun run() {
                    model.despawn()
                }
            }.runTaskLater(this, 20*20)
        }
        return super.onCommand(sender, command, label, args)
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        event.player.isCollidable = false
    }

    companion object {
        lateinit var instance: BetterVehicles
    }
}