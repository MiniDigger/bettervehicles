package me.minidigger.bettervehicles.model

import com.google.gson.GsonBuilder
import org.bukkit.Material
import org.bukkit.material.MaterialData
import org.bukkit.util.Vector
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*
import com.github.salomonbrys.kotson.*

class ModelHandler(var modelFolder: File) {

    init {
        if(!modelFolder.exists()){
            modelFolder.mkdir()
        }
    }

    private val gson = GsonBuilder()
            .registerTypeAdapter(ArmorStandModelSize.serializer)
            .registerTypeAdapter(ArmorStandModelSize.deserializer)
            .setPrettyPrinting()
            .create()!!

    fun loadModel(name: String): FileModel {
        val reader = FileReader(File(modelFolder, "$name.json"))
        val model = gson.fromJson<FileModel>(reader)
        reader.close()
        return model
    }

    fun saveModel(model: FileModel, name: String) {
        val writer = FileWriter(File(modelFolder, "$name.json"))
        gson.toJson(model, writer)
        writer.close()
    }

    fun toArmorStandModel(fileModel: FileModel): ArmorStandModel {
        val entities = mutableListOf<ArmorStandModelEntity>()

        println(fileModel)

        fileModel.objects.forEach { obj ->
            val entity = ArmorStandModelEntity(
                    parseMaterialData(obj.userData.rawid),
                    obj.userData.size,
                    obj.userData.visible,
                    findOffset(obj.uuid, fileModel.keyframes[0].objects),
                    findRotation(obj.uuid, fileModel.keyframes[0].objects)
            )
            entities.add(entity)
        }

        val model = ArmorStandModel(entities)
        return model
    }


    private fun parseMaterialData(id: String): MaterialData {
        //TODO better id handling
        return MaterialData(Material.valueOf(id.toUpperCase()))
    }

    private fun findOffset(id: UUID, keyFrameObjects: Array<KeyFrameObject>): Vector {
        val obj = keyFrameObjects.find { it.uuid == id } ?: throw  RuntimeException("Could not find offset for object $id")
        return obj.position
    }

    private fun findRotation(id: UUID, keyFrameObjects: Array<KeyFrameObject>): Vector {
        val obj = keyFrameObjects.find { it.uuid == id } ?: throw  RuntimeException("Could not find rotation for object $id")
        return obj.rotation
    }
}