package common

import common.util.log
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
abstract class Puzzle<D1, D2>(private val day: Int, filename: String = "input") {

    protected val puzzleInput = Input("inputs/day${day}/$filename.txt")

    fun solve(): PuzzleSolution {
        val solution1 = getSolution(::parse1, ::compute1)
        val solution2 = getSolution(::parse2, ::compute2)

        return PuzzleSolution(
            day = day,
            part1 = solution1,
            part2 = solution2,
            totalDuration = solution1.totalDuration + solution2.totalDuration
        ).also { log(it) }
    }

    private fun <D> getSolution(parseBlock: () -> D, computeBlock: (data: D) -> Any): PartSolution {
        val parse = measureTimedValue(parseBlock)
        val computation = measureTimedValue { computeBlock(parse.value) }
        return PartSolution(computation.value, parse.duration, computation.duration)
    }

    abstract fun parse1(): D1

    abstract fun compute1(data: D1): Any

    abstract fun parse2(): D2

    abstract fun compute2(data: D2): Any

    data class PuzzleSolution(
        val day: Int,
        val part1: PartSolution,
        val part2: PartSolution,
        val totalDuration: Duration,
    ) {
        override fun toString() = """
            Puzzle day $day ($totalDuration):
            - Part 1: ${part1.value} (${part1.parseDuration} -> ${part1.computeDuration})
            - Part 2: ${part2.value} (${part2.parseDuration} -> ${part2.computeDuration})
        """.trimIndent()
    }

    data class PartSolution(
        val value: Any,
        val parseDuration: Duration,
        val computeDuration: Duration,
    ) {
        val totalDuration = parseDuration + computeDuration
    }
}