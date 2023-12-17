package api.ludicrous245.module

import api.ludicrous245.EventListener
import api.ludicrous245.data.duplicate
import api.ludicrous245.event.CompressedEvent
import api.ludicrous245.event.events.PlayerEvents

class Event: Module("Event") {
    override fun onLoad() {
        rawEvents.let {
            it.add(PlayerEvents())
        }
    }

    companion object{
        internal val rawEvents: MutableList<CompressedEvent> = mutableListOf()

        fun createListener(): EventListener{
            return EventListener().apply {
                _events.addAll(rawEvents.duplicate)
            }
        }
    }
}