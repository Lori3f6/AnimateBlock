package blue.melon.minecraft.animatedblock

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Save(
    @Expose
    @SerializedName("screens")
    val screens: ArrayList<Screen>
)