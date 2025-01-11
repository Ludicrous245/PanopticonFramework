package api.ludicrous245.contents.data

import org.bukkit.Bukkit

class Argset {
    internal val requires: MutableList<String> = mutableListOf()

    constructor()

    constructor(builder: Argset.() -> Unit){
       apply(builder)
    }

    constructor(list: List<String>){
        requires.addAll(list)
    }

    companion object {
        fun fromPlayers(): Argset{
            val players = Bukkit.getOnlinePlayers().map { it.name }
            return Argset(players)
        }
    }

    fun add(argName: String){
        requires.add(argName)
    }
}