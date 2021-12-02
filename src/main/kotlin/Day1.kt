import kotlin.reflect.KClass

fun main() {
    println("Part 1: ${Day1.part1()} depth increases")
    println("Part 2: ${Day1.part2()} sliding window depth increases")
}

class Day1 {
    companion object {
        fun part1() = Day1::class.input().useLines { lines ->
            lines.map { it.toLong() }
                .countIncreases()
        }

        fun part2() = Day1::class.input().useLines { lines ->
            lines.map { it.toLong() }
                .window(3)
                .map { it.sum() }
                .countIncreases()
        }
    }
}

private fun Sequence<Long>.window(size: Int) = runningFold(emptyList<Long>()) { list, next ->
    list.takeLast(size - 1) + next
}.dropWhile { it.size < size }

private fun Sequence<Long>.countIncreases(): Long {
    val (count, _last) = fold(Pair(0L, Long.MAX_VALUE)) { (count, previous), next ->
        Pair(
            if (next > previous) count + 1
            else count, next
        )
    }
    return count
}

private fun <T : Any> KClass<T>.input() =
    this.java.getResource("${this.simpleName}Input.txt")!!.openStream().bufferedReader()

