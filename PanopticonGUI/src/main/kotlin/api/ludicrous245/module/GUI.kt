package api.ludicrous245.module

import api.ludicrous245.Frame
import api.ludicrous245.project.global.PublicCompanion

class GUI: Module("GUI") {
    companion object{
        val screens: MutableMap<String, Frame> = mutableMapOf()

        /**
         * Default method of create new Screen Object.
         *
         * name: code name for identify.
         * size: define size of inventory. require integer between 1 and 54.
         * builder: lambda section for initialize Screen Object.
         */
        fun createFrame(name: String, size: Int, builder: Frame.() -> Unit): Frame {
            val frame = Frame(name, size, builder)
            screens[name] = frame

            PublicCompanion.pluginManager.registerEvents(frame, PublicCompanion.plugin)

            return frame
        }
    }
}