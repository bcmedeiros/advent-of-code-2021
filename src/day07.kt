import java.util.*
import kotlin.math.abs

fun main() {

    fun part1(scanner: Scanner): Int {
        val numbers: List<Int> = scanner.nextLine().split(",").map { it.toInt() }

        val min = numbers.minOf { it }
        val max = numbers.maxOf { it }

        val s = mutableMapOf<Int, Int>()
        for (p in min..max) {
            s[p] = numbers.sumOf { n -> abs(n - p) }
        }

        return s.values.minOf { it }
    }

    fun part2(scanner: Scanner): Int {
        val numbers: List<Int> = scanner.nextLine().split(",").map { it.toInt() }

        val min = numbers.minOf { it }
        val max = numbers.maxOf { it }

        val s = mutableMapOf<Int, Int>()
        for (p in min..max) {
            s[p] = numbers
                    .sumOf { n -> (0 until abs(n - p)) // summing the cost for each number
                    .mapIndexed { index, _ -> index + 1 }
                    .sumOf { it } } // summing the cost of each
        }

        return s.values.minOf { it }
    }

    // adjust based on the day
    val day = "07"
    val part1TestResult = 37
    val part2TestResult = 168

    val part1 = part1(scanner("day$day-sample"))
    println("part 1: $part1")
    check(part1 == part1TestResult)
    val part2 = part2(scanner("day$day-sample"))
    println("part 2: $part2")
    check(part2 == part2TestResult)

    println(part1(scanner("day$day-input")))
    println(part2(scanner("day$day-input")))
}
