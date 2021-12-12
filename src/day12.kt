import java.util.*

fun main() {

    fun edges(scanner: Scanner): List<Pair<String, String>> {
        val oneWay = readLines(scanner)
                .map { it.split("-") }
        return oneWay.map { it[0] to it[1] } + oneWay.map { it[1] to it[0] }
    }

    fun part1(scanner: Scanner): Long {
        val edges = edges(scanner)

        fun numberOfPathsFrom(n: String, path: List<String> = emptyList()): Long {
            return when (n) {
                "end" -> 1
                else -> edges.filter { it.first == n }
                        .filter { it.second == it.second.uppercase() || (!path.contains(it.second) && it.second != "start") }
                        .sumOf {
                            val newPath = path + it.second
                            numberOfPathsFrom(it.second, newPath)
                        }
            }
        }

        return numberOfPathsFrom("start")
    }

    fun part2(scanner: Scanner): Long {
        val edges = edges(scanner)

        fun pathsFrom(n: String, possibleDouble: String, path: List<String> = emptyList()): List<List<String>> {
            return when (n) {
                "end" -> listOf(path)
                else -> edges.filter { it.first == n }
                        .filter {
                            if (it.second == possibleDouble) {
                                path.count { e -> e == it.second } < 2
                            } else {
                                it.second == it.second.uppercase() || (!path.contains(it.second) && it.second != "start")
                            }
                        }
                        .map {
                            val newPath = path + it.second
                            pathsFrom(it.second, possibleDouble, newPath)
                        }
                        .fold(listOf()) { a, b -> a + b }
            }
        }

        // a set of lowercase nodes that can be visited twice
        val possibleDouble = edges.map { it.first }
                .filter { it.lowercase() == it && it != "start" && it != "end" }
                .distinct()

        // for each that can be doubled, calculate all possible paths
        val paths = possibleDouble
                .flatMap { pathsFrom("start", it) }
                .toSet()

        return paths.count().toLong()
    }

    // adjust based on the day
    val day = "12"
    val part1TestResult = 10L
    val part2TestResult = 36L

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
