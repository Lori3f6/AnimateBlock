package blue.melon.minecraft.animatedblock

import ColorScheme
import java.io.File
import java.io.FileInputStream
import java.util.zip.GZIPInputStream

class Projector(
    val source: File,
    val transform: Matrix,
    val base: org.bukkit.Location,
    var framePlayed: Int
) {
    private val colorSchemes = ColorScheme.values()
    private var sourceStream = GZIPInputStream(FileInputStream(source))
    private var sourceReader = sourceStream.bufferedReader(Charsets.UTF_8)
    private var width: Int
    private var height: Int

    init {
        val meta = sourceReader.readLine().split(" ")
        width = meta[0].toInt()
        height = meta[1].toInt()
        sourceReader.skip((width + 2) * height * framePlayed * 1L)
    }

    private fun restart() {
        sourceReader.close()
        sourceStream.close()
        sourceStream = GZIPInputStream(FileInputStream(source))
        sourceReader = sourceStream.bufferedReader(Charsets.UTF_8)
        val meta = sourceReader.readLine().split(" ")
        width = meta[0].toInt()
        height = meta[1].toInt()
        framePlayed = 0
        printNext()
    }

    fun printNext() {
        for (y in 0 until height) {
            val read = sourceReader.readLine()
            if (read == null) {
                restart()
                return
            }
            val source = read.toCharArray()
            for (x in source.indices) {
                val offset = transform(Vector(x, y, 0), transform)
                val toPrint = base.clone()
                toPrint.x += offset.x
                toPrint.y += offset.y
                toPrint.z += offset.z
                toPrint.block.type = colorSchemes[source[x].code - 32].bukkitMaterial
            }
        }
        framePlayed++
    }

    private fun transform(original: Vector, transform: Matrix): Vector {
        return Vector(
            original.x * transform.i.x + original.y * transform.j.x + original.z * transform.k.x,
            original.x * transform.i.y + original.y * transform.j.y + original.z * transform.k.y,
            original.x * transform.i.z + original.y * transform.j.z + original.z * transform.k.z
        )
    }


}