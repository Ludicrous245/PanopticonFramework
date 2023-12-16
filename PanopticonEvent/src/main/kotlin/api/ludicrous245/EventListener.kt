package api.ludicrous245

import org.bukkit.event.Event
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.reflect.typeOf

class EventListener {
    inline fun <reified T: Event> on(consumer: T.() -> Unit){
        when(typeOf<T>()){
            is InventoryClickEvent -> {

            }
        }
    }
}