enum class SEQUENCE { FRONT, BACK }

fun main() {
    val input = getResourceFile("day5.txt")!!
    fun filterInput(num: Int): MutableList<ArrayDeque<Char>> {
        val stack = MutableList(num) { ArrayDeque<Char>() }
        input.useLines {
            // current stack the highest height
            it
                .take(8)
                .forEach { line ->
                    line
                        .replace(Regex("(\\[|\\])"), " ")
                        .chunked(4)
                        .map { chunk ->
                            chunk.firstOrNull { str -> !str.isWhitespace() }
                        }
                        .forEachIndexed { index, c ->
                            if (c != null) {
                                stack[index].addLast(c)
                            }
                        }
                }
        }
        return stack
    }

    fun getMoveInfo(line: String): Triple<Int, Int, Int> {
        val group = """\w+ (?<num1>\d+) \w+ (?<num2>\d+) \w+ (?<num3>\d+)"""
            .toRegex()
            .matchEntire(line)!!
            .groups
        var moveSteps = group["num1"]?.value?.toInt()!!
        val startStackIdx = group["num2"]?.value?.toInt()!! - 1
        val endStackIdx = group["num3"]?.value?.toInt()!! - 1
        return Triple(moveSteps, startStackIdx, endStackIdx)
    }

    fun getStack(num: Int, seq: SEQUENCE): String {
        val stack = filterInput(num)
        input.useLines { lines ->
            val it = lines.toList()
            it
                .takeLast(it.size - 10)
                .forEach { line ->
                    var (moveSteps, startStackIdx, endStackIdx) = getMoveInfo(line)

                    var newStack = ArrayDeque<Char>()
                    while (moveSteps != 0 && !stack[startStackIdx].isEmpty()) {
                        val value = stack[startStackIdx].removeFirst()
                        if (seq == SEQUENCE.BACK) {
                            newStack.addLast(value)
                        } else {
                            newStack.addFirst(value)
                        }
                        moveSteps--
                    }
                    newStack.addAll(stack[endStackIdx])
                    stack[endStackIdx] = newStack
                }
        }
        return stack
            .map { it.first() }
            .joinToString("")
    }

    fun part1(): String {
        return getStack(9, SEQUENCE.FRONT)
    }

    fun part2(): String {
        return getStack(9, SEQUENCE.BACK)
    }
    println(part1())
    println(part2())
}

