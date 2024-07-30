package com.ludicrous245

import api.ludicrous245.EventListener
import api.ludicrous245.Frame
import api.ludicrous245.components.Pane
import api.ludicrous245.contents.data.Argset
import api.ludicrous245.module.GUI
import api.ludicrous245.module.Command
import api.ludicrous245.module.Event
import api.ludicrous245.module.Event.Companion.createListener
import api.ludicrous245.project.Project
import api.ludicrous245.project.task.Task
import com.sun.org.apache.bcel.internal.util.Args
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

        Command.createCommand("ikttest"){
            case("minjar"){
                reqArg(Argset.Players)
            }

            case("munjar"){
                reqArg("sam")
            }

            reqArg("jar")
        }

    }
}