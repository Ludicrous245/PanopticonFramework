package api.ludicrous245.module

import api.ludicrous245.contents.CommandSelection
import api.ludicrous245.contents.ProxyCommand
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import java.lang.reflect.Field

class Command: Module("Command") {
    companion object{
        lateinit var commandMap: CommandMap

        internal fun register(command: ProxyCommand){
            commandMap.register(command.commandName, "", command)
        }

        fun createCommand(commandName: String, consumer: CommandSelection.() -> Unit){
            CommandSelection(commandName, -1).apply(consumer).build()
        }
    }

    override fun onLoad() {
        val server = Bukkit.getServer()
        val bukkitCommandMap: Field = server.javaClass.getDeclaredField("commandMap")
        bukkitCommandMap.isAccessible = true

        commandMap = bukkitCommandMap.get(server) as CommandMap
    }
}