package com.ludicrous245

import api.ludicrous245.EventListener
import api.ludicrous245.Frame
import api.ludicrous245.module.GUI
import api.ludicrous245.components.Pane
import api.ludicrous245.event.events.InventoryEvents
import api.ludicrous245.event.events.PlayerEvents
import api.ludicrous245.module.Event
import api.ludicrous245.module.Event.Companion.createListener
import api.ludicrous245.project.Project
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent

class Plugin: Project("TesPlugin"){
    lateinit var frame: Frame

    companion object{
        lateinit var event: EventListener
    }

    override fun init() {
        module.use(GUI())
        module.use(Event())
        event = createListener()

        projectManager.add("SubPlugin", PanopticonSubPlugin())
        projectManager.load()

        event.on<PlayerEvents> {
            listen<PlayerInteractEvent> {
                log("인터렉션")
            }
        }

        event.on<InventoryEvents>{

        }
    }

    override fun close() {

    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            if(label == "inje"){
                frame = GUI.createFrame("mjr", 9){
                    var page = 0

                    pane(4, 0){
                        tag = "@"

                        icon(Material.DIAMOND){
                            val meta = itemMeta!!.apply {
                                setDisplayName("${page}페이지")
                            }
                            itemMeta =  meta
                        }
                    }

                    fun changePage(){
                        getComponents<Pane>("@").first().let { pane ->
                            pane.icon = createIcon(Material.DIAMOND) {
                                val paneMeta = itemMeta!!.apply {
                                    setDisplayName("${page}페이지")
                                }

                                this.itemMeta = paneMeta
                            }
                        }
                    }

                    button(0, 0) {
                        action = {
                            if (page > 0) {
                                page --
                                changePage()
                                reload()
                            }
                        }

                        icon(Material.IRON_INGOT) {
                            val meta = itemMeta!!.apply {
                                setDisplayName("이전 페이지")
                            }

                            itemMeta = meta
                        }
                    }

                    button(8, 0){
                        action = {
                            page++
                            changePage()
                            reload()
                        }

                        icon(Material.IRON_INGOT) {
                            val meta = itemMeta!!.apply {
                                setDisplayName("다음 페이지")
                            }

                            itemMeta = meta
                        }
                    }

                    displayName = "김문남의 묘"
                }

                frame.render(sender)
            }
        }
        return true
    }
}