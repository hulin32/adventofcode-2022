import kotlin.math.max
import kotlin.math.min

data class Point(var x: Int, var y: Int) {
    operator fun plus(p: Point): Point {
        return Point(x + p.x, y + p.y)
    }
}

fun <T> List<List<T>>.get(p: Point): T {
    return this[p.x][p.y]
}

fun main() {
    val input = Utils.getResourceFile("day14.txt")!!
    val grid = MutableList(1000) { MutableList(1000) { true } }
    val rocks = input
        .readLines()
        .map {
            it.split(" -> ")
                .map {
                    val (x, y) = it.split(",").map { it.toInt() }

                    Point(x, y)
                }
        }
    fun setRockLines(grid: MutableList<MutableList<Boolean>>) {
        rocks.forEach {
            it.zipWithNext { a, b ->
                val xRange = min(a.x, b.x)..max(a.x, b.x)
                val yRange = min(a.y, b.y)..max(a.y, b.y)
                for (x in xRange) {
                    for (y in yRange) {
                        grid[x][y] = false
                    }
                }
            }
        }
    }

    val down = Point(0, 1)
    val downLeft = Point(-1, 1)
    val downRight = Point(1, 1)

    fun part1(): Int {
        val grid = MutableList(1000) { MutableList(1000) { true } }
        setRockLines(grid)
        val maxY = rocks.flatMap { it }.maxOf { it.y }
        var sandCount = 0
        while (true) {
            // Simulate sand falling.
            var pos = Point(500, 0)
            while (true) {
                if (grid[pos.x][pos.y + 1]) {
                    pos += down
                } else if (grid[pos.x - 1][pos.y + 1]) {
                    pos += downLeft
                } else if (grid[pos.x + 1][pos.y + 1]) {
                    pos += downRight
                } else {
                    // Sand can't move
                    grid[pos.x][pos.y] = false
                    break
                }
                // Check if the sand is falling below the last rocks.
                if (pos.y > maxY) {
                    return sandCount
                }
            }
            sandCount++
        }
        return sandCount
    }

    fun part2(): Int {
        val grid = MutableList(1000) { MutableList(1000) { true } }
        setRockLines(grid)

        // Draw the floor
        val floor = rocks.flatMap { it }.maxOf { it.y } + 2
        grid[0].indices.forEach { x -> grid[x][floor] = false }

        var sandCount = 0
        while (true) {
            sandCount++
            // Simulate sand falling.
            var pos = Point(500, 0)
            while (true) {
                if (grid[pos.x][pos.y + 1]) {
                    pos += down
                } else if (grid[pos.x - 1][pos.y + 1]) {
                    pos += downLeft
                } else if (grid[pos.x + 1][pos.y + 1]) {
                    pos += downRight
                } else {
                    // Sand can't move
                    grid[pos.x][pos.y] = false

                    // Sand plugs the source
                    if (pos.y <= 0) {
                        return sandCount
                    }

                    break
                }
            }
        }
        return sandCount
    }

    println(part1())
    println(part2())
}

