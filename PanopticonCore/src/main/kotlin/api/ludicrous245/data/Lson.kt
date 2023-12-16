package api.ludicrous245.data

import api.ludicrous245.project.global.PublicCompanion.pluginName
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Lson/Native
 * Minecraft Edition
 */
class Lson(val dir: String, val file: File, val raw: JsonObject) {
    companion object{

        /**
         * it often throws NoClassDefFoundError at when you try to override plugin and reload.
         * but there's no functional problem.
         * so, I recommend use try/catch when you try to use this method.
         */
        @Throws(NoClassDefFoundError::class)
        fun find(path: String, initializer: JsonObject, consumer: Lson.() -> Unit): Lson{
            var tempPath = path

            tempPath = if(!path.contains(".json")) "./plugins/${pluginName}/${tempPath}.json"
            else "./plugins/${pluginName}/${tempPath}"

            val fileName = tempPath.split("/").last()
            val pathName = tempPath.replace(fileName, "")

            val tempDir: File = File(pathName)
            val tempFile: File = File(tempPath)

            if(pathName != "./" && !tempDir.exists()) tempDir.mkdirs()
            if(!tempFile.isFile) {
                tempFile.createNewFile()

                val writer: FileWriter = FileWriter(tempFile)

                writer.let {
                    it.write(initializer.toString())
                    it.flush()
                }

                writer.close()
            }

            val reader: FileReader = FileReader(tempPath)
            val obj = JsonParser.parseReader(reader)

            val tempLson = Lson(tempPath, tempFile, obj.asJsonObject)
            tempLson.run(consumer)

            reader.close()

            return tempLson
        }

        fun find(path: String, consumer: Lson.() -> Unit): Lson{
            return find(path, JsonObject(), consumer)
        }

        fun find(path: String): Lson{
            return find(path, JsonObject()){}
        }

        fun find(path: String, initializer: JsonObject): Lson{
            return find(path, initializer){}
        }

        fun JsonElement.attach(consumer: JsonElement.() -> Unit){
            this.apply(consumer)
        }
    }

    fun attach(save: Boolean, consumer: JsonObject.() -> Unit){
        try{
            val used = raw.apply(consumer)

            if(save) detach(used)
        }catch (_: Exception){}
    }

    fun detach(){
        try{
            val writer = FileWriter(file)

            writer.flush()
            writer.close()
        }catch (_: Exception){}
    }

    private fun detach(write: JsonElement){
        try{
            val writer = FileWriter(file)

            writer.let {
                it.write(write.toString())
                it.flush()
            }

            writer.close()
        }catch (_: Exception){}
    }
}