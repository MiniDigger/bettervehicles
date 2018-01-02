package me.minidigger.bettervehicles

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import me.minidigger.bettervehicles.model.FileModel
import org.bukkit.plugin.java.JavaPlugin
import java.io.FileReader

class BetterVehicles : JavaPlugin() {

    val gson = GsonBuilder().setPrettyPrinting().create()

    override fun onEnable() {
        super.onEnable()
    }

    fun loadModel(name : String): FileModel {
        val reader = FileReader("$name.json")
        val model = gson.fromJson<FileModel>(reader)
        reader.close()
        return model
    }
}

fun main(args: Array<String>) {
    BetterVehicles().loadModel("tie-fighter")
}