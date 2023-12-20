package com.ludicrous245

import api.ludicrous245.EventListener
import api.ludicrous245.Frame
import api.ludicrous245.components.Pane
import api.ludicrous245.module.GUI
import api.ludicrous245.event.events.InventoryEvents
import api.ludicrous245.event.events.PlayerEvents
import api.ludicrous245.module.Command
import api.ludicrous245.module.Event
import api.ludicrous245.module.Event.Companion.createListener
import api.ludicrous245.project.Project
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent

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

        newInje()
        testCommand(Material.DIAMOND)
    }

    fun testCommand(material: Material){
        Command.createCommand("iktt") {
            run {
                val player = sender as Player

                val testPage = GUI.createFrame("KTT", 9){
                    button(4, 0){
                        icon(material){
                            val meta = itemMeta!!.apply {
                                setDisplayName("인생 리셋 버튼")
                            }
                            itemMeta =  meta
                        }

                        action = {
                            player.health = 0.0; // (명령어 프레임워크 sender 파라미터)
                            // 또는 it.health = 0.0; (action 함수 자체제공 파라미터)
                        }
                    }
                }

                testPage.render(player)
            }
        }
    }

    fun newInje(){
        Command.createCommand("inje") {
            run {
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

                frame.render(sender as Player)
            }
        }
    }
}