package api.ludicrous245.project

import api.ludicrous245.project.global.PublicCompanion.pluginName

open class SubProject(val name: String) {
    val projectManager = ProjectManager()

    open fun onLoad(){

    }

    open fun onUnload(){

    }

    /**
     * print method which shows messages with project name
     */
    fun log(message: Any){
        println("[${pluginName}-${name}] $message")
    }
}