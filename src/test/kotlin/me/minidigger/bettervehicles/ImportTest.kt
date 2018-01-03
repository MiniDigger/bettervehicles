package me.minidigger.bettervehicles

import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.StringSpec
import me.minidigger.bettervehicles.model.ModelHandler
import java.io.File

class ImportTest : StringSpec() {

    init {
        "export of imported file should equal original model" {
            val modelHandler = ModelHandler(File("./src/test/resources"))
            val model = modelHandler.loadModel("tie-fighter")
            modelHandler.saveModel(model, "tie-fighter-exported")
            val model2 = modelHandler.loadModel("tie-fighter-exported")
            model shouldEqual model2
        }

        "shouldn't crash" {
            val modelHandler = ModelHandler(File("./src/test/resources"))
            val model = modelHandler.loadModel("tie-fighter")
            val am = modelHandler.toArmorStandModel(model)
            println(am)
        }
    }
}