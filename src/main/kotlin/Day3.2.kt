import java.io.File
import java.util.*

fun main() {
    var result = 0
    val calculatePriority = { a: Int ->
        when (a) {
            in 97..122 -> a - 96// a-z
            in 65..90 -> a - 64 + 26 // A-Z
            else -> 0
        }
    }
    var lines = mutableListOf<SortedSet<Int>>()
    File(Utils.getResourceFile("day3.txt")!!).forEachLine { line ->
        // format data
        val compartments = line
            .split("")
            .filter { it.isNotEmpty() }
            .map { it.toCharArray().first().code }
            .toSortedSet()

        lines.add(compartments)

        // add to 3 lines
        if (lines.size == 3) {
            // loop first line
            lines[0].forEach {
                // choose smaller or equal value in rest of lines
                lines.takeLast(lines.size - 1).forEach { sortedLine ->
                    while (sortedLine.size > 0) {
                        if (sortedLine.first() < it) {
                            sortedLine.remove(sortedLine.first())
                        } else {
                            break
                        }
                    }
                }

                // get first element in all rest of line
                val firstOfAllLines = lines
                    .takeLast(lines.size - 1)
                    .map { lineItem ->
                        if (lineItem.size > 0) {
                            lineItem.first()
                        } else {
                            -1
                        }
                    }
                    .toSortedSet()

                // add firs line's
                firstOfAllLines.add(it)

                // increase resule if its same
                if (firstOfAllLines.size == 1) {
                    result += calculatePriority(it)
                }
            }
            // reset lines
            lines = mutableListOf()
        }
    }
    println(result)
}

