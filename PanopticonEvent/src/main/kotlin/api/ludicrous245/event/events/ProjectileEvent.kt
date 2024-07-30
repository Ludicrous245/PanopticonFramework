package api.ludicrous245.event.events

import api.ludicrous245.event.CompressedEvent
import api.ludicrous245.project.global.PublicCompanion.log
import api.ludicrous245.util.UnhandledEventListenException
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.*

class ProjectileEvent: CompressedEvent(){
    val projectileEvents: MutableList<(event: ProjectileLaunchEvent) -> Unit> = mutableListOf()

    inline fun <reified T: ProjectileLaunchEvent> listen(noinline consumer: (event: T) -> Unit){
        val name = T::class.java.simpleName
        try {
            when (name) {
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
    fun projectile(event: ProjectileLaunchEvent){
        projectileEvents.forEach{
            it(event)
        }
    }
}