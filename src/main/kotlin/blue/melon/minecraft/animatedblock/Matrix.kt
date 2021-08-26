package blue.melon.minecraft.animatedblock

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Matrix(
    @Expose
    @SerializedName("i") val i: Vector,
    @Expose
    @SerializedName("j") val j: Vector,
    @Expose
    @SerializedName("k") val k: Vector
)
