package api.ludicrous245.project.global

import api.ludicrous245.module.ModuleManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

object PublicCompanion {
    lateinit var pluginName: String

    lateinit var plugin: JavaPlugin

    val pluginManager = Bukkit.getPluginManager()

    val module = ModuleManager()

    /**
     * print method which shows messages with project name
     */
    fun log(message: Any){
        println("[$pluginName] $message")
    }
}