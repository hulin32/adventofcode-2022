import java.io.File

fun main() {
    data class Point(val x: Int, val y: Int)
    data class Step(val point: Point, var pathLength: Int)

    val input = Utils.getResourceFile("day12.txt")!!

    fun parseInput(file: File): Triple<Step, Step, List<List<Char>>> {
        var start = Step(Point(0, 0), 0)
        var end = Step(Point(0, 0), 0)
        val graph = file
            .readLines()
            .mapIndexed { rIdx, line ->
                val columns = line
                    .split("")
                    .filter { it.isNotEmpty() }
                    .map { it.first() }
                if (columns.indexOf('S') >= 0) {
                    start = Step(Point(rIdx, columns.indexOf('S')), 0)
                }
                if (columns.indexOf('E') >= 0) {
                    end = Step(Point(rIdx, columns.indexOf('E')), 0)
                }
                columns.toMutableList()
            }
        graph[start.point.x][start.point.y] = 'a'
        graph[end.point.x][end.point.y] = 'z'
        return Triple(start, end, graph)
    }

    val (start, end, graph) = parseInput(input)
    fun findSmallestPath(isReached: (point: Point) -> Boolean): Int {
        val visited = mutableSetOf(end.point)
        val steps = ArrayDeque(listOf(end))
        while (steps.isNotEmpty()) {
            val step = steps.removeFirst()
            if (isReached(step.point)) {
                    return step.pathLength
            }
            var aroundSteps = listOf(
                Step(Point(step.point.x - 1, step.point.y), step.pathLength + 1),
                Step(Point(step.point.x + 1, step.point.y), step.pathLength + 1),
                Step(Point(step.point.x, step.point.y - 1), step.pathLength + 1),
                Step(Point(step.point.x, step.point.y + 1), step.pathLength + 1),
            )
            aroundSteps
                .filter {
                    graph.getOrNull(it.point.x)?.getOrNull(it.point.y) != null
                }
                .filter { it.point !in visited }
                .filter {
                    graph[step.point.x][step.point.y].code <= 1 + graph[it.point.x][it.point.y].code
                }
                .forEach {
                    steps.addLast(it)
                    visited.add(it.point)
                }
        }
        return 0
    }

    fun part1(): Int {
        return findSmallestPath() {
            it == start.point
        }
    }

    fun part2(): Int {
        return findSmallestPath() {
            graph[it.x][it.y] == 'a'
        }
    }

    println(part1())
    println(part2())
}

