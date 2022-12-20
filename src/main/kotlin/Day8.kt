import kotlin.math.max

fun main() {
    val input = getResourceFile("day8.txt")!!
    val graph = input
        .readLines()
        .map {
            it
                .split("")
                .filter { c -> c.isNotEmpty() }
                .map { c -> c.toInt() }
        }

    fun part1(): Int {
        var viewed = mutableMapOf<String, Boolean>()
        // left -> right
        for (rowIdx in graph.indices) {
            viewed["$rowIdx-0"] = true
            var highest = graph[rowIdx][0]
            for (columIdx in graph[0].indices) {
                if (graph[rowIdx][columIdx] > highest) {
                    highest = graph[rowIdx][columIdx]
                    viewed["$rowIdx-$columIdx"] = true
                }
            }
        }
        // right -> left
        for (rowIdx in graph.indices) {
            viewed["$rowIdx-${graph[0].size - 1}"] = true
            var highest = graph[rowIdx][graph[0].size - 1]
            for (columIdx in graph[0].indices.reversed()) {
                if (graph[rowIdx][columIdx] > highest) {
                    highest = graph[rowIdx][columIdx]
                    viewed["$rowIdx-$columIdx"] = true
                }
            }
        }

        // top -> bottom
        for (columIdx in graph[0].indices) {
            viewed["0-$columIdx"] = true
            var highest = graph[0][columIdx]
            for (rowIdx in graph.indices) {
                if (graph[rowIdx][columIdx] > highest) {
                    highest = graph[rowIdx][columIdx]
                    viewed["$rowIdx-$columIdx"] = true
                }
            }
        }

        // bottom -> top
        for (columIdx in graph[0].indices) {
            viewed["${graph.size - 1}-$columIdx"] = true
            var highest = graph[graph.size - 1][columIdx]
            for (rowIdx in graph.indices.reversed()) {
                if (graph[rowIdx][columIdx] > highest) {
                    highest = graph[rowIdx][columIdx]
                    viewed["$rowIdx-$columIdx"] = true
                }
            }
        }
        return viewed.size
    }

    fun leftToRight(rIdx: Int, cIdx: Int): Int {
        var highest = graph[rIdx][cIdx]
        var stepTo = cIdx
        for (columIdx in cIdx + 1 until graph[0].size) {
            if (graph[rIdx][columIdx] >= highest) {
                stepTo = columIdx
                break
            }
        }
        if (stepTo == cIdx) {
           return graph[0].size - cIdx - 1
        }
        return stepTo - cIdx
    }

    fun rightToLeft(rIdx: Int, cIdx: Int): Int {
        var highest = graph[rIdx][cIdx]
        var stepTo = cIdx
        for (columIdx in cIdx - 1 downTo 0) {
            if (graph[rIdx][columIdx] >= highest) {
                stepTo = columIdx
                break
            }
        }
        if (stepTo == cIdx) {
            return cIdx
        }
        return cIdx - stepTo
    }

    fun topToBottom(rIdx: Int, cIdx: Int): Int {
        var highest = graph[rIdx][cIdx]
        var stepTo = rIdx
        for (rowIdx in rIdx + 1 until graph.size) {
            if (graph[rowIdx][cIdx] >= highest) {
                stepTo = rowIdx
                break
            }
        }
        if (stepTo == rIdx) {
            return graph.size - rIdx  - 1
        }
        return stepTo - rIdx
    }

    fun bottomToTop(rIdx: Int, cIdx: Int): Int {
        var highest = graph[rIdx][cIdx]
        var stepTo = rIdx
        for (rowIdx in rIdx - 1 downTo 0) {
            if (graph[rowIdx][cIdx] >= highest) {
                stepTo = rowIdx
                break
            }
        }
        if (stepTo == rIdx) {
            return rIdx
        }
        return rIdx - stepTo
    }

    fun part2(): Int {
        var result = 0
        graph.forEachIndexed { rIdx, it ->
            it.forEachIndexed { cIdx, _  ->
                result = max(result, leftToRight(rIdx, cIdx) * rightToLeft(rIdx, cIdx) * topToBottom(rIdx, cIdx) * bottomToTop(rIdx, cIdx))
            }
        }
        return result
    }

//    println(part1())
    println(part2())
}

