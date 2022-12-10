import kotlin.math.min

class Node(val dirname: String) {
    var previous: Node? = null
    var total: Long = 0
    var children = mutableListOf<Node>()
    var childrenTotal: Long = 0
    var values = mutableMapOf<String, Long>()
}

fun main() {
    val input = Utils.getResourceFile("day7.txt")!!

    fun generateNodes(): List<Node> {
        var rootNode = Node("root")
        var currentNode = rootNode
        var nodeMap = mutableMapOf<String, Node>()
        val cdCmd = Regex("\\$ cd (?<dirName>(/|.)+)")
        val dirLine = Regex("dir (?<dirName>.+)")
        val fileLine = Regex("(?<fileSize>\\d+) (?<fileName>.+)")

        // build tree
        input.useLines {
            it.forEach { line ->
                var folderName = ""
                var folderNode = currentNode
                while (folderNode.previous != null) {
                    folderName = "${folderNode.dirname}-${folderName}"
                    folderNode = folderNode.previous!!
                }
                when {
                    cdCmd.matches(line) -> {
                        val dirName = cdCmd.matchEntire(line)?.groups?.get("dirName")?.value!!
                        when (dirName) {
                            ".." -> currentNode = currentNode.previous!!
                            else -> {
                                if (nodeMap["${folderName}-${dirName}"] == null) {
                                    var parentNde = currentNode
                                    currentNode = Node(dirName)
                                    currentNode.previous = parentNde
                                    parentNde.children.add(currentNode)
                                    nodeMap["${folderName}-${dirName}"] = currentNode
                                } else {
                                    currentNode = nodeMap["${folderName}-${dirName}"]!!
                                }
                            }
                        }
                    }

                    dirLine.matches(line) -> {
                        val dirName = dirLine.matchEntire(line)?.groups?.get("dirName")?.value!!
                        if (nodeMap["${folderName}-${dirName}"] == null) {
                            var tmpNode = Node(dirName)
                            tmpNode.previous = currentNode
                            currentNode.children.add(tmpNode)
                            nodeMap["${folderName}-${dirName}"] = tmpNode
                        }
                    }

                    fileLine.matches(line) -> {
                        val fileSize = fileLine.matchEntire(line)?.groups?.get("fileSize")?.value!!
                        val fileName = fileLine.matchEntire(line)?.groups?.get("fileName")?.value!!
                        currentNode.total += fileSize.toLong()
                        currentNode.values.put(fileName, fileSize.toLong())
                    }
                }
            }
        }
        var tmpNodes = ArrayDeque(rootNode.children)
        var nodes = ArrayDeque<Node>()
        // calculate all folder size
        while (true) {
            var tmpNode = tmpNodes.removeFirst()
            nodes.addFirst(tmpNode)
            tmpNode.children.forEach {
                tmpNodes.addLast(it)
            }
            if (tmpNodes.size == 0) {
                break
            }
        }
        return nodes
            .map {
                it.previous?.let { preNode ->
                    preNode.childrenTotal += it.childrenTotal + it.total
                }
                it
            }
    }

    fun part1(): Long {
        var nodes = generateNodes()
        return nodes
            .filter {
                it.childrenTotal + it.total <= 100000
            }.sumOf {
                it.childrenTotal + it.total
            }
    }

    fun part2(): Long {
        var nodes = generateNodes()
        val totalUsed = nodes[nodes.lastIndex].childrenTotal + nodes[nodes.lastIndex].total
        val needSpace = 30000000 - (70000000 - totalUsed)
        var needDeletedSpace: Long = 70000000
        nodes.forEach {
            if ((it.childrenTotal + it.total) >= needSpace) {
                needDeletedSpace = min(needDeletedSpace, it.childrenTotal + it.total)
            }
        }
        return needDeletedSpace
    }

    println(part1())
    println(part2())
}

