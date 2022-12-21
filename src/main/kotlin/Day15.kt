import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


data class Sensor(val sensorLoc: Point, val beaconLoc: Point) {
    fun range(): Int {
        return abs(sensorLoc.x - beaconLoc.x) + abs(sensorLoc.y - beaconLoc.y)
    }
}

// ref: https://github.com/NoMoor/aoc2022/blob/main/src/aoc2022/Day15.kt
fun main() {
    val input = getResourceFile("day15.txt")!!
    val sensors = input
        .readLines()
        .map { it.removePrefix("Sensor at ").allInts() }
        .map { Sensor(Point(it[0], it[1]), Point(it[2], it[3])) }
        .toList()

    fun coveredRanges(targetY: Int): List<IntRange> {
        val notHereRanges = sensors
            .filter { s ->
                abs(targetY - s.sensorLoc.y) <= s.range()
            }
            .map { s ->
                val dx = s.range() - abs(targetY - s.sensorLoc.y)
                -dx + s.sensorLoc.x..dx + s.sensorLoc.x
            }
        return notHereRanges
    }

    fun findOpenSpot(y: Int): Long {
        val coveredRanges = coveredRanges(y).sortedBy { it.first }.toList()
        coveredRanges
            .reduce { a, b ->
                if (a.overlaps(b) || a.last + 1 == b.first) {
                    return@reduce min(a.first, b.first)..max(a.last, b.last)
                }
                return a.last + 1.toLong()
            }
        return -1
    }

    fun part1(): Long {
        val targetY = 2000000
        val coveredRanges = coveredRanges(targetY)

        val invalidLocations = coveredRanges.flatten().distinct()
        val beacons = sensors.map { it.beaconLoc }.filter { it.y == targetY }.distinct()

        return invalidLocations.size - beacons.size.toLong()
    }

    fun part2(): Long {
        val dimension = 4_000_000
        for (y in 0..dimension) {
            val x = findOpenSpot(y)
            if (x != -1L) {
                return x * 4_000_000 + y
            }
        }
        throw RuntimeException("Solution not found")
    }

//    println(part1())
    println(part2())
}

