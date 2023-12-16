package api.ludicrous245

class FrameLocation(var x: Int, var y: Int) {

    val asInt: Int
        get() = (x) + ((y) * 9)

    fun add(additional: Int): FrameLocation {
        x += additional
        y += additional

        return this
    }

    fun add(additionalX: Int, additionalY: Int): FrameLocation {
        x += additionalX
        y += additionalY

        return this
    }

    fun multiply(multiple: Int): FrameLocation {
        x *= multiple
        y *= multiple

        return this
    }

    fun multiply(multipleX: Int, multipleY: Int): FrameLocation {
        x *= multipleX
        y *= multipleY

        return this
    }

    fun clone(): FrameLocation {
        return FrameLocation(x, y)
    }
}