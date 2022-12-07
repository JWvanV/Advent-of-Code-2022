package day4

import common.Input
import common.Puzzle

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle<List<Pair<UIntRange, UIntRange>>, List<Pair<UIntRange, UIntRange>>>(4, Input.ASSIGNMENT) {

    override fun parse1() = getRanges()

    override fun compute1(data: List<Pair<UIntRange, UIntRange>>) = data.count { (range1, range2) ->
        range1.contains(range2) || range2.contains(range1)
    }

    override fun parse2() = getRanges()

    override fun compute2(data: List<Pair<UIntRange, UIntRange>>) = data.count { (range1, range2) ->
        range1.contains(range2) || range2.contains(range1) || range1.overlaps(range2)
    }

    private fun getRanges() = puzzleInput.mapEach { line ->
        val elfRangeStrings = line.split(',')
        getRange(elfRangeStrings[0]) to getRange(elfRangeStrings[1])
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