package api.ludicrous245.project

import api.ludicrous245.project.global.PublicCompanion
import api.ludicrous245.project.global.PublicCompanion.plugin
import api.ludicrous245.project.global.PublicCompanion.pluginName
import org.bukkit.plugin.java.JavaPlugin

abstract class Project(private val projectName: String): JavaPlugin() {
    val projectManager = ProjectManager()

    val module = PublicCompanion.module

    /**
     * Startup Method of PanopticonProject.
     * you need to override this method instead of onEnable().
     */
    open fun init() {

    }

    open fun close(){

    }

    /**
     * print method which shows messages with project name
     */
    fun log(message: Any){
        println("[$projectName] $message")
    }

    /**
     * impl of JavaPlugin
     */
    override fun onEnable(){
        initProject()
        init()
    }

    override fun onDisable() {
        module.close()
        close()
    }

    /**
     * pre init method
     */
    private fun initProject() {
        pluginName = projectName
        plugin = this

        log("Successfully Initialized.")
    }
}