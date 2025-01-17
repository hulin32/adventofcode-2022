fun main() {
    val input = getResourceFile("day10.txt")!!

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

    // ref https://github.com/pavlo-dh/aoc-2022-in-kotlin/blob/main/src/Day10.kt
    fun part2(): String {
        var cycle = 0
        var x = 1
        var spritePosition = 0..2
        val screen = Array(6) { CharArray(40) { '.' } }

        fun draw() {
            val row = cycle / 40
            val column = cycle - row * 40
            if (column in spritePosition) {
                screen[row][column] = '#'
            }
        }

        fun noop() {
            draw()
            cycle++
        }

        fun add(line: String) {
            repeat(2) {
                noop()
            }
            val v = line.split(' ').last().toInt()
            x += v
            spritePosition = x - 1..x + 1
        }

        fun printScreen() = buildString {
            for (i in screen.indices) {
                for (j in screen.first().indices) {
                    append(screen[i][j])
                }
                if (i != screen.lastIndex) appendLine()
            }
        }

        input.forEachLine { line ->
            if (line.startsWith('n')) noop()
            else add(line)
        }

        return printScreen()
    }
    println(part1())
    println(part2())
}

