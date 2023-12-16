package api.ludicrous245.components

import api.ludicrous245.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Pane : Component() {
    override var icon: ItemStack = createIcon(Material.PAPER){
        val meta = itemMeta!!.apply {setDisplayName("Pane")}
        itemMeta =  meta
    }
}