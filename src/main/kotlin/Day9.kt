import kotlin.math.abs

fun main() {
    val input = Utils.getResourceFile("day9.txt")!!

    fun moveTail(h: Pair<Int, Int>, t: Pair<Int, Int>): Pair<Int, Int> {
        var result: Pair<Int, Int> = t
        if (h.first == t.first) {
            if (h.second - t.second == 2) {
                result = Pair(t.first, t.second + 1)
            } else if (t.second - h.second == 2) {
                result = Pair(t.first, t.second - 1)
            }
        } else if (h.second == t.second) {
            if (h.first - t.first == 2) {
                result = Pair(t.first + 1, t.second)
            } else if (t.first - h.first == 2) {
                result = Pair(t.first - 1, t.second)
            }
        } else if (intArrayOf(3, 4).contains(abs(t.second - h.second) + abs(t.first - h.first))) {
            result = Pair(
                if (t.first - h.first > 0) t.first - 1 else t.first + 1,
                if (t.second - h.second > 0) t.second - 1 else t.second + 1,
            )
        }
        return result
    }

    fun part1(): Int {
        val result = mutableMapOf<Pair<Int, Int>, Boolean>()
        var tails = mutableListOf<Pair<Int, Int>>()
        repeat(10) {
            tails.add(Pair(0, 0))
        }
        var head = Pair(0, 0)
        var tail = Pair(0, 0)
        result[tail] = true
        input.forEachLine { line ->
            var pairOfLine = line
                .split(" ")
                .filter { it.isNotBlank() }
                .zipWithNext()
                .single()

            val direction = pairOfLine.first
            var step2 = pairOfLine.second.toInt()
            when (direction) {
                "L" ->
                    while (step2 != 0) {
                        head = Pair(head.first - 1, head.second)
                        moveTail(head, tail)?.let { t ->
                            tail = t
                            result[t] = true
                        }
                        step2--
                    }

                "R" ->
                    while (step2 != 0) {
                        head = Pair(head.first + 1, head.second)
                        moveTail(head, tail)?.let { t ->
                            tail = t
                            result[t] = true
                        }
                        step2--
                    }

                "U" ->
                    while (step2 != 0) {
                        head = Pair(head.first, head.second + 1)
                        moveTail(head, tail)?.let { t ->
                            tail = t
                            result[t] = true
                        }

                        step2--
                    }

                "D" ->
                    while (step2 != 0) {
                        head = Pair(head.first, head.second - 1)
                        moveTail(head, tail)?.let { t ->
                            tail = t
                            result[t] = true
                        }

                        step2--
                    }
            }
        }

        return result.size
    }

    fun part2(): Int {
        val result = mutableMapOf<Pair<Int, Int>, Boolean>()
        var knots = mutableListOf<Pair<Int, Int>>()
        repeat(10) {
            knots.add(Pair(0, 0))
        }
        result[Pair(0, 0)] = true
        input.forEachLine { line ->
            var pairOfLine = line
                .split(" ")
                .filter { it.isNotBlank() }
                .zipWithNext()
                .single()

            val direction = pairOfLine.first
            var step2 = pairOfLine.second.toInt()
            when (direction) {
                "L" ->
                    repeat(step2) {
                        knots[0] = Pair(knots[0].first - 1, knots[0].second)
                        repeat(9) { idx ->
                            var t = moveTail(knots[idx], knots[idx + 1])
                            knots[idx + 1] = t
                            if (idx == 8) {
                                result[t] = true
                            }
                        }
                    }

                "R" ->
                    repeat(step2) {
                        knots[0] = Pair(knots[0].first + 1, knots[0].second)
                        repeat(9) { idx ->
                            var t = moveTail(knots[idx], knots[idx + 1])
                            knots[idx + 1] = t
                            if (idx == 8) {
                                result[t] = true
                            }
                        }
                    }

                "U" ->
                    repeat(step2) { outIdx ->
                        knots[0] = Pair(knots[0].first, knots[0].second + 1)
                        repeat(9) { idx ->
                            var t = moveTail(knots[idx], knots[idx + 1])
                            knots[idx + 1] = t
                            if (idx == 8) {
                                result[t] = true
                            }
                        }
                    }

                "D" ->
                    repeat(step2) {
                        knots[0] = Pair(knots[0].first, knots[0].second - 1)
                        repeat(9) { idx ->
                            var t = moveTail(knots[idx], knots[idx + 1])
                            knots[idx + 1] = t
                            if (idx == 8) {
                                result[t] = true
                            }
                        }
                    }
            }
        }

        return result.size
    }
//    println(part1())
    println(part2())
}

