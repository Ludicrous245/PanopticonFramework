package api.ludicrous245.components

import api.ludicrous245.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Slot: Component() {
    override var icon: ItemStack = createIcon(Material.BOOK){
        val meta = itemMeta!!.apply {setDisplayName("Slot")}
        itemMeta =  meta
    }
    var holder: ItemStack? = null
    var enableAction: (whoEnabled: Player) -> Unit = {}
    var disableAction: (whoDisabled: Player) -> Unit = {}
    var singleMode: Boolean = true
    var save: Boolean = false
}