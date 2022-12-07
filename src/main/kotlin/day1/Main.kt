package day1

import common.Puzzle

fun main() {
    puzzle.solve()
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