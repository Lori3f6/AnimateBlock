package blue.melon.minecraft.animatedblock.command

import blue.melon.minecraft.animatedblock.Loader
import blue.melon.minecraft.animatedblock.Matrix
import blue.melon.minecraft.animatedblock.Projector
import blue.melon.minecraft.animatedblock.Vector
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.io.File

class Projector(
    private val pluginInstance: Loader
) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false
        when (args[0]) {
            "create" -> {
                if (args.size < 16)
                    return false

                val world = pluginInstance.server.getWorld(args[11])
                if (world == null) {
                    sender.sendMessage("${args[11]} 不是有效的世界")
                    return false
                }
                pluginInstance.projectors.add(
                    Projector(
                        File("${pluginInstance.dataFolder.absolutePath}/clips/${args[1]}"),
                        Matrix(
                            Vector(args[2].toInt(), args[3].toInt(), args[4].toInt()),
                            Vector(args[5].toInt(), args[6].toInt(), args[7].toInt()),
                            Vector(args[8].toInt(), args[9].toInt(), args[10].toInt())
                        ),
                        world.getBlockAt(args[12].toInt(), args[13].toInt(), args[14].toInt()).location,
                        args[15].toInt()
                    )
                )
                return true
            }

            else -> return false
        }

    }
}