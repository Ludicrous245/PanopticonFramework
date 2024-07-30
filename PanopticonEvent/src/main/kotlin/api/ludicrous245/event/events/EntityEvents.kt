package api.ludicrous245.event.events

import api.ludicrous245.event.CompressedEvent
import api.ludicrous245.project.global.PublicCompanion.log
import api.ludicrous245.util.UnhandledEventListenException
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityDropItemEvent
import org.bukkit.event.entity.EntityEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.entity.ProjectileLaunchEvent

class EntityEvents: CompressedEvent(){
    val bowEvent: MutableList<(event: EntityShootBowEvent) -> Unit> = mutableListOf()
    val damageEvent: MutableList<(event: EntityDamageEvent) -> Unit> = mutableListOf()
    val damageByEvent: MutableList<(event: EntityDamageByEntityEvent) -> Unit> = mutableListOf()
    val deathEvent: MutableList<(event: EntityDeathEvent) -> Unit> = mutableListOf()
    val spawnEvent: MutableList<(event: EntitySpawnEvent) -> Unit> = mutableListOf()
    val dropEvent: MutableList<(event: EntityDropItemEvent) -> Unit> = mutableListOf()

    inline fun <reified T: EntityEvent> listen(noinline consumer: (event: T) -> Unit){
        val name = T::class.java.simpleName
        try {
            when (name) {
                "EntityShootBowEvent"-> {
                    bowEvent.add(consumer as (event: EntityShootBowEvent) -> Unit)
                }

                "EntityDamageEvent"-> {
                    damageEvent.add(consumer as (event: EntityDamageEvent) -> Unit)
                }

                "EntityDamageByEntityEvent"-> {
                    damageByEvent.add(consumer as (event: EntityDamageByEntityEvent) -> Unit)
                }

                "EntityDeathEvent"-> {
                    deathEvent.add(consumer as (event: EntityDeathEvent) -> Unit)
                }

                "EntitySpawnEvent"-> {
                    spawnEvent.add(consumer as (event: EntitySpawnEvent) -> Unit)
                }

                "EntityDropItemEvent"-> {
                    dropEvent.add(consumer as (event: EntityDropItemEvent) -> Unit)
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
    fun bow(event: EntityShootBowEvent){
        bowEvent.forEach{
            it(event)
        }
    }

    @EventHandler
    fun damage(event: EntityDamageEvent){
        damageEvent.forEach{
            it(event)
        }
    }

    @EventHandler
    fun damage(event: EntityDamageByEntityEvent){
        damageByEvent.forEach{
            it(event)
        }
    }

    @EventHandler
    fun death(event: EntityDeathEvent){
        deathEvent.forEach{
            it(event)
        }
    }

    @EventHandler
    fun drop(event: EntityDropItemEvent){
        dropEvent.forEach{
            it(event)
        }
    }

    @EventHandler
    fun spawn(event: EntitySpawnEvent){
        spawnEvent.forEach{
            it(event)
        }
    }
}