import java.io.File
import kotlin.math.max

fun main() {
    var result = 0
    var current = 0
    File(Utils.getResourceFile("day1.txt")!!).forEachLine {
        if (it.isEmpty()) {
            result = max(result, current)
            current = 0
        } else {
            current += it.toInt()
        }
    }
    println("result: $result")
}