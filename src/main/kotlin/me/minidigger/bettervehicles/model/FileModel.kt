package me.minidigger.bettervehicles.model

import org.bukkit.util.Vector
import java.util.*

data class FileModel(val objects: Array<Object>,
                     val keyframes: Array<Keyframe>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileModel

        if (!Arrays.equals(objects, other.objects)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(objects)
    }
}

data class Object(val userData: UserData,
                  val uuid: UUID)

data class UserData(val name: String,
                    val selected: Boolean,
                    val rawid: String,
                    val blockid: String,
                    val size: ArmorStandModelSize,
                    val customname: String,
                    val isItem: Boolean,
                    val isDynamic: Boolean,
                    val visible: Boolean,
                    val customnbt: String,
                    val translation: Vector,
                    val justAdded: Boolean)

data class Keyframe(val ontick: Int,
                    val objects: Array<KeyFrameObject>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Keyframe

        if (ontick != other.ontick) return false
        if (!Arrays.equals(objects, other.objects)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ontick
        result = 31 * result + Arrays.hashCode(objects)
        return result
    }
}

data class KeyFrameObject(val customname: String,
                          val position: Vector,
                          val rotation: Vector,
                          val size: ArmorStandModelSize,
                          val uuid: UUID,
                          val visible: Boolean)