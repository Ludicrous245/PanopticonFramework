package api.ludicrous245.data

val Collection<String>.complete: String
    get() = this.complete()

val Collection<String>.completeWithComma: String
    get() = this.completeWithComma

val <T : Any> Collection<T>.duplicate: MutableList<T>
    get() = this.duplicate()

val <T : Any> Collection<T>.repack: MutableList<T>
    get() = this.duplicate()

private fun <T : Any> Collection<T>.duplicate(): MutableList<T>{
    val tempList: MutableList<T> = mutableListOf()
    tempList.addAll(this)

    return tempList
}

private fun Collection<String>.complete():String {
    var syntax = ""

    for(arg in this){
        syntax = if(syntax == ""){
            arg
        }else {
            "$syntax $arg"
        }
    }

    return syntax
}

private fun Collection<String>.completeWithComma():String {
    var syntax = ""

    for(arg in this){
        syntax = if(syntax == ""){
            arg
        }else {
            "$syntax, $arg"
        }
    }

    return syntax
}