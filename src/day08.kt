import java.lang.Math.pow
import java.util.*
import kotlin.math.pow

fun main() {

    fun part1(scanner: Scanner): Int {
        var count = 0
        while (scanner.hasNext()) {
            val codes: List<Set<Char>> = buildList {
                repeat(10) {
                    add(scanner.next().toCharArray().toSet())
                }
            }
            scanner.next()
            val digits: List<Set<Char>> = buildList {
                repeat(4) {
                    add(scanner.next().toCharArray().toSet())
                }
            }

            val decode = buildMap<Set<Char>, Short> {
                codes.forEach { c ->
                    when {
                        c.size == 2 -> put(c, 1)
                        c.size == 4 -> put(c, 4)
                        c.size == 3 -> put(c, 7)
                        c.size == 7 -> put(c, 8)
                    }
                }
            }

            digits.forEach { d ->
                if (decode.contains(d)) {
                    count++
                }
            }
        }

        return count
    }

    fun part2(scanner: Scanner): Int {
        var count = 0
        while (scanner.hasNext()) {
            val codes: List<Set<Char>> = buildList {
                repeat(10) {
                    add(scanner.next().toCharArray().toSet())
                }
            }
            scanner.next()
            val digits: List<Set<Char>> = buildList {
                repeat(4) {
                    add(scanner.next().toCharArray().toSet())
                }
            }

            val inverse = mutableMapOf<Short, Set<Char>>()
            val decode = buildMap<Set<Char>, Short> {
                codes.forEach { c ->
                    when {
                        c.size == 2 -> {
                            put(c, 1)
                            inverse[1] = c
                        }
                        c.size == 4 -> {
                            put(c, 4)
                            inverse[4] = c
                        }
                        c.size == 3 -> {
                            put(c, 7)
                            inverse[7] = c
                        }
                        c.size == 7 -> {
                            put(c, 8)
                            inverse[8] = c
                        }
                    }
                }
                val d1 = inverse[7]!! - inverse[1]!!
                val d2Ord4 = inverse[4]!! - inverse[7]!!
                var d5Ord7 = inverse[8]!! - inverse[7]!! - inverse[4]!!

                val codeFor2Or5 = codes.filter { it.size == 5 }.filterNot { (inverse[1]!! - it).isEmpty() }
                if (codeFor2Or5[0].intersect(d2Ord4).size == 2) {
                    put(codeFor2Or5[0], 5)
                    inverse[5] = codeFor2Or5[0]
                    put(codeFor2Or5[1], 2)
                    inverse[2] = codeFor2Or5[1]
                } else {
                    put(codeFor2Or5[1], 5)
                    inverse[5] = codeFor2Or5[1]
                    put(codeFor2Or5[0], 2)
                    inverse[2] = codeFor2Or5[0]
                }

                val d2 = d2Ord4 - inverse[2]!!
                val d4 = d2Ord4 - d2
                val d5 = inverse[2]!! - inverse[5]!! - inverse[4]!!
                val d7 = d5Ord7 - d5

                val codeFor9 = inverse[8]!! - d5
                put(codeFor9, 9)
                inverse[9] = codeFor9

                val codeFor3 = inverse[8]!! - d2 - d5
                put(codeFor3, 3)
                inverse[3] = codeFor3

                val codeFor0 = inverse[8]!! - d4
                put(codeFor0, 0)
                inverse[0] = codeFor0

                val codeFor6 = codes.filter { it.size == 6 }.single { it != codeFor9 && it != codeFor0 }
                put(codeFor6, 6)
                inverse[6] = codeFor6
            }

            digits.forEachIndexed { index, d ->
                count += decode[d]!! * 10.0.pow(
                        when (index) {
                            0 -> 3
                            1 -> 2
                            2 -> 1
                            3 -> 0
                            else -> throw IllegalStateException()
                        }
                ).toInt()
            }
        }

        return count
    }

    // adjust based on the day
    val day = "08"
    val part1TestResult = 26
    val part2TestResult = 61229

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
