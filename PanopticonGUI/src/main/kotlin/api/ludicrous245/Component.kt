package api.ludicrous245

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

abstract class Component {
    abstract var icon: ItemStack
    val loreList: MutableList<String> = mutableListOf()
    var location: FrameLocation = FrameLocation(0, 0)
    var tag: String = ""

    fun createIcon(material: Material, consumer: ItemStack.() -> Unit): ItemStack {
        return ItemStack(material).apply(consumer)
    }

    fun icon(material: Material, consumer: ItemStack.() -> Unit){
        icon = createIcon(material, consumer)
    }
}