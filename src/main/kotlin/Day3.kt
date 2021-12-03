import java.lang.IllegalArgumentException
import kotlin.streams.asSequence

fun main() {
    println("Part 1: Power consumption  : ${Day3.gamma() * Day3.epsilon()}")
    println("Part 2: Life support rating: ${Day3.oxygen() * Day3.coScrubber()}")
}

class Day3 {
    companion object {
        fun gamma() = Day3::class.useInput { lines ->
            val result = Result()
            lines.forEach(result::add)
            result.value
        }

        fun epsilon() = gamma().inv() and 0b1111_1111_1111

        fun oxygen() = filteredValue(false)

        fun coScrubber() = filteredValue(true)

        private fun filteredValue(invert: Boolean): Int {
            var lines = Day3::class.useInput { lines -> lines.map(Line::fromString).toList() }

            for (bit in 0..11) {
                val keep = (lines.count { it.bitSet(bit) } * 2 >= lines.size) xor invert
                lines = lines.filter { line -> line.bitSet(bit) == keep }
                if (lines.size <= 1) break
            }

            return lines.single().value
        }
    }
}

class Line(val bits: List<Boolean>) {
    val value: Int
        get() {
            var result = 0
            bits.forEach { bit ->
                result = (result shl 1) + if (bit) 1 else 0
            }
            return result
        }

    companion object {
        fun fromString(line: String): Line = Line(line.chars()
            .asSequence()
            .map { it.toChar() == '1' }
            .toList()
        )
    }

    fun bitSet(bit: Int) = bits[bit]
}

class Bit(private var setCount: Int, private var unsetCount: Int) {
    val value
        get() = if (setCount > unsetCount) 1 else 0

    operator fun plusAssign(char: Char) {
        when (char) {
            '0' -> unsetCount++
            '1' -> setCount++
            else -> throw IllegalArgumentException()
        }
    }
}

class Result(
    private val bits: Array<Bit> = Array(12) { Bit(0, 0) }
) {
    fun add(line: String): Result {
        line.chars().asSequence().forEachIndexed { index, char ->
            bits[index] += char.toChar()
        }
        return this
    }

    val value: Int
        get() {
            var result = 0
            bits.forEach { bit ->
                result = (result shl 1) + bit.value
            }
            return result
        }
}

