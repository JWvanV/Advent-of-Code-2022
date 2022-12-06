package day6

import common.Input
import common.Puzzle
import kotlin.math.max

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle(6, Input.ASSIGNMENT) {
    override fun part1(): Any {
        return getEndIndexesOfFirstNonRepeatingCharWindow(4).first()
    }

    override fun part2(): Any {
        return getEndIndexesOfFirstNonRepeatingCharWindow(14).first()
    }

    private fun getEndIndexesOfFirstNonRepeatingCharWindow(windowSize: Int): List<Int> {
        val smallerWindowSize = windowSize - 1

        return puzzleInput.mapEach { line ->
            val chars = line.toCharArray().toList()

            for (charIndex in smallerWindowSize until chars.size) {
                val charWindow = chars.subList(max(charIndex - smallerWindowSize, 0), charIndex + 1)
                if (charWindow.size == charWindow.toSet().size) {
                    return@mapEach charIndex + 1
                }
            }

            error("Chars kept repeating")
        }
    }
}