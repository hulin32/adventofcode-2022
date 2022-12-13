fun main() {
    val input = Utils.getResourceFile("day10.txt")!!

    fun part1(): Int {
        var result = 1
        var signalResult = 0
        var step = 0
        var signalStrength = 20

        fun checkSignalStrength() {
            step++
            if (step == signalStrength) {
                signalResult += signalStrength * result
                signalStrength += 40
            }
        }
        input.forEachLine { line ->
            when {
                line.startsWith("addx") -> {
                    repeat(2) {
                        checkSignalStrength()
                    }
                    val v = line.split(' ').last().toInt()
                    result += v
                }
                else -> {
                    checkSignalStrength()
                }
            }
        }
        return signalResult
    }

    fun part2(): Int {
        return 1
    }
    println(part1())
//    println(part2())
}

