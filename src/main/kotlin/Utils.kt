import java.io.File

class Utils {
    companion object {
        fun getResourceFile(path: String): File? = {}.javaClass.getResource(path)?.path?.let { File(it) }
    }
}