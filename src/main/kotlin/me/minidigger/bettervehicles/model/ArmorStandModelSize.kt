package me.minidigger.bettervehicles.model

import com.github.salomonbrys.kotson.jsonDeserializer
import com.github.salomonbrys.kotson.jsonSerializer

enum class ArmorStandModelSize(val scale: Double, val yOffset: Double) {
    SMALL(0.28125, 0.5075),
    MEDIUM(0.4375, 0.66),
    LARGE(0.625, 1.56),
    SOLID(1.0, 0.0),
    ;

    companion object {
        fun valueOf(scale: Double): ArmorStandModelSize? {
            return values().find { it.scale == scale }
        }

        //TODO we might also need to serialize to double?
        val serializer = jsonSerializer<ArmorStandModelSize> { it.context.serialize(it.src.name.toLowerCase()) }
        val deserializer = jsonDeserializer {
            if (it.json.asJsonPrimitive.isString) {
                ArmorStandModelSize.valueOf(it.json.asString.toUpperCase())
            } else {
                ArmorStandModelSize.valueOf(it.json.asDouble)
            }
        }
    }
}