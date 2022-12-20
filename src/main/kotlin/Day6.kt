fun main() {
    val input = getResourceFile("day6.txt")!!

    fun findDistinctCharacters(num: Int): Int {
        val text = input.readText()
        var first = 1
        var tail = 0
        while (first != tail) {
            if ((first - tail) == num) {
                return first
            }
            val substr = text.substring(tail, first)
            val newChar = text[first]
            val foundIdx = substr.indexOf(newChar)
            if (foundIdx >= 0) {
                tail += foundIdx + 1
            } else {
                first += 1
            }
            if (tail == first && tail.toLong() != (input.length() - 1)) {
                first += 1
            }
        }
        return first
    }

    fun part1(): Int {
        return findDistinctCharacters(4)
    }

    fun part2(): Int {
        return findDistinctCharacters(14)
    }
    println(part1())
    println(part2())
}

