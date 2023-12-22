package com.ludicrous245

import api.ludicrous245.EventListener
import api.ludicrous245.Frame
import api.ludicrous245.components.Pane
import api.ludicrous245.module.GUI
import api.ludicrous245.module.Command
import api.ludicrous245.module.Event
import api.ludicrous245.module.Event.Companion.createListener
import api.ludicrous245.project.Project
import api.ludicrous245.project.task.Task
import org.bukkit.Material
import org.bukkit.entity.Player

class PanopticonPlugin: Project("TesPlugin"){
    lateinit var frame: Frame

    companion object{
        lateinit var event: EventListener
    }

    override fun init() {
        module.use(GUI())
        module.use(Event())
        module.use(Command())
        event = createListener()

        projectManager.add("SubPlugin", PanopticonSubPlugin())
        projectManager.load()

        val task = Task.createTask("Initial", 20, false){
            task("a"){
                if(it>10) terminated = true
                log(it)
            }
            task("b"){
                log("oh")
            }
        }

        Command.createCommand("ikttest"){
            executes {
                task.start()
            }
        }
    }
}