package day6

import common.model.Input
import common.model.Puzzle
import kotlin.math.max

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle<List<String>, List<String>>(6, Input.ASSIGNMENT) {

    override fun parse1(lines: List<String>) = lines

    override fun compute1(data: List<String>) = getEndIndexesOfFirstNonRepeatingCharWindow(data, 4).first()

    override fun parse2(lines: List<String>) = lines

    override fun compute2(data: List<String>) = getEndIndexesOfFirstNonRepeatingCharWindow(data, 14).first()

    private fun getEndIndexesOfFirstNonRepeatingCharWindow(data: List<String>, windowSize: Int): List<Int> {
        val smallerWindowSize = windowSize - 1

        return data.map { line ->
            val chars = line.toCharArray().toList()

            for (charIndex in smallerWindowSize until chars.size) {
                val charWindow = chars.subList(max(charIndex - smallerWindowSize, 0), charIndex + 1)
                if (charWindow.size == charWindow.toSet().size) {
                    return@map charIndex + 1
                }
            }

            error("Chars kept repeating")
        }
    }
}