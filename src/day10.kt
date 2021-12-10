import java.nio.channels.IllegalSelectorException
import java.util.*

fun main() {

    val open = setOf('(', '[', '{', '<')
    val closeMap = mapOf(
            '(' to ')',
            '[' to ']',
            '{' to '}',
            '<' to '>',
    )

    fun part1(scanner: Scanner): Int {
        val data: List<String> = readLines(scanner)

        val points = mapOf(
                ')' to 3,
                ']' to 57,
                '}' to 1197,
                '>' to 25137,
        )

        val linePoints = data.map { line ->
            val stack = Stack<Char>()
            line.forEach { c ->
                if (open.contains(c)) {
                    stack.push(c)
                } else {
                    val expectedPair = stack.pop()
                    if (closeMap[expectedPair] != c) {
//                        println("Expected ${closeMap[expectedPair]}, but found $c instead.")
                        return@map points[c]
                    }
                }
            }
            0
        }

        return linePoints.sumOf { it!! }
    }

    fun part2(scanner: Scanner): Long {
        val data: List<String> = readLines(scanner)

        val points = mapOf(
                ')' to 1,
                ']' to 2,
                '}' to 3,
                '>' to 4,
        )

        val incomplete = data.filter { line ->
            val stack = Stack<Char>()/**/
            line.forEach { c ->
                if (open.contains(c)) {
                    stack.push(c)
                } else {
                    val expectedPair = stack.pop()
                    if (closeMap[expectedPair] != c) {
                        return@filter false
                    }
                }
            }
            return@filter true
        }

        val linePoints = incomplete.map { line ->
            val stack = Stack<Char>()
            line.forEach { c ->
                if (open.contains(c)) {
                    stack.push(c)
                } else {
                    val expectedPair = stack.pop()
                    if (closeMap[expectedPair] != c) {
                        throw IllegalStateException("not allowed")
                    }
                }
            }
            var total = 0L
            while (stack.isNotEmpty()) {
                val pop = stack.pop()
                val closing = closeMap[pop]
                total = total * 5 + points[closing]!!
            }
            total
        }

        return linePoints.sorted()[linePoints.size / 2]
    }

    // adjust based on the day
    val day = "10"
    val part1TestResult = 26397
    val part2TestResult = 288957L

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
