fun main() {

    fun readLines(input: List<String>) = input
            .map {
                val split = it.split(" ")
                Command(Direction.valueOf(split[0]), split[1].toInt())
            }

    fun part1(input: List<String>): Int {
        val commands: List<Command> = readLines(input)

        return commands.fold(Position()) { p, c ->
            when (c.d) {
                Direction.forward -> Position(p.horizontalPosition + c.amount, p.depth)
                Direction.down -> Position(p.horizontalPosition, p.depth + c.amount)
                Direction.up -> Position(p.horizontalPosition, p.depth - c.amount)
            }
        }.depthTimesPosition()
    }

    fun part2(input: List<String>): Int {
        val commands: List<Command> = readLines(input)

        return commands.fold(PositionWithAim()) { p, c ->
            when (c.d) {
                Direction.down -> PositionWithAim(p.horizontalPosition, p.depth, p.aim + c.amount)
                Direction.up -> PositionWithAim(p.horizontalPosition, p.depth, p.aim - c.amount)
                Direction.forward -> PositionWithAim(p.horizontalPosition + c.amount, p.depth + (p.aim * c.amount), p.aim)
            }
        }.depthTimesPosition()
    }

    // adjust based on the day
    val day = "02"
    val part1TestResult = 150
    val part2TestResult = 900

    val testInput = readInput("Day${day}_test")
    val part1 = part1(testInput)
    println("part 1: $part1")
    check(part1 == part1TestResult)
    val part2 = part2(testInput)
    println("part 2: $part2")
    check(part2 == part2TestResult)

    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}

data class Position(
        val horizontalPosition: Int = 0,
        val depth: Int = 0,
) {
    fun depthTimesPosition() = horizontalPosition * depth
}

data class PositionWithAim(
        val horizontalPosition: Int = 0,
        val depth: Int = 0,
        val aim: Int = 0,
) {
    fun depthTimesPosition() = horizontalPosition * depth
}

data class Command(
        val d: Direction,
        val amount: Int,
)

enum class Direction {
    forward, down, up
}