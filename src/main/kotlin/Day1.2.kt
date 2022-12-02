import java.io.File
import java.nio.file.Paths
import kotlin.math.max

fun main() {
    var result = mutableSetOf<Int>()
    var current = 0
    File(Utils.getResourceFile("day1.txt")!!).forEachLine {
        if (it.isEmpty()) {
            result.add(current)
            current = 0
        } else {
            current += it.toInt()
        }
    }
    println("result: " + result.sortedDescending().take(3).sum())
}