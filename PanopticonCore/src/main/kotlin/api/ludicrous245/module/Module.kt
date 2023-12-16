package api.ludicrous245.module

open class Module(val moduleName: String) {
    var isEnabled = false

    internal fun enable(){
        isEnabled = true
        onLoad()
    }

    internal fun disable(){
        isEnabled = false
        onUnload()
    }

    open fun onLoad(){

    }

    open fun onUnload(){

    }

    /**
     * print method which shows messages with project name
     */
    fun log(message: Any){
        println("[$moduleName] $message")
    }
}