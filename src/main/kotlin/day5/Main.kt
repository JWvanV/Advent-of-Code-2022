package day5

import common.Puzzle

typealias CrateStack = ArrayDeque<Char>

fun main() {
    puzzle.solve()
}

val puzzle = object :
    Puzzle<Pair<List<CrateStack>, List<IntArray>>, Pair<List<CrateStack>, List<IntArray>>>(5, Input.ASSIGNMENT) {

    override fun parse1(lines: List<String>) = getStacksAndMoves(lines)

    override fun compute1(data: Pair<List<CrateStack>, List<IntArray>>): Any {
        val (crateStacks, moves) = data

        moves.forEach { moveNumbers ->
            repeat(moveNumbers[0]) {
                val crate = crateStacks[moveNumbers[1] - 1].removeLast()
                crateStacks[moveNumbers[2] - 1].addLast(crate)
            }
        }

        return crateStacks.map { it.last() }.joinToString(separator = "")
    }

    override fun parse2(lines: List<String>) = getStacksAndMoves(lines)

    override fun compute2(data: Pair<List<CrateStack>, List<IntArray>>): Any {
        val (crateStacks, moves) = data

        moves.forEach { moveNumbers ->
            val movedCrates = CrateStack()
            repeat(moveNumbers[0]) {
                movedCrates.addFirst(crateStacks[moveNumbers[1] - 1].removeLast())
            }
            movedCrates.forEach {
                crateStacks[moveNumbers[2] - 1].addLast(it)
            }
        }

        return crateStacks.map { it.last() }.joinToString(separator = "")
    }

    private fun getStacksAndMoves(lines: List<String>): Pair<List<CrateStack>, List<IntArray>> {
        var numStacks = 0
        val crateLines = mutableListOf<String>()
        val moveLines = mutableListOf<IntArray>()

        lines.forEach { line ->
            when {
                line.isBlank() ->
                    Unit

                line.startsWith(" 1") ->
                    numStacks = line.trim().split(" ").last().toInt()

                line.startsWith("move") ->
                    moveLines.add(line.split(" ").mapNotNull { it.toIntOrNull() }.toIntArray())

                else -> crateLines.add(line)
            }
        }

        val crateStacks = getCrateStacks(numStacks, crateLines)
        return Pair(crateStacks, moveLines)
    }

    private fun getCrateStacks(numStacks: Int, crateLines: List<String>): List<CrateStack> {
        val crateStacks = buildList {
            repeat(numStacks) { add(CrateStack()) }
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