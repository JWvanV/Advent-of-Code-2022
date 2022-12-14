import com.google.common.truth.Truth.assertThat
import common.model.Puzzle
import common.model.PuzzleSolution
import common.util.loggingEnabled
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration
import kotlin.time.DurationUnit

class SolutionTests {

    private val loggingWasEnabled = loggingEnabled

    @BeforeTest
    fun setup() {
        // Set to true if you want to get the durations for each puzzle
        loggingEnabled = false
        System.gc()
    }

    @AfterTest
    fun teardown() {
        loggingEnabled = loggingWasEnabled
    }

    @Test
    fun `Day 1`() = assertPuzzleSolutions(day1.puzzle, 71780, 212489)

    @Test
    fun `Day 2`() = assertPuzzleSolutions(day2.puzzle, 12586, 13193)

    @Test
    fun `Day 3`() = assertPuzzleSolutions(day3.puzzle, 7889, 2825)

    @Test
    fun `Day 4`() = assertPuzzleSolutions(day4.puzzle, 524, 798)

    @Test
    fun `Day 5`() = assertPuzzleSolutions(day5.puzzle, "WCZTHTMPS", "BLSGJSDTS")

    @Test
    fun `Day 6`() = assertPuzzleSolutions(day6.puzzle, 1855, 3256)

    @Test
    fun `Day 7`() = assertPuzzleSolutions(day7.puzzle, 1325919, 2050735)

    @Test
    fun `Day 8`() = assertPuzzleSolutions(day8.puzzle, 1733, 284648)

    @Test
    fun `Day 9`() = assertPuzzleSolutions(day9.puzzle, 6314, 2504)

    @Test
    fun `Day 10`() = assertPuzzleSolutions(day10.puzzle, 11960, "EJCFPGLH")

    @Test
    fun `Day 11`() = assertPuzzleSolutions(day11.puzzle, 61503UL, 14081365540UL)

    private fun <D1, D2> assertPuzzleSolutions(puzzle: Puzzle<D1, D2>, solution1: Any, solution2: Any) {
        with(puzzle.solve()) {
            assertThat(part1.value).isEqualTo(solution1)
            assertThat(part2.value).isEqualTo(solution2)
            printDurationSection(this)
        }
    }

    private fun printDurationSection(solution: PuzzleSolution, reportUnit: DurationUnit? = null) {
        println(
            "| ${solution.day} " +
                    "| ${solution.totalDuration.format(reportUnit)} " +
                    "| ${solution.part1.parseDuration.format(reportUnit)} " +
                    "| ${solution.part1.computeDuration.format(reportUnit)} " +
                    "| ${solution.part2.parseDuration.format(reportUnit)} " +
                    "| ${solution.part2.computeDuration.format(reportUnit)} |"
        )
    }

    private fun Duration.format(reportUnit: DurationUnit?) = when (reportUnit) {
        null -> toString()
        else -> toString(reportUnit, decimals = 2)
    }
}