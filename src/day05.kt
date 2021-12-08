import java.lang.Integer.max
import java.lang.Integer.min

fun main() {

    fun readLines(input: List<String>) = input
            .map { l ->
                val pairs = l.split(" -> ")
                val p1 = pairs[0].split(",")
                val p2 = pairs[1].split(",")
                Line(Point(p1[0].toInt(), p1[1].toInt()), Point(p2[0].toInt(), p2[1].toInt()))
            }

    fun part1(input: List<String>): Int {
        val listOfPoints: List<Line> = readLines(input)

        val count = mutableMapOf<Point, Int>()

        listOfPoints.forEach { l ->
            if (l.p1.x == l.p2.x) for (y in min(l.p1.y, l.p2.y)..max(l.p1.y, l.p2.y)) {
                val point = Point(l.p1.x, y)
                count[point] = (count[point] ?: 0) + 1
            } else if (l.p1.y == l.p2.y) for (x in min(l.p1.x, l.p2.x)..max(l.p1.x, l.p2.x)) {
                val point = Point(x, l.p1.y)
                count[point] = (count[point] ?: 0) + 1
            }
        }

        return count.filter { it.value > 1 }.count()
    }

    fun part2(input: List<String>): Int {
        val listOfPoints: List<Line> = readLines(input)

        val count = mutableMapOf<Point, Int>()

        listOfPoints.forEach { l ->
            if (l.p1.x == l.p2.x) for (y in min(l.p1.y, l.p2.y)..max(l.p1.y, l.p2.y)) {
                val point = Point(l.p1.x, y)
                count[point] = (count[point] ?: 0) + 1
            } else if (l.p1.y == l.p2.y) for (x in min(l.p1.x, l.p2.x)..max(l.p1.x, l.p2.x)) {
                val point = Point(x, l.p1.y)
                count[point] = (count[point] ?: 0) + 1
            } else {
                val size = max(l.p1.x, l.p2.x) - min(l.p1.x, l.p2.x)
                val inverted = l.p1.x - l.p2.x != l.p1.y - l.p2.y
                for (s in 0..size) {
                    val point =
                            if (!inverted) Point(min(l.p1.x, l.p2.x) + s, min(l.p1.y, l.p2.y) + s)
                            else Point(max(l.p1.x, l.p2.x) - s, min(l.p1.y, l.p2.y) + s)
                    count[point] = (count[point] ?: 0) + 1
                }
            }
        }

        return count.filter { it.value > 1 }.count()
    }

    val day = "05"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day$day-sample")
    val part1 = part1(testInput)
    println("part 1: $part1")
    check(part1 == 5)
    val part2 = part2(testInput)
    println("part 2: $part2")
    check(part2 == 12)

    val input = readInput("day$day-input")
    println(part1(input))
    println(part2(input))
}

data class Point(
        val x: Int,
        val y: Int,
)

data class Line(
        val p1: Point,
        val p2: Point,
)