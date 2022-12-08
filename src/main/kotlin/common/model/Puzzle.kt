package common.model

import common.util.log
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
abstract class Puzzle<D1, D2>(private val day: Int, filename: String = Input.EXAMPLE) {

    private val lines: List<String> = File("inputs/day${day}/$filename.txt").readLines()

    abstract fun parse1(lines: List<String>): D1

    abstract fun compute1(data: D1): Any

    abstract fun parse2(lines: List<String>): D2

    abstract fun compute2(data: D2): Any

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

    private fun <D> getSolution(
        parseBlock: (lines: List<String>) -> D,
        computeBlock: (data: D) -> Any,
    ): PuzzleSolution.Part {
        val parse = measureTimedValue { parseBlock(lines) }
        val computation = measureTimedValue { computeBlock(parse.value) }
        return PuzzleSolution.Part(computation.value, parse.duration, computation.duration)
    }
}