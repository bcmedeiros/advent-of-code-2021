import java.util.*

fun main() {

    fun part1(scanner: Scanner): Long {
        val map = Array(12) { Array(12) { 0 } }
        repeat(10) {
            scanner.nextLine().forEachIndexed { index, c -> map[it + 1][index + 1] = c.digitToInt() }
        }

        var totalFlashes = 0L
        repeat(100) {
            // all plus 1
            for (r in 1..10)
                for (c in 1..10) {
                    map[r][c] = map[r][c] + 1
                }

            // keep doing while anyone is flashing
            val thisStepFlashes = mutableSetOf<Pair<Int, Int>>()
            do {
                val start = thisStepFlashes.toSet()
                for (r in 1..10)
                    for (c in 1..10) {
                        if (map[r][c] >= 10 && !thisStepFlashes.contains(r to c)) {
                            totalFlashes++
                            thisStepFlashes += r to c
                            for (incR in -1..1)
                                for (incC in -1.. 1)
                                    when {
                                        incR == 0 && incC == 0 -> continue
                                        else -> map[r + incR][c + incC] = map[r + incR][c + incC] + 1
                                    }
                        }
                    }
            } while (start != thisStepFlashes)

            // reset flashed to zero
            for (r in 1..10)
                for (c in 1..10) {
                    if (map[r][c] >= 10) {
                        map[r][c] = 0
                    }
                }
        }

        return totalFlashes
    }


    fun part2(scanner: Scanner): Long {
        val map = Array(12) { Array(12) { 0 } }
        repeat(10) {
            scanner.nextLine().forEachIndexed { index, c -> map[it + 1][index + 1] = c.digitToInt() }
        }

        repeat(100000) { step ->
            // all plus 1
            for (r in 1..10)
                for (c in 1..10) {
                    map[r][c] = map[r][c] + 1
                }

            // keep doing while anyone is flashing
            val thisStepFlashes = mutableSetOf<Pair<Int, Int>>()
            do {
                val start = thisStepFlashes.toSet()
                for (r in 1..10)
                    for (c in 1..10) {
                        if (map[r][c] >= 10 && !thisStepFlashes.contains(r to c)) {
                            thisStepFlashes += r to c
                            for (incR in -1..1)
                                for (incC in -1.. 1)
                                    when {
                                        incR == 0 && incC == 0 -> continue
                                        else -> map[r + incR][c + incC] = map[r + incR][c + incC] + 1
                                    }
                        }
                    }
            } while (start != thisStepFlashes)

            // reset flashed to zero
            var thisStepFlashes2 = 0
            for (r in 1..10)
                for (c in 1..10) {
                    if (map[r][c] >= 10) {
                        map[r][c] = 0
                        thisStepFlashes2++
                    }
                }
            if (thisStepFlashes2 == 100) {
                return step + 1L
            }
        }

        return -1
    }

    // adjust based on the day
    val day = "11"
    val part1TestResult = 1656L
    val part2TestResult = 195L

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
