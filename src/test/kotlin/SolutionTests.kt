import com.google.common.truth.Truth.assertThat
import common.Puzzle
import common.util.loggingEnabled
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class SolutionTests {

    private val loggingWasEnabled = loggingEnabled

    @BeforeTest
    fun setup() {
        // Set to true if you want to get the durations for each puzzle
        loggingEnabled = false
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
    fun `Day 4`() = assertPuzzleSolutions(day4.puzzle, Unit, Unit)

    private fun assertPuzzleSolutions(puzzle: Puzzle, solution1: Any, solution2: Any) {
        with(puzzle.solve()) {
            assertThat(part1.value).isEqualTo(solution1)
            assertThat(part2.value).isEqualTo(solution2)
        }
    }
}