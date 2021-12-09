import java.util.*

fun main() {

    data class Point(
            val row: Int,
            val col: Int,
    )

    fun readMap(scanner: Scanner): List<List<Int>> {
        val map = buildList {
            var first = true
            lateinit var limit: List<Int>
            while (scanner.hasNextLine()) {
                val nextLine = scanner.nextLine()
                if (first) {
                    first = false
                    limit = buildList<Int> {
                        repeat(nextLine.length + 2) {
                            add(10)
                        }
                    }
                    add(limit)
                }
                add(listOf(10) + nextLine.toCharArray().map { it.toString().toInt() } + listOf(10))

            }
            add(limit)
        }
        return map
    }

    fun part1(scanner: Scanner): Int {
        val map = readMap(scanner)

        var riskLevel = 0
        for (r in 1..map.size - 2) {
            for (c in 1..map[0].size - 2) {
                val v = map[r][c]
                if (v < map[r - 1][c] && v < map[r][c - 1] && v < map[r + 1][c] && v < map[r][c + 1]) {
                    riskLevel += v + 1
                }
            }
        }

        return riskLevel
    }

    fun part2(scanner: Scanner): Int {
        val map = readMap(scanner)

        var start = listOf<Point>()
        for (r in 1..map.size - 2) {
            for (c in 1..map[0].size - 2) {
                val v = map[r][c]
                if (v < map[r - 1][c] && v < map[r][c - 1] && v < map[r + 1][c] && v < map[r][c + 1]) {
                    start += Point(r, c)
                }
            }
        }

        val basins = start.associateWith { s ->
            val visited = mutableListOf<Point>()
            val queue = LinkedList<Point>()
            queue.add(s)
            while (queue.isNotEmpty()) {
                val point = queue.removeFirst()!!
                if (visited.contains(point)) continue
                visited.add(point)
                for (possibleRoute in setOf(
                        Point(point.row - 1, point.col),
                        Point(point.row, point.col - 1),
                        Point(point.row + 1, point.col),
                        Point(point.row, point.col + 1),
                )) {
                    if (map[possibleRoute.row][possibleRoute.col] < 9) {
                        queue.add(possibleRoute)
                    }
                }
            }
            visited
        }

        return basins.values
                .sortedByDescending { it.size }
                .map { it.size }
                .take(3)
                .fold(1) { a, b -> a * b }
    }

    // adjust based on the day
    val day = "09"
    val part1TestResult = 15
    val part2TestResult = 1134

    val sample = "day$day-sample"
    val part1 = part1(scanner(sample))
    println("part 1: $part1")
    check(part1 == part1TestResult)
    val part2 = part2(scanner(sample))
    println("part 2: $part2")
    check(part2 == part2TestResult)

    val input = "day$day-input"
    println(part1(scanner(input)))
    println(part2(scanner(input)))
}
