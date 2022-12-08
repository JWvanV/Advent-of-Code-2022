package day1

import common.model.Input
import common.model.Puzzle
import kotlin.io.path.Path
import kotlin.io.path.forEachLine
import kotlin.math.max
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
fun main() {
//    puzzle.solve()

    val maxCalories = measureTimedValue {
        var maxCalories = 0
        var currentCalories = 0

        Path("inputs/day1/assignment2.txt").forEachLine { line ->
            if (line.isEmpty()) {
                maxCalories = max(maxCalories, currentCalories)
                currentCalories = 0
            } else {
                currentCalories += line.toInt()
            }
        }

        maxCalories = max(maxCalories, currentCalories)

        maxCalories
    }


    println(maxCalories)
}

val puzzle = object : Puzzle<List<Int>, List<Int>>(1, Input.ASSIGNMENT) {

    override fun parse1(lines: List<String>) = getCalories(lines)

    override fun compute1(data: List<Int>) = data.max()

    override fun parse2(lines: List<String>) = getCalories(lines)

    override fun compute2(data: List<Int>) = data.sorted().takeLast(3).sum()

    private fun getCalories(lines: List<String>) = buildList {
        var elfCalories = 0

        lines.forEach { line ->
            if (line.isBlank()) {
                add(elfCalories)
                elfCalories = 0
            } else {
                elfCalories += line.toInt()
            }
        }
    }
}