package blue.melon.minecraft.animatedblock

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Vector(
    @Expose
    @SerializedName("x") val x: Int,
    @Expose
    @SerializedName("y") val y: Int,
    @Expose
    @SerializedName("z") val z: Int
)

