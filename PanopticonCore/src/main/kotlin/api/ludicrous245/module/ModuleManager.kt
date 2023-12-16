package api.ludicrous245.module

import api.ludicrous245.project.global.PublicCompanion.log

class ModuleManager{
    private val modules: MutableMap<String, Module> = mutableMapOf()

    fun use(module: Module){
        module.enable()

        if(modules.containsKey(module.moduleName)) throw Exception("Already using module ${module.moduleName}")
        modules[module.moduleName] = module

        log("using Module '${module.moduleName}'")
    }

    fun close(){
        modules.values.forEach {
            it.disable()
        }
    }

    fun find(name: String): Module?{
        return modules[name]
    }
}