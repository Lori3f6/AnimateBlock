import org.bukkit.Material
import kotlin.math.pow

enum class ColorScheme(private val red: Int, private val green: Int, private val blue: Int) {
    SNOW_BLOCK(252, 252, 252),
    SMOOTH_SANDSTONE(224, 216, 174),
    SMOOTH_RED_SANDSTONE(185, 101, 34),
    SMOOTH_QUARTZ(231, 223, 215),
    STRIPPED_WARPED_HYPHAE(67, 159, 157),
    STRIPPED_CRIMSON_HYPHAE(148, 61, 97),
    STRIPPED_DARK_OAK_WOOD(95, 74, 48),
    STRIPPED_ACACIA_WOOD(179, 95, 61),
    STRIPPED_JUNGLE_WOOD(178, 132, 89),
    STRIPPED_BIRCH_WOOD(205, 187, 127),
    STRIPPED_SPRUCE_WOOD(120, 90, 54),
    STRIPPED_OAK_WOOD(186, 153, 92),

    BLACK_TERRACOTTA(36, 22, 16),
    BLUE_TERRACOTTA(72, 57, 90),
    GRAY_TERRACOTTA(57, 42, 36),
    LIGHT_BLUE_TERRACOTTA(113, 107, 136),
    RED_TERRACOTTA(140, 60, 46),
    PURPLE_TERRACOTTA(114, 67, 83),
    PINK_TERRACOTTA(195, 93, 9),
    MAGENTA_TERRACOTTA(147, 86, 107),
    GREEN_TERRACOTTA(75, 82, 42),
    CYAN_TERRACOTTA(88, 92, 91),
    LIME_TERRACOTTA(102, 115, 50),
    ORANGE_TERRACOTTA(198, 101, 47),
    BROWN_TERRACOTTA(78, 52, 37),
    LIGHT_GRAY_TERRACOTTA(133, 107, 98),
    YELLOW_TERRACOTTA(225, 161, 43),
    WHITE_TERRACOTTA(245, 207, 188),

    BLACK_WOOL(28, 28, 32),
    BLUE_WOOL(58, 63, 164),
    GRAY_WOOL(59, 64, 67),
    LIGHT_BLUE_WOOL(82, 205, 255),
    RED_WOOL(192, 46, 41),
    PURPLE_WOOL(124, 42, 171),
    PINK_WOOL(255, 154, 178),
    MAGENTA_WOOL(200, 81, 191),
    GREEN_WOOL(80, 103, 29),
    CYAN_WOOL(26, 175, 183),
    LIME_WOOL(122, 193, 27),
    ORANGE_WOOL(242, 120, 23),
    BROWN_WOOL(117, 73, 42),
    LIGHT_GRAY_WOOL(218, 218, 208),
    YELLOW_WOOL(255, 214, 35),
    WHITE_WOOL(245, 251, 252),

    BLACK_CONCRETE(8, 10, 15),
    BLUE_CONCRETE(45, 47, 142),
    GRAY_CONCRETE(53, 56, 60),
    LIGHT_BLUE_CONCRETE(36, 136, 198),
    RED_CONCRETE(141, 35, 35),
    PURPLE_CONCRETE(99, 31, 154),
    PINK_CONCRETE(211, 100, 141),
    MAGENTA_CONCRETE(167, 47, 157),
    GREEN_CONCRETE(72, 90, 36),
    CYAN_CONCRETE(21, 118, 134),
    LIME_CONCRETE(92, 166, 24),
    ORANGE_CONCRETE(219, 95, 0),
    BROWN_CONCRETE(95, 58, 31),
    LIGHT_GRAY_CONCRETE(123, 123, 113),
    YELLOW_CONCRETE(239, 174, 21),
    WHITE_CONCRETE(204, 209, 210);

    val bukkitMaterial: Material = Material.getMaterial(this.name)!!

    companion object {
        private fun getNearestColorScheme(red: Int, green: Int, blue: Int): ColorScheme {
            var scheme: ColorScheme = CYAN_CONCRETE
            var diff = 256 * 256 * 4 //unreachable initial difference
            values().forEach {
                val delta = it.getDiff(red, green, blue).toList().sum()
                if (delta < diff) {
                    diff = delta
                    scheme = it
                }
            }
            return scheme
        }

        fun getNearestColorScheme(color: Int): ColorScheme {
            val blue = color and 0x000000ff
            val green = color and 0x0000ff00 shr 8
            val red = color and 0x00ff0000 shr 16
            return getNearestColorScheme(red, green, blue)
        }
    }

    fun getBukkitMaterialName(): String {
        return this.name
    }

    fun getMinecraftItemName(): String {
        return "minecraft:${this.name.lowercase()}"
    }

    fun getDiff(red: Int, blue: Int, green: Int): Triple<Int, Int, Int> {
        return Triple(
            (this.red - red.toDouble()).pow(2.0).toInt(),
            (this.green - green.toDouble()).pow(2.0).toInt(),
            (this.blue - blue.toDouble()).pow(2.0).toInt()
        )
    }

}