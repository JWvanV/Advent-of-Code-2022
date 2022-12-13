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
        val stringBuilder = StringBuilder()

        repeat(6) {
            val startIndex = it * 40
            val xValues = xPerCycle.subList(startIndex, startIndex + 40)
            stringBuilder.appendLine(getLine(xValues))
        }

        return stringBuilder.toString().trim()
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

    private fun getLine(xValues: List<Int>): String {
        val lineChars = CharArray(40) { '.' }

        repeat(40) { index ->
            val xValue = xValues[index]
            val pixelIndexes = xValue - 1 until xValue + 2
            if (pixelIndexes.contains(index))
                lineChars[index] = '#'
        }

        return lineChars.toList().joinToString(separator = "")
    }
}