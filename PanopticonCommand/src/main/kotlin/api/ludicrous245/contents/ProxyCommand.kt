package api.ludicrous245.contents

import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand

class ProxyCommand(val commandName: String): BukkitCommand(commandName) {
    override fun execute(p0: CommandSender, p1: String, p2: Array<out String>): Boolean {
        TODO()
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        TODO()
    }
}