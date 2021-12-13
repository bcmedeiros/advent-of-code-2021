import java.util.*

fun main() {

    fun readMatrix(scanner: Scanner): Array<Array<Boolean>> {
        val matrix = Array(10000) { Array(10000) { false } }

        while (scanner.hasNextLine()) {
            val nextLine = scanner.nextLine()
            if (nextLine.isBlank()) break
            nextLine.split(",").let { p ->
                matrix[p[1].toInt()][p[0].toInt()] = true
            }
        }
        return matrix
    }

    fun part1(scanner: Scanner): Long {
        val matrix = readMatrix(scanner)

        while (scanner.hasNextLine()) {
            val fold = scanner.nextLine().removePrefix("fold along ").split("=").let { p -> p[0] to p[1].toInt() }

            when (fold.first) {
                "y" -> for (f in 1..fold.second) {
                    for (x in 0 until 10000) {
                        val yl = fold.second - f
                        val yh = fold.second + f
                        matrix[yl][x] = matrix[yl][x] || matrix[yh][x]
                        matrix[yh][x] = false
                    }
                }
                "x" -> for (f in 1..fold.second) {
                    for (y in 0 until 10000) {
                        val xl = fold.second - f
                        val xh = fold.second + f
                        matrix[y][xl] = matrix[y][xl] || matrix[y][xh]
                        matrix[y][xh] = false
                    }
                }
            }
            break
        }

        return matrix.sumOf { r -> r.count { it }.toLong() }
    }

    fun part2(scanner: Scanner): Long {
        val matrix = readMatrix(scanner)

        while (scanner.hasNextLine()) {
            val fold = scanner.nextLine().removePrefix("fold along ").split("=").let { p -> p[0] to p[1].toInt() }

            when (fold.first) {
                "y" -> for (d in 1..fold.second) {
                    for (x in 0 until 10000) {
                        val yl = fold.second - d
                        val yh = fold.second + d
                        matrix[yl][x] = matrix[yl][x] || matrix[yh][x]
                        matrix[yh][x] = false
                    }
                }
                "x" -> for (d in 1..fold.second) {
                    for (y in 0 until 10000) {
                        val xl = fold.second - d
                        val xh = fold.second + d
                        matrix[y][xl] = matrix[y][xl] || matrix[y][xh]
                        matrix[y][xh] = false
                    }
                }
            }
        }

        for (y in 0 until 8) {
            for (x in 0 until 50) {
                print(if (matrix[y][x]) "#" else " ")
            }
            println()
        }

        return 0
    }

    // adjust based on the day
    val day = "13"
    val part1TestResult = 17L
    val part2TestResult = 0L

    val sample = "day$day-sample"
    val part1 = part1(scanner(sample))
    println("part 1: $part1")
    check(part1 == part1TestResult)
//    val part2 = part2(scanner(sample))
//    println("part 2: $part2")
//    check(part2 == part2TestResult)

    val input = "day$day-input"
    println(part1(scanner(input)))
    println(part2(scanner(input)))
}
