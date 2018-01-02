package me.minidigger.bettervehicles.model

import org.bukkit.util.Vector
import java.util.*

data class FileModel(val objects : Array<Object>) {

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

data class Object(val userData : UserData,
                  val uuid : UUID)

data class UserData(val name : String,
                    val selected: Boolean,
                    val rawid : String,
                    val blockid : String,
                    val size : Size,
                    val customname : String,
                    val isItem : Boolean,
                    val isDynamic : Boolean,
                    val visible: Boolean,
                    val customnbt : String,
                    val justAdded : Boolean,
                    val beforeDirProcessing : Vector,
                    val beforeDirProcessingRot : Vector)

enum class Size{
    SMALL, MEDIUM, LARGE, SOLID
}