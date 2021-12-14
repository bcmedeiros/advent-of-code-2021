import java.util.*

fun main() {

    fun part1(scanner: Scanner): Long {
        val template = scanner.nextLine()

        scanner.nextLine()

        val replacements = readLines(scanner)
            .map { it.split(" -> ").let { it[0] to it[1] } }

        var polymer = template
        repeat(10) {
            val inserts = mutableListOf<Pair<Int, String>>()
            polymer.windowed(2).forEachIndexed { index, s ->
                for (r in replacements) {
                    if (s == r.first) {
                        if (polymer.contains(r.first)) {
                            inserts.add(0, index + 1 to r.second)
                        }
                    }
                }
            }
            val chars = LinkedList(polymer.toList())
            for (i in inserts) {
                chars.add(i.first, i.second[0])
            }
            polymer = chars.joinToString("")
        }

        val counts = polymer.toCharArray().groupBy { it }.mapValues { it.value.count() }
        return counts.maxOf { it.value }.toLong() - counts.minOf { it.value }.toLong()
    }

    fun part2(scanner: Scanner): Long {
        val template = scanner.nextLine()

        scanner.nextLine()

        val replacements = readLines(scanner)
            .map { it.split(" -> ") }
            .associate { it[0] to it[1] }

        var pairCounts = (template + "1")
            .windowed(2)
            .groupBy { it }
            .mapValues { it.value.count().toLong() }

        repeat(40) {
            pairCounts = pairCounts
                .flatMap { (letterPair, pairCount) ->
                    val extra = replacements[letterPair]
                    if (extra != null) {
                        listOf(
                            letterPair[0] + extra to pairCount,
                            extra + letterPair[1] to pairCount
                        )
                    } else {
                        listOf(
                            letterPair to pairCount
                        )
                    }
                }
                .groupBy { it.first }
                .mapValues { e -> e.value.sumOf { it.second } }
        }

        val letterCounts = mutableMapOf<Char, Long>()
        pairCounts
            .map { it.key[0] to it.value }
            .forEach {
                letterCounts[it.first] = (letterCounts[it.first] ?: 0) + it.second
            }

        return letterCounts.maxOf { it.value }.toLong() - letterCounts.minOf { it.value }.toLong()
    }

    // adjust based on the day
    val day = "14"
    val part1TestResult = 1588L
    val part2TestResult = 2188189693529L

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
