package api.ludicrous245.contents

import api.ludicrous245.contents.data.Argset
import api.ludicrous245.contents.data.SelectionInfo
import api.ludicrous245.data.duplicate
import api.ludicrous245.module.Command.Companion.register
import org.bukkit.command.CommandSender

class CommandSelection(val commandName: String, val location: Int) {
    /**
     * variable about command arguments.
     * you can directly access to arguments with it.
     *
     * recommend to use getArg(i: Int) method instead.
     */
    lateinit var args: MutableList<String>

    /**
     * CommandSender variable
     */
    lateinit var sender: CommandSender

    private val actions: MutableList<() -> Unit> = mutableListOf()
    private val selections: MutableMap<String, SelectionInfo> = mutableMapOf()

    var argset: Argset = Argset()

    /**
     * Add Action when command execute
     *
     * action: lambda block of callback which call on execute
     */
    fun executes(action: () -> Unit){
        this.actions.add(action)
    }

    /**
     * Conditional Sentence in CommandSelection.
     * can get next command from argument.
     *
     * label: the name of next command.
     * consumer: lambda block for build command continuously
     */
    fun case(label: String, consumer: CommandSelection.() -> Unit): CommandSelection{
        val _location = location+1

        val selection = CommandSelection(label, _location).apply(consumer)
        selections[label] = SelectionInfo(label, _location, selection)

        return selection
    }

    /**
     * return argument witch exists after parameter i: Int, Based on the current block.
     * if not, will return null
     *
     * i: range of apartment from current block
     */
    fun getArg(i: Int): String?{
        try {
            val arg: String by lazy { args[location + (i + 1)] }
            return arg
        }catch (_:Exception){
            return null
        }
    }

    /**
     * request Argument by force
     * basically, arguments are registered when call 'case' method
     * nevertheless, use it when argument requires without 'case'
     * or need online player names (Argset.Players)
     */
    fun reqArg(set: Argset){
        argset = set
    }

    fun reqArg(builder: Argset.() -> Unit){
        argset = Argset(builder)
    }

    fun reqArg(arg: String){
        argset.add(arg)
    }

    /**
     * runs command
     */
    private fun emit(argRaw: List<String>, i: Int){
        val __args = args.duplicate
        val __sender = sender

        if(argRaw.isEmpty()){
            if(actions.isNotEmpty()) actions.onEach { it() }
            return
        }

        val selectionInfo = selections[argRaw[i]]

        if(selectionInfo != null){
            val nextInt = i + 1
            val newSelection = selectionInfo.selection.apply {
                this.sender = __sender
                this.args = __args
            }

            if(nextInt < argRaw.size) newSelection.emit(argRaw, nextInt)
            else if(newSelection.actions.isNotEmpty()) newSelection.actions.onEach { it() }
        }

        else if(actions.isNotEmpty()) actions.onEach { it() }
    }

    /**
     * register tabComplete
     */
    private fun tab(args: List<String>): MutableList<String>{
        val complete: MutableList<String> = mutableListOf()

        if((args.size-1) == 0) return complete.apply {
            addAll(selections.keys)
            if(argset.requires.isNotEmpty()) addAll(argset.requires)
        } else {
            val nextSel = selections[args[0]]
            if(nextSel != null) tab(nextSel.selection, args,1, complete)
        }

        return complete
    }

    private fun tab(selection: CommandSelection, args: List<String>, j: Int, target: MutableList<String>){
        if((args.size-1) == j) {
            target.addAll(selection.selections.keys)
            if(selection.argset.requires.isNotEmpty()) target.addAll(selection.argset.requires)
        } else {
            try {
                val arg = args[j]
                val nextSelection = selection.selections[arg]
                nextSelection?.selection?.tab(nextSelection.selection, args, j+1, target)
            }catch (_: Exception){}
        }
    }

    /**
     * transfer to ProxyCommand.
     * also, you can register command with this method.
     * command will reload when this method called.
     */
    internal fun build(): ProxyCommand{
        val proxy = object: ProxyCommand(commandName) {
            override fun execute(commandSender: CommandSender, label: String, rawArg: Array<out String>): Boolean {
                sender = commandSender
                args = rawArg.toMutableList()

                emit(args, 0)
                return true
            }

            override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
                return tab(args.toList())
            }
        }

        register(proxy)

        return proxy
    }
}