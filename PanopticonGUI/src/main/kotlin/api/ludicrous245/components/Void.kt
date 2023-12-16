package api.ludicrous245.components

import api.ludicrous245.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Void : Component() {
    override var icon: ItemStack = createIcon(Material.LIGHT_GRAY_STAINED_GLASS_PANE){
        val meta = itemMeta!!.apply {setDisplayName(" ")}
        itemMeta =  meta
    }
}