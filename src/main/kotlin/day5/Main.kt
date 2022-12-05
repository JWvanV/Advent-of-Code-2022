package day5

import common.Input
import common.Puzzle

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle(5, Input.ASSIGNMENT) {
    override fun part1(): Any {
        val (crateStacks, moveLines) = getStacksAndMoves()

        moveLines.forEach { line ->
            val numbers = line.split(" ").mapNotNull { it.toIntOrNull() }
            repeat(numbers[0]) {
                val crate = crateStacks[numbers[1] - 1].removeLast()
                crateStacks[numbers[2] - 1].addLast(crate)
            }
        }

        return crateStacks.map { it.last() }.joinToString(separator = "")
    }

    override fun part2(): Any {
        val (crateStacks, moveLines) = getStacksAndMoves()

        moveLines.forEach { line ->
            val numbers = line.split(" ").mapNotNull { it.toIntOrNull() }
            val movedCrates = ArrayDeque<Char>()
            repeat(numbers[0]) {
                movedCrates.addFirst(crateStacks[numbers[1] - 1].removeLast())
            }
            movedCrates.forEach {
                crateStacks[numbers[2] - 1].addLast(it)
            }
        }

        return crateStacks.map { it.last() }.joinToString(separator = "")
    }

    private fun getStacksAndMoves(): Pair<List<ArrayDeque<Char>>, List<String>> {
        var numStacks = 0
        val crateLines = mutableListOf<String>()
        val moveLines = mutableListOf<String>()

        puzzleInput.forEach { line ->
            when {
                line.isBlank() -> Unit
                line.startsWith(" 1") -> numStacks = line.trim().split(" ").last().toInt()
                line.startsWith("move") -> moveLines.add(line)
                else -> crateLines.add(line)
            }
        }

        val crateStacks = getCrateStacks(numStacks, crateLines)
        return Pair(crateStacks, moveLines)
    }

    private fun getCrateStacks(numStacks: Int, crateLines: List<String>): List<ArrayDeque<Char>> {
        val crateStacks = buildList {
            repeat(numStacks) { add(ArrayDeque<Char>()) }
        }

        crateLines.forEach { line ->
            var remainingLine = line
            var stackIndex = 0

            while (remainingLine.isNotEmpty()) {
                if (remainingLine.startsWith("[")) {
                    crateStacks[stackIndex].addFirst(remainingLine[1])
                }
                remainingLine = remainingLine.drop(4)
                stackIndex++
            }
        }

        return crateStacks
    }
}