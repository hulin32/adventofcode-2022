fun main() {
    var result = mutableSetOf<Int>()
    var current = 0
    Utils.getResourceFile("day1.txt")!!.forEachLine {
        if (it.isEmpty()) {
            result.add(current)
            current = 0
        } else {
            current += it.toInt()
        }
    }
    println("result: " + result.sortedDescending().take(3).sum())
}