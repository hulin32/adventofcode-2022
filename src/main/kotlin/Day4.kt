import java.util.*

fun main() {
    val input = getResourceFile("day4.txt")!!
    fun part1(): Int {
        return input
            .readLines()
            .filter {
                val(l, r) = it.split(",")
                val(lLeft, lRight) = l.split("-").map { itInner -> itInner.toInt() }
                val(rLeft, rRight) = r.split("-").map { itInner -> itInner.toInt() }
                (lLeft <= rLeft && rRight <= lRight) || (rLeft <= lLeft && lRight <= rRight)
            }
            .size
    }
    fun part2(): Int {
        return input
            .readLines()
            .filter {
                val(l, r) = it.split(",")
                val(lLeft, lRight) = l.split("-").map { itInner -> itInner.toInt() }
                val(rLeft, rRight) = r.split("-").map { itInner -> itInner.toInt() }
                !((lRight < rLeft) || (rRight < lLeft))
            }
            .size
    }
    println(part1())
    println(part2())
}

