package api.ludicrous245

import api.ludicrous245.data.duplicate
import api.ludicrous245.event.CompressedEvent
import api.ludicrous245.project.global.PublicCompanion.log
import api.ludicrous245.project.global.PublicCompanion.plugin
import api.ludicrous245.project.global.PublicCompanion.pluginManager

class EventListener {
    val _events: MutableList<CompressedEvent> = mutableListOf()
    val events: List<CompressedEvent> = _events

    inline fun <reified T: CompressedEvent> on(consumer: T.() -> Unit){
        events.duplicate.filterIsInstance<T>().onEach{ event ->
            runCatching {
                event.apply(consumer)

                if(!event.registered) {
                    pluginManager.registerEvents(event, plugin)
                    event.registered = true
                }
            }.onFailure {
                log("Failed to register event ${T::class.java.simpleName}")
            }
        }
    }
}