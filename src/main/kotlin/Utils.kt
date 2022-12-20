import java.io.File

data class Point(var x: Int, var y: Int) {
    operator fun plus(p: Point): Point {
        return Point(x + p.x, y + p.y)
    }
}

fun <T> List<List<T>>.get(p: Point): T {
    return this[p.x][p.y]
}

fun getResourceFile(path: String): File? = {}.javaClass.getResource(path)?.path?.let { File(it) }
fun String.allInts() = allIntsInString(this)
fun allIntsInString(line: String): List<Int> {
    return """-?\d+""".toRegex().findAll(line)
        .map { it.value.toInt() }
        .toList()
}

fun <T> T.debug(): T {
    if (this is Iterable<*>) {
        this.forEach { println(it) }
    } else {
        println(this)
    }
    return this
}

fun IntRange.contains(b: IntRange): Boolean {
    return this.first <= b.first && b.last <= this.last
}

fun IntRange.overlaps(b: IntRange): Boolean {
    return this.contains(b.first) || this.contains(b.last) || b.contains(this.first)
}