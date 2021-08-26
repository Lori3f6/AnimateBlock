package blue.melon.minecraft.animatedblock

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException

class Loader : JavaPlugin() {
    private val gsonInstance: Gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()
    private val saveFile = File(dataFolder, "save.json")
    val projectors = ArrayList<Projector>()


    override fun onEnable() {
        if (!dataFolder.mkdir() && !dataFolder.isDirectory)
            throw IOException("${dataFolder.absolutePath} should be a directory, but found a file.")
        val save = loadSave(saveFile)
        save.screens.forEach { screen ->
            val projector = Projector(
                File(screen.source),
                screen.transform,
                server.getWorld(screen.location.world)!!
                    .getBlockAt(screen.location.x, screen.location.y, screen.location.z).location,
                screen.frameOffsite
            )
            projectors.add(projector)
        }
        this.server.scheduler.runTaskTimer(this, Runnable {
            projectors.forEach { projector -> projector.printNext() }
        }, 1L, 1L)
        getCommand("projector")?.setExecutor(blue.melon.minecraft.animatedblock.command.Projector(this))
    }

    override fun onDisable() {
        server.scheduler.cancelTasks(this)
        val screenList = ArrayList<Screen>()
        projectors.forEach { projector ->
            val screen = Screen(
                Location(projector.base),
                projector.transform,
                projector.framePlayed,
                projector.source.absolutePath
            )
            screenList.add(screen)
        }
        saveJsonToFile(saveFile, Save(screenList))
    }

    private fun loadSave(saveFile: File): Save {
        return when {
            saveFile.createNewFile() -> Save(ArrayList())
            saveFile.isDirectory -> throw IOException(saveFile.absolutePath + " should be a file, but found a directory.")
            else -> {
                var saveLoaded: Save?
                val configFileReader = FileReader(saveFile, Charsets.UTF_8)
                saveLoaded = gsonInstance.fromJson(configFileReader, Save::class.java)
                if (saveLoaded == null) {
                    saveLoaded = Save(ArrayList())
                }
                saveLoaded
            }
        }
    }

    @Throws(IOException::class)
    private fun <T> saveJsonToFile(file: File, instance: T) {
        val fileOutputStream = FileOutputStream(file, false)
        fileOutputStream.write(gsonInstance.toJson(instance).toByteArray(Charsets.UTF_8))
        fileOutputStream.flush()
        fileOutputStream.close()
    }
}