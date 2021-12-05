import java.lang.IllegalStateException
import java.util.*

fun main() {

    fun readBoards(scanner: Scanner) = buildList {
        while (scanner.hasNextInt()) {
            this.add(Board((1..25).map { scanner.nextInt() }))
        }
    }

    fun part1(scanner: Scanner): Int {
        val draw = scanner.nextLine().split(",").map { it.toInt() }

        var boards = readBoards(scanner)

        draw.forEach { d ->
            boards = boards.map { b -> Board(b.numbers, b.marked + d) }
            boards.singleOrNull { b -> b.won }?.let { b -> return b.score(d) }
        }

        throw IllegalStateException("no winner")
    }

    fun part2(scanner: Scanner): Int {
        val draw = scanner.nextLine().split(",").map { it.toInt() }

        var boards = readBoards(scanner)

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

    val part1 = part1(scanner("Day${day}_test"))
    println("part 1: $part1")
    check(part1 == part1TestResult)
    val part2 = part2(scanner("Day${day}_test"))
    println("part 2: $part2")
    check(part2 == part2TestResult)

    println(part1(scanner("Day$day")))
    println(part2(scanner("Day$day")))
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
