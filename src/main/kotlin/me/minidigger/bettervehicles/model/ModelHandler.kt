package me.minidigger.bettervehicles.model

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class ModelHandler ( var modelFolder : File){

    private val gson = GsonBuilder().setPrettyPrinting().create()!!

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
}