import java.lang.IllegalStateException

fun main() {

    fun readLines(input: List<String>) = input

    fun readBoards(data: List<String>): List<Board> {
        val n = (data.size - 1) / 6

        val boards = (0 until n).map { b ->
            val boardString = data[b * 6 + 2] + " " + data[b * 6 + 3] + " " + data[b * 6 + 4] + " " + data[b * 6 + 5] + " " + data[b * 6 + 6]
            val boardInput = boardString.replace("  ", " ").trim().split(" ")
            Board(boardInput.map { it.toInt() })
        }
        return boards
    }

    fun part1(input: List<String>): Int {
        val data: List<String> = readLines(input)

        val draw = data[0].split(",").map { it.toInt() }

        var boards = readBoards(data)

        draw.forEach { d ->
            boards = boards.map { b -> Board(b.numbers, b.marked + d) }
            boards.singleOrNull { b -> b.won }?.let { b -> return b.score(d) }
        }

        throw IllegalStateException("no winner")
    }

    fun part2(input: List<String>): Int {
        val data: List<String> = readLines(input)

        val draw = data[0].split(",").map { it.toInt() }

        var boards = readBoards(data)

        draw.forEach { d ->
            boards = boards.map { b -> Board(b.numbers, b.marked + d) }
            if (boards.size > 1) {
                boards = boards.filterNot { b -> b.won }
            }
            boards.singleOrNull { b -> b.won }?.let { b -> return b.score(d) }
        }

        throw IllegalStateException("no winner")
    }

    // adjust based on the day
    val day = "04"
    val part1TestResult = 4512
    val part2TestResult = 1924

    val testInput = readInput("Day${day}_test")
    val part1 = part1(testInput)
    println("part 1: $part1")
    check(part1 == part1TestResult)
    val part2 = part2(testInput)
    println("part 2: $part2")
    check(part2 == part2TestResult)

    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}

data class Board(
        val numbers: List<Int>,
        val marked: Set<Int> = emptySet(),
) {
    val won: Boolean
        get() = (0..4).any { r -> (0..4).all { c -> marked.contains(numbers[r * 5 + c]) } } ||
                (0..4).any { c -> (0..4).all { r -> marked.contains(numbers[r * 5 + c]) } }

    fun score(d: Int): Int = numbers.filterNot { marked.contains(it) }.sum() * d
}
