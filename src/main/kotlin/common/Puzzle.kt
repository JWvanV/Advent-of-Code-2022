package common

import common.util.log

abstract class Puzzle(private val day: Int, filename: String = "input") {

    protected val puzzleInput = Input("inputs/day${day}/$filename.txt")

    fun solve(expectedSolution1: Any? = null, expectedSolution2: Any? = null): Solution {
        val solution1 = part1()
        val solution2 = part2()

        if (expectedSolution1 != null)
            assert(expectedSolution1 == solution1)
        if (expectedSolution2 != null)
            assert(expectedSolution2 == solution2)

        return Solution(day, solution1, solution2)
            .also { log(it) }
    }

    abstract fun part1(): Any
    abstract fun part2(): Any

    data class Solution(val day: Int, val solution1: Any, val solution2: Any)
}