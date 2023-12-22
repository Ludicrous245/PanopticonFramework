package api.ludicrous245.project.task

import api.ludicrous245.project.global.PublicCompanion.log
import api.ludicrous245.project.global.PublicCompanion.plugin
import org.bukkit.scheduler.BukkitRunnable

class Task(internal val name: String) {
    companion object{
        private val availableTask: MutableMap<String, Task> = mutableMapOf()

        fun createTask(name: String, consumer: Task.() -> Unit): Task {
            return createTask(name, 1, true, consumer)
        }

        fun createTask(name: String, period: Int, consumer: Task.() -> Unit): Task {
            return createTask(name, period, true, consumer)
        }

        fun createTask(name: String, autoStart: Boolean, consumer: Task.() -> Unit): Task {
            return createTask(name, 1, autoStart, consumer)
        }

        fun createTask(name: String, period: Int, autoStart: Boolean, consumer: Task.() -> Unit): Task {
            val task = Task(name).apply(consumer).apply { this.period = period }
            if(autoStart) task.start()

            availableTask[name] = task
            return availableTask[name]!!
        }
    }

    private val taskCallback: MutableMap<String, (Long) -> Unit> = mutableMapOf()
    private var period: Int = 1

    var terminated: Boolean = false

    private val runnable: BukkitRunnable = object: BukkitRunnable(){
        var uptime: Long = 0
        override fun run() {
            uptime++

            if(terminated) {
                availableTask.remove(name)
                log("Task $name is terminated")
                this.cancel()
            }

            if(taskCallback.isNotEmpty()){
                taskCallback.values.onEach {
                    it(uptime)
                }
            }
        }
    }

    fun start(){
        log("Task $name is started")
        runnable.runTaskTimer(plugin, 0L, period.toLong())
    }

    fun task(taskName: String, callback: (uptime: Long) -> Unit){
        taskCallback[taskName] = callback
    }

    fun remove(methodName: String) : Boolean{
        if(taskCallback.containsKey(methodName)){
            taskCallback.remove(methodName)
            return true
        }
        return false
    }
}