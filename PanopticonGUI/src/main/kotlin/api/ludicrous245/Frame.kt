package api.ludicrous245

import api.ludicrous245.components.Button
import api.ludicrous245.components.Pane
import api.ludicrous245.components.Slot
import api.ludicrous245.components.Void
import api.ludicrous245.project.global.PublicCompanion.log
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

class Frame(val name: String, val size: Int, builder: Frame.() -> Unit): Listener {
    val objects: MutableMap<Int, Component> = mutableMapOf()
    val inventory: Inventory

    /**
     * displayName: define name of inventory. default is same as value of variable 'name'.
     */
    var displayName: String = name

    init {
        for(i in 0..<size) objects[i] = Void()

        apply(builder)

        inventory = Bukkit.createInventory(null, size, displayName)
        reload()
    }

    fun createIcon(material: Material, consumer: ItemStack.() -> Unit): ItemStack {
        return ItemStack(material).apply(consumer)
    }

    /**
     * method of adding PaneObjet
     * x: x location of inventory
     * y: y location of inventory
     * builder: lambda section of initializing IconObjet class
     */
    fun pane(x: Int, y: Int, builder: Pane.() -> Unit){
        val tempIcon = Pane().apply(builder)
        tempIcon.location = FrameLocation(x, y)

        objects[tempIcon.location.asInt] = tempIcon
    }

    fun pane(location: FrameLocation, builder: Pane.() -> Unit){
        val tempIcon = Pane().apply(builder)
        tempIcon.location = location

        objects[tempIcon.location.asInt] = tempIcon
    }

    /**
     * method of adding ButtonObjet
     * x: x location of inventory
     * y: y location of inventory
     * builder: lambda section of initializing ButtonObjet class
     */
    fun button(x: Int, y: Int, builder: Button.() -> Unit){
        val tempButton = Button().apply(builder)
        tempButton.location = FrameLocation(x, y)

        objects[tempButton.location.asInt] = tempButton
    }

    fun button(location: FrameLocation, builder: Button.() -> Unit){
        val tempButton = Button().apply(builder)
        tempButton.location = location

        objects[tempButton.location.asInt] = tempButton
    }

    /**
     * method of adding SlotObjet
     * x: x location of inventory
     * y: y location of inventory
     * builder: lambda section of initializing SlotObjet class
     */
    fun slot(x: Int, y: Int, builder: Slot.() -> Unit){
        val tempSlot = Slot().apply(builder)
        tempSlot.location = FrameLocation(x, y)

        objects[tempSlot.location.asInt] = tempSlot
    }

    fun slot(location: FrameLocation, builder: Slot.() -> Unit){
        val tempSlot = Slot().apply(builder)
        tempSlot.location = location

        objects[tempSlot.location.asInt] = tempSlot
    }

    /**
     * method of removing component
     * x: x location of inventory
     * y: y location of inventory
     */
    fun remove(x: Int, y: Int){
        val void = Void().apply {
            location = FrameLocation(x, y)
        }

        objects[void.location.asInt] = void
    }

    fun remove(location: FrameLocation){
        val void = Void().apply {
            this.location = location
        }

        objects[void.location.asInt] = void
    }

    /**
     * method of showing screen
     */
    fun render(player: Player){
        reload()
        player.openInventory(inventory)
    }

    /**
     * method of reload your screen
     */
    fun reload(){
        inventory.run {
            objects.keys.forEach {
                val obj = objects[it]!!

                if (obj is Slot) {
                    if (obj.holder != null) setItem(it, obj.holder)
                    else setItem(it, obj.icon)
                } else setItem(it, obj.icon)
            }
        }
    }

    /**
     * method of find by type
     * returns MutableList of all same type objects
     */
    inline fun <reified T : Component> getComponents(): List<T>{
        return objects.values.filterIsInstance<T>()
    }

    fun getComponent(x: Int, y: Int): Component?{
        return objects[FrameLocation(x, y).asInt]
    }

    fun getComponent(location: Int): Component?{
        return objects[location]
    }

    /**
     * method of find by tag
     * returns MutableList of all same tag objects
     */
    @JvmName("getComponentsByTag")
    fun getComponents(tag: String): List<Component>{
        val tempList:MutableList<Component> = mutableListOf()
        objects.values.forEach {
            if(it.tag == tag) tempList.add(it)
        }

        return tempList
    }

    /**
     * method of find by type and tag
     * returns not only same tag but also same type object at first
     */
    inline fun <reified T : Component> getComponents(tag: String): List<T>{
        val tempList:MutableList<T> = mutableListOf()

        objects.values.filterIsInstance<T>().onEach {
            if(it.tag == tag) tempList.add(it)
        }

        return tempList
    }

    /**
     * Handler of Screen
     */
    @EventHandler
    fun onInvClick(event: InventoryClickEvent){
        if(event.clickedInventory == inventory){
            event.isCancelled = true
            val slot = event.slot
            log(slot)

            if(event.whoClicked is Player){
                val player = event.whoClicked as Player

                if(objects.containsKey(slot)){
                    when(val obj = objects[slot]!!){
                        is Button -> {
                            obj.action(player)
                        }

                        is Slot -> {
                            if(obj.holder == null){
                                if(player.itemOnCursor.type != Material.AIR) {
                                    if(obj.singleMode){
                                        player.itemOnCursor.clone().let {
                                            it.amount -= 1
                                            player.setItemOnCursor(it)

                                            val sub = it.clone()
                                            sub.amount = 1
                                            obj.holder = sub
                                        }
                                    }
                                    else{
                                        obj.holder = player.itemOnCursor
                                        player.setItemOnCursor(null)
                                    }

                                    obj.enableAction(player)
                                }
                            }
                            else if(player.itemOnCursor.type == Material.AIR){
                                player.setItemOnCursor(obj.holder!!)
                                obj.holder = null

                                obj.disableAction(player)
                            }

                            reload()
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    fun onInvClose(event: InventoryCloseEvent){
        if(event.inventory == inventory){
            val player = event.player

            if(player is Player){
                val slots = getComponents<Slot>()
                slots.forEach {
                    if(!it.save && it.holder != null){
                        if(player.inventory.firstEmpty() != -1){
                            player.inventory.addItem(it.holder)
                            it.holder = null
                        }else{
                            player.sendMessage("${ChatColor.RED}인벤토리가 가득 찼습니다. 슬롯의 아이템을 임시보관합니다.")
                            player.sendMessage("${ChatColor.RED}슬롯의 아이템은 서버 종료 시 삭제됩니다.")
                            return
                        }
                    }
                }
            }
        }
    }
}