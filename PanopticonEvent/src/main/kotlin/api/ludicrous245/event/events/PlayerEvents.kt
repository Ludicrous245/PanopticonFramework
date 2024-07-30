package api.ludicrous245.event.events

import api.ludicrous245.event.CompressedEvent
import api.ludicrous245.project.global.PublicCompanion.log
import api.ludicrous245.util.UnhandledEventListenException
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerToggleSneakEvent

@Suppress("UNCHECKED_CAST")
class PlayerEvents: CompressedEvent() {
    val interactEvents: MutableList<(event: PlayerInteractEvent) -> Unit> = mutableListOf()
    val joinEvents: MutableList<(event: PlayerJoinEvent) -> Unit> = mutableListOf()
    val quitEvents: MutableList<(event: PlayerQuitEvent) -> Unit> = mutableListOf()
    val moveEvents: MutableList<(event: PlayerMoveEvent) -> Unit> = mutableListOf()
    val sneakEvents: MutableList<(event: PlayerToggleSneakEvent) -> Unit> = mutableListOf()
    val chatEvents: MutableList<(event: AsyncPlayerChatEvent) -> Unit> = mutableListOf()
    val projectileEvents: MutableList<(event: ProjectileLaunchEvent) -> Unit> = mutableListOf()

    inline fun <reified T: PlayerEvent> listen(noinline consumer: (event: T) -> Unit){
        val name = T::class.java.simpleName
        try {
            when (name) {
                "PlayerInteractEvent" -> {
                    interactEvents.add(consumer as (event: PlayerInteractEvent) -> Unit)
                }

                "PlayerJoinEvent" -> {
                    joinEvents.add(consumer as (event: PlayerJoinEvent) -> Unit)
                }

                "PlayerQuitEvent" -> {
                    quitEvents.add(consumer as (event: PlayerQuitEvent) -> Unit)
                }

                "PlayerMoveEvent" -> {
                    moveEvents.add(consumer as (event: PlayerMoveEvent) -> Unit)
                }

                "PlayerToggleSneakEvent" -> {
                    sneakEvents.add(consumer as (event: PlayerToggleSneakEvent) -> Unit)
                }

                "AsyncPlayerChatEvent" -> {
                    chatEvents.add(consumer as (event: AsyncPlayerChatEvent) -> Unit)
                }

                "ProjectileLaunchEvent"-> {
                    projectileEvents.add(consumer as (event: ProjectileLaunchEvent) -> Unit)
                }

                else -> {
                    throw UnhandledEventListenException("Bukkit Event $name is un handled in this compress")
                }
            }
        }catch (exception: Exception){
            log("Failed to Listen Event $name")
            exception.printStackTrace()
        }
    }

    @EventHandler
    fun interact(event: PlayerInteractEvent){
        interactEvents.forEach{
            it(event)
        }
    }

    @EventHandler
    fun join(event: PlayerJoinEvent){
        joinEvents.forEach{
            it(event)
        }
    }

    @EventHandler
    fun quit(event: PlayerQuitEvent){
        quitEvents.forEach{
            it(event)
        }
    }

    @EventHandler
    fun projectile(event: ProjectileLaunchEvent){
        projectileEvents.forEach{
            it(event)
        }
    }

    @EventHandler
    fun move(event: PlayerMoveEvent){
        moveEvents.forEach{
            it(event)
        }
    }

    @EventHandler
    fun sneak(event: PlayerToggleSneakEvent){
        sneakEvents.forEach{
            it(event)
        }
    }

    @EventHandler
    fun chat(event: AsyncPlayerChatEvent){
        chatEvents.forEach{
            it(event)
        }
    }
}