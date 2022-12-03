package common

import common.util.log

abstract class Puzzle(private val day: Int, filename: String = "input") {

    protected val puzzleInput = Input("inputs/day${day}/$filename.txt")

    fun solve(): Solution {
        val solution = Solution(day, part1(), part2())
        log(solution)
        return solution
    }

    abstract fun part1(): Any
    abstract fun part2(): Any

    data class Solution(val day: Int, val solution1: Any, val solution2: Any)
}