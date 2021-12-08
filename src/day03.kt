fun main() {

    fun readLines(input: List<String>) = input

    fun part1(input: List<String>): Int {
        val data: List<String> = readLines(input)

        val countOfOnes = data[0].map { 0 }.toMutableList()

        data.forEach { s -> s.forEachIndexed { index, c -> if (c == '1') countOfOnes[index]++ } }

        val gamma = countOfOnes.map { if (it >= data.size / 2.0) '1' else '0' }.joinToString("")
        val epsilon = countOfOnes.map { if (it < data.size / 2.0) '1' else '0' }.joinToString("")
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: List<String>): Int {
        val data: List<String> = readLines(input)

        val firstInput = data[0]

        var oxygen = data
        for (p in firstInput.indices) {
            val countOfOnes = oxygen.count { s -> s[p] == '1' }
            val bit1IsMoreFrequent = countOfOnes >= oxygen.size / 2.0
            oxygen = oxygen.filter { s -> s[p] == if (bit1IsMoreFrequent) '1' else '0' }
            if (oxygen.size == 1) break
        }
        var co2 = data
        for (p in firstInput.indices) {
            val countOfOnes = co2.count { s -> s[p] == '1' }
            val bit0IsMoreFrequent = countOfOnes < co2.size / 2.0
            co2 = co2.filter { s -> s[p] == if (bit0IsMoreFrequent) '1' else '0' }
            if (co2.size == 1) break
        }

        return oxygen.single().toInt(2) * co2.single().toInt(2)
    }

    // adjust based on the day
    val day = "03"
    val part1TestResult = 198
    val part2TestResult = 230

    val testInput = readInput("day$day-sample")
    val part1 = part1(testInput)
    println("part 1: $part1")
    check(part1 == part1TestResult)
    val part2 = part2(testInput)
    println("part 2: $part2")
    check(part2 == part2TestResult)

    val input = readInput("day$day-input")
    println(part1(input))
    println(part2(input))
}
