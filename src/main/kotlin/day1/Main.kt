package day1

import common.Input
import common.Puzzle
import day1.model.Elf
import day1.model.FoodItem

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle(1, Input.ASSIGNMENT) {
    override fun part1(): Any {
        val elfCalories = getElfs().map { it.totalFoodCalories() }
        return elfCalories.max()
    }

    override fun part2(): Any {
        val elfCalories = getElfs().map { it.totalFoodCalories() }
        return elfCalories.sorted().takeLast(3).sum()
    }

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