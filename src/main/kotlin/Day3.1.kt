fun main() {
    var result = 0
    var left: MutableSet<Int>
    var right: MutableSet<Int>
    val calculatePriority = { a: Int ->
        when (a) {
            in 97..122 -> a - 96// a-z
            in 65..90 -> a - 64 + 26 // A-Z
            else -> 0
        }
    }
    getResourceFile("day3.txt")!!.forEachLine { line ->
        val compartments = line
            .split("")
            .filter { it.isNotEmpty() }
            .map { it.toCharArray().first().code }
        val halfLength = compartments.size / 2
        left = compartments.take(halfLength).toMutableSet()
        right = compartments.takeLast(halfLength).toMutableSet()

        left.forEach { num ->
            if (right.contains(num)) {
                result += calculatePriority(num)
            }
        }
    }
    println(result)
}

