package api.ludicrous245.event

import org.bukkit.event.Listener

abstract class CompressedEvent: Listener {
    var registered: Boolean = false
}