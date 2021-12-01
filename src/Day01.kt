fun main() {

    fun part1(input: List<String>): Int {
        val intList = input.map { it.toInt() }
        return intList
                .filterIndexed { index, int -> if (index == 0) false else int > intList[index - 1] }
                .count()
    }

    fun part2(input: List<String>): Int {
        val intList = input.map { it.toInt() }
        var count = 0
        for (i in 0..input.size - 3) {
            if (i > 0) {
                if (intList[i + 2] > intList[i - 1]) count++
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
