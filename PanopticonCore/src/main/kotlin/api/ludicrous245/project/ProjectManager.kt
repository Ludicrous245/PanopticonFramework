package api.ludicrous245.project

class ProjectManager {
    val subProjects: MutableMap<String, SubProject> = mutableMapOf()

    fun add(projectName: String, module: SubProject){
        subProjects[projectName] = module
    }

    fun find(projectName: String): SubProject?{
        return subProjects[projectName]
    }

    fun load(){
        subProjects.values.forEach{
            it.onLoad()
        }
    }

    fun unload(){
        subProjects.values.forEach{
            it.onUnload()
        }
    }
}