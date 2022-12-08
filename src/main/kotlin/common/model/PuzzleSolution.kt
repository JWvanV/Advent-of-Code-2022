package common.model

import kotlin.time.Duration

data class PuzzleSolution(
    val day: Int,
    val part1: Part,
    val part2: Part,
    val totalDuration: Duration,
) {
    override fun toString() = """
        Puzzle day $day ($totalDuration):
        - Part 1: ${part1.value} (${part1.parseDuration} -> ${part1.computeDuration})
        - Part 2: ${part2.value} (${part2.parseDuration} -> ${part2.computeDuration})
        """.trimIndent()

    data class Part(
        val value: Any,
        val parseDuration: Duration,
        val computeDuration: Duration,
    ) {
        val totalDuration = parseDuration + computeDuration
    }
}