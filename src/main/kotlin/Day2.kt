import java.lang.IllegalArgumentException

fun main() {
    println("Part 1: ${Day2.part1()}")
    println("Part 2: ${Day2.part2()}")
}

class Day2 {
    companion object {
        fun part1() = Day2::class.useInput { lines ->
            lines.map { it.toMovement() }
                .fold(0 to 0) { (horizontal, depth), movement ->
                    when (movement) {
                        is Movement.Down -> horizontal to depth + movement.steps
                        is Movement.Forward -> horizontal + movement.steps to depth
                        is Movement.Up -> horizontal to depth - movement.steps
                    }
                }
                .let { (horizontal, depth) -> horizontal * depth }
        }

        fun part2() = Day2::class.useInput { lines ->
            lines.map(String::toMovement)
                .fold(Submarine(0, 0, 0), Submarine::move)
                .let { submarine -> submarine.horizontal * submarine.depth }
        }
    }
}

private data class Submarine(val depth: Int, val horizontal: Int, val aim: Int) {
    fun move(movement: Movement): Submarine =
        when (movement) {
            is Movement.Down -> copy(aim = aim + movement.steps)
            is Movement.Forward -> copy(horizontal = horizontal + movement.steps, depth = depth + aim * movement.steps)
            is Movement.Up -> copy(aim = aim - movement.steps)
        }
}

private sealed class Movement(val steps: Int) {
    class Forward(steps: Int) : Movement(steps)
    class Up(steps: Int) : Movement(steps)
    class Down(steps: Int) : Movement(steps)
}

private fun String.toMovement(): Movement {
    val split = split(" ")
    if (split.size != 2) throw IllegalArgumentException("'$this' is not a valid movement")
    val steps = split[1].toInt()
    return when (split[0]) {
        "forward" -> Movement.Forward(steps)
        "down" -> Movement.Down(steps)
        "up" -> Movement.Up(steps)
        else -> throw IllegalArgumentException("'$this' is not a valid movement")
    }
}
