package day4

import common.Input
import common.Puzzle

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle(4, Input.ASSIGNMENT) {
    override fun part1(): Any {
        val rangeContainCount = puzzleInput.count { line ->
            val elfRangeStrings = line.split(',')
            val elfRange1 = getRange(elfRangeStrings[0])
            val elfRange2 = getRange(elfRangeStrings[1])

            elfRange1.contains(elfRange2) || elfRange2.contains(elfRange1)
        }

        return rangeContainCount
    }

    override fun part2(): Any {
        val rangeOverlapCount = puzzleInput.count { line ->
            val elfRangeStrings = line.split(',')
            val elfRange1 = getRange(elfRangeStrings[0])
            val elfRange2 = getRange(elfRangeStrings[1])

            elfRange1.contains(elfRange2) || elfRange2.contains(elfRange1) || elfRange1.overlaps(elfRange2)
        }

        return rangeOverlapCount
    }

    private fun getRange(rangeDescription: String) =
        rangeDescription.split('-').let {
            UIntRange(it.first().toUInt(), it.last().toUInt())
        }

    private fun UIntRange.contains(other: UIntRange): Boolean {
        return contains(other.first) && contains(other.last)
    }

    private fun UIntRange.overlaps(other: UIntRange): Boolean {
        return contains(other.first) || contains(other.last)
    }
}