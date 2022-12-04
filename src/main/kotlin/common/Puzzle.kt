package common

import common.util.log
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
abstract class Puzzle(private val day: Int, filename: String = "input") {

    protected val puzzleInput = Input("inputs/day${day}/$filename.txt")

    fun solve(): PuzzleSolution {
        val solution1 = measureTimedValue(::part1).let { PartSolution(it.value, it.duration) }
        val solution2 = measureTimedValue(::part2).let { PartSolution(it.value, it.duration) }

        return PuzzleSolution(
            day = day,
            part1 = solution1,
            part2 = solution2,
            totalDuration = solution1.duration + solution2.duration
        ).also { log(it) }
    }

    abstract fun part1(): Any
    abstract fun part2(): Any

    data class PuzzleSolution(
        val day: Int,
        val part1: PartSolution,
        val part2: PartSolution,
        val totalDuration: Duration,
    ) {
        override fun toString() = """
            Puzzle day $day ($totalDuration):
            - Part 1: ${part1.value} (${part1.duration})
            - Part 2: ${part2.value} (${part2.duration})
        """.trimIndent()
    }

    data class PartSolution(
        val value: Any,
        val duration: Duration,
    )
}