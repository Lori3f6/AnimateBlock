package blue.melon.minecraft.animatedblock

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Screen(
    @Expose
    @SerializedName("location") val location: Location,
    @Expose
    @SerializedName("transform") val transform: Matrix,
    @Expose
    @SerializedName("frameOffsite") val frameOffsite: Int,
    @Expose
    @SerializedName("source") val source: String
)