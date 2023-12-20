package api.ludicrous245.event.events

import api.ludicrous245.event.CompressedEvent
import api.ludicrous245.project.global.PublicCompanion
import api.ludicrous245.util.UnhandledEventListenException
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.event.inventory.InventoryOpenEvent

@Suppress("UNCHECKED_CAST")
class InventoryEvents: CompressedEvent() {
    val clickEvents: MutableList<(event: InventoryClickEvent) -> Unit> = mutableListOf()
    val closeEvents: MutableList<(event: InventoryCloseEvent) -> Unit> = mutableListOf()
    val openEvents: MutableList<(event: InventoryOpenEvent) -> Unit> = mutableListOf()

    inline fun <reified T: InventoryEvent> listen(noinline consumer: (event: T) -> Unit){
        val name = T::class.java.simpleName
        try {
            when (name) {
                "InventoryClickEvent" -> {
                    clickEvents.add(consumer as (event: InventoryClickEvent) -> Unit)
                }

                "InventoryCloseEvent" -> {
                    closeEvents.add(consumer as (event: InventoryCloseEvent) -> Unit)
                }

                "InventoryOpenEvent" -> {
                    openEvents.add(consumer as (event: InventoryOpenEvent) -> Unit)
                }

                else -> {
                    throw UnhandledEventListenException("Bukkit Event $name is un handled in this compress")
                }
            }
        }catch (exception: Exception){
            PublicCompanion.log("Failed to Listen Event $name")
            exception.printStackTrace()
        }
    }

    @EventHandler
    fun click(event: InventoryClickEvent){
        clickEvents.forEach{
            it(event)
        }
    }

    @EventHandler
    fun close(event: InventoryCloseEvent){
        closeEvents.forEach{
            it(event)
        }
    }

    @EventHandler
    fun open(event: InventoryOpenEvent){
        openEvents.forEach{
            it(event)
        }
    }
}