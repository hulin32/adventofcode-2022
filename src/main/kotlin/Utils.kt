class Utils {
    companion object {
        fun getResourceFile(path: String): String? =
            object {}.javaClass.getResource(path)?.path
    }
}