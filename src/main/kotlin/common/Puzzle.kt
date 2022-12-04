package common

import common.util.log
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
abstract class Puzzle(private val day: Int, filename: String = "input") {

    protected val puzzleInput = Input("inputs/day${day}/$filename.txt")

    fun solve(expectedSolution1: Any? = null, expectedSolution2: Any? = null): PuzzleSolution {
        val timedSolution1 = measureTimedValue { part1() }
        val timedSolution2 = measureTimedValue { part2() }

        if (expectedSolution1 != null)
            assert(expectedSolution1 == timedSolution1.value)
        if (expectedSolution2 != null)
            assert(expectedSolution2 == timedSolution2.value)

        return PuzzleSolution(
            day,
            PartSolution(
                timedSolution1.value,
                timedSolution1.duration,
            ),
            PartSolution(
                timedSolution2.value,
                timedSolution2.duration,
            ),
            timedSolution1.duration + timedSolution2.duration
        )
            .also { log(it) }
    }

    abstract fun part1(): Any
    abstract fun part2(): Any

    data class PuzzleSolution(
        val day: Int,
        val part1: PartSolution,
        val part2: PartSolution,
        val duration: Duration,
    )

    data class PartSolution(
        val value: Any,
        val duration: Duration,
    )
}