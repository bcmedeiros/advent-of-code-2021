import java.util.*

fun main() {

    fun part1(scanner: Scanner): Int {
        var fish: List<Fish> = scanner.nextLine()
                .split(",")
                .map { Fish(it.toInt()) }

        repeat(80) {
            val newborn = mutableListOf<Fish>()
            fish = fish.map {
                when (it.age) {
                    0 -> Fish(6).also { newborn.add(Fish()) }
                    else -> Fish(it.age - 1)
                }
            } + newborn
        }

        return fish.size
    }

    fun part2(scanner: Scanner): Long {
        val fishByAge: Map<Int, Long> = scanner.nextLine()
                .split(",")
                .map { it.toInt() }
                .groupBy { it }
                .mapValues { it.value.size.toLong() }

        fun solution(days: Int): Map<Int, Long> = when (days) {
            0 -> fishByAge
            else -> {
                val solutionDaysMinus1 = solution(days - 1)
                val aboutToHaveChildrenDaysMinus1 = solutionDaysMinus1[0] ?: 0
                mapOf(
                        0 to (solutionDaysMinus1[1] ?: 0),
                        1 to (solutionDaysMinus1[2] ?: 0),
                        2 to (solutionDaysMinus1[3] ?: 0),
                        3 to (solutionDaysMinus1[4] ?: 0),
                        4 to (solutionDaysMinus1[5] ?: 0),
                        5 to (solutionDaysMinus1[6] ?: 0),
                        6 to (solutionDaysMinus1[7] ?: 0) + aboutToHaveChildrenDaysMinus1,
                        7 to (solutionDaysMinus1[8] ?: 0),
                        8 to aboutToHaveChildrenDaysMinus1,
                )
            }
        }
        return solution(256).values.sumOf { it }
    }

    // adjust based on the day
    val day = "06"
    val part1TestResult = 5934
    val part2TestResult = 26984457539

    val part1 = part1(scanner("day$day-sample"))
    println("part 1: $part1")
    check(part1 == part1TestResult)
    val part2 = part2(scanner("day$day-sample"))
    println("part 2: $part2")
    check(part2 == part2TestResult)

    println(part1(scanner("day$day-input")))
    println(part2(scanner("day$day-input")))
}

@JvmInline
value class Fish(
        val age: Int = 8,
)