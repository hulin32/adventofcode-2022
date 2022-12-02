import java.io.File

fun main() {
    var result = 0
    val competition = mapOf<String,Int>(
        "AX" to 1 + 3,
        "AY" to 2 + 6,
        "AZ" to 3 + 0,
        "BX" to 1 + 0,
        "BY" to 2 + 3,
        "BZ" to 3 + 6,
        "CX" to 1 + 6,
        "CY" to 2 + 0,
        "CZ" to 3 + 3,
    )

    File(Utils.getResourceFile("day2.txt")!!).forEachLine {
        val key = it.replace(" ", "")
        result += competition[key]!!
    }
    println(result)
}

