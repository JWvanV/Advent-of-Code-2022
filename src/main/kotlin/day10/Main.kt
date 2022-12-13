package day10

import common.model.Input
import common.model.Puzzle
import common.util.log
import day10.model.Instruction

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle<List<Instruction>, List<Instruction>>(10, Input.ASSIGNMENT) {

    override fun parse1(lines: List<String>) = getInstructions(lines)

    override fun compute1(data: List<Instruction>): Any {
        val xPerCycle = getXValues(data)
        log(xPerCycle)

        return listOf(20, 60, 100, 140, 180, 220)
            .sumOf { it * xPerCycle[it - 1] }
    }

    override fun parse2(lines: List<String>) = getInstructions(lines)

    override fun compute2(data: List<Instruction>): Any {
        val xPerCycle = getXValues(data)

        val displayLines = mutableListOf<CharArray>()

        repeat(6) {
            val startIndex = it * 40
            val xValues = xPerCycle.subList(startIndex, startIndex + 40)
            displayLines.add(getLine(xValues))
        }

        return readDisplayLines(displayLines)
    }

    private fun getInstructions(lines: List<String>) = lines.map {
        if (it[0] == 'n') {
            Instruction.NoOp
        } else {
            Instruction.AddX(it.split(" ").last().toInt())
        }
    }

    private fun getXValues(instructions: List<Instruction>): List<Int> {
        val xPerCycle = mutableListOf(1)

        instructions.forEach {
            xPerCycle.add(xPerCycle.last())
            when (it) {
                is Instruction.AddX -> {
                    xPerCycle.add(xPerCycle.last() + it.dx)
                }

                else -> {
                    /*NoOp*/
                }
            }
        }

        return xPerCycle
    }

    private fun getLine(xValues: List<Int>): CharArray {
        val lineChars = CharArray(40) { '.' }

        repeat(40) { index ->
            val xValue = xValues[index]
            val pixelIndexes = xValue - 1 until xValue + 2
            if (pixelIndexes.contains(index))
                lineChars[index] = '#'
        }

        return lineChars
    }

    private fun readDisplayLines(displayLines: List<CharArray>): String {
        var tempDisplayLines = displayLines
        var displayText = ""

        var currentIndex = 0
        repeat(40) {
            if (tempDisplayLines.all { it[currentIndex] == '.' }) {
                val charLines = mutableListOf<CharArray>()
                tempDisplayLines = tempDisplayLines.map {
                    charLines.add(it.take(currentIndex).toCharArray())
                    it.drop(currentIndex + 1).toCharArray()
                }
                currentIndex = 0
                displayText += readCharacter(charLines)
            } else {
                currentIndex++
            }
        }

        return displayText
    }

    private fun readCharacter(characterLines: List<CharArray>): Char {
        val characterDisplay = buildString {
            characterLines.forEach {
                appendLine(it.concatToString())
            }
        }.trim()

        return when (characterDisplay) {
            """
                ####
                #...
                ###.
                #...
                #...
                ####
            """.trimIndent(),
            -> 'E'

            """
                ..##
                ...#
                ...#
                ...#
                #..#
                .##.
            """.trimIndent(),
            -> 'J'

            """
                .##.
                #..#
                #...
                #...
                #..#
                .##.
            """.trimIndent(),
            -> 'C'

            """
                ####
                #...
                ###.
                #...
                #...
                #...
            """.trimIndent(),
            -> 'F'

            """
                ###.
                #..#
                #..#
                ###.
                #...
                #...
            """.trimIndent(),
            -> 'P'

            """
                .##.
                #..#
                #...
                #.##
                #..#
                .###
            """.trimIndent(),
            -> 'G'

            """
                #...
                #...
                #...
                #...
                #...
                ####
            """.trimIndent(),
            -> 'L'

            """
               #..#
               #..#
               ####
               #..#
               #..#
               #..#
           """.trimIndent(),
            -> 'H'

            else -> '?'
        }
    }
}