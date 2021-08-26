package blue.melon.minecraft.animatedblock

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.bukkit.Location

data class Location(
    @Expose
    @SerializedName("world") val world: String,
    @Expose
    @SerializedName("x") val x: Int,
    @Expose
    @SerializedName("y") val y: Int,
    @Expose
    @SerializedName("z") val z: Int
) {
    constructor(location: Location) : this(location.world!!.name, location.blockX, location.blockY, location.blockZ)
}