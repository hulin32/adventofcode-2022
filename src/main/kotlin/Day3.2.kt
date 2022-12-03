import java.io.File
import java.util.SortedSet

fun main() {
    var result = 0
    var left: SortedSet<Int>
    var right: SortedSet<Int>
    val calculatePriority = { a: Int ->
        when (a) {
            in 97..122 -> a - 96// a-z
            in 65..90 -> a - 64 + 26 // A-Z
            else -> 0
        }
    }
    var lines = mutableListOf<SortedSet<Int>>()
    File(Utils.getResourceFile("day3.txt")!!).forEachLine { line ->
        val compartments = line
            .split("")
            .filter { it.isNotEmpty() }
            .map { it.toCharArray().first().code }
            .toSortedSet()
        lines.add(compartments)
        if (lines.size === 3) {
            lines[0].forEach {
                var line1It = 0
                var line2It = 0
                while (lines[1].size > 0) {
                    line1It = lines[1].first()
                    if (line1It < it) {
                        lines[1].remove(line1It)
                    } else {
                        break
                    }
                }
                while (lines[2].size > 0) {
                    line2It = lines[2].first()
                    if (line2It < it) {
                        lines[2].remove(line2It)
                    } else {
                        break
                    }
                }
                if ((it === line2It) && (line1It === line2It)) {
                    result += calculatePriority(it)
                }
            }
            lines = mutableListOf()
        }
    }
    println(result)
}

