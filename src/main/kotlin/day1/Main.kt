package day1

import common.Input
import common.Puzzle
import day1.model.Elf
import day1.model.FoodItem

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle<List<Elf>, List<Elf>>(1, Input.ASSIGNMENT) {

    override fun parse1() = getElfs()

    override fun compute1(data: List<Elf>) = data.maxOf { it.totalFoodCalories() }

    override fun parse2() = getElfs()

    override fun compute2(data: List<Elf>) = data.map { it.totalFoodCalories() }.sorted().takeLast(3).sum()

    private fun getElfs(): List<Elf> {
        val snacks = puzzleInput.mapEach { input ->
            if (input.isBlank())
                null
            else
                FoodItem(calories = input.toInt())
        }

        val elfs = buildList {
            var currentElfSnacks = mutableListOf<FoodItem>()

            snacks.forEach { snack ->
                if (snack == null) {
                    add(Elf(currentElfSnacks))
                    currentElfSnacks = mutableListOf()
                } else {
                    currentElfSnacks.add(snack)
                }
            }

            if (currentElfSnacks.isNotEmpty())
                add(Elf(currentElfSnacks))
        }

        return elfs
    }
}