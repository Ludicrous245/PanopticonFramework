package api.ludicrous245.components

import api.ludicrous245.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Button: Component() {
    override var icon: ItemStack = createIcon(Material.IRON_INGOT){
        val meta = itemMeta!!.apply {setDisplayName("Button")}
        itemMeta =  meta
    }
    var action: (whoClicked: Player) -> Unit = {}
}