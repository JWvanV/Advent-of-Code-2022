package day3

import common.Input
import common.Puzzle

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle<List<String>, List<String>>(3, Input.ASSIGNMENT) {

    override fun parse1() = puzzleInput.lines

    override fun compute1(data: List<String>): Any {
        val backpackPriorities = data.map { line ->
            val compartment1 = line.dropLast(line.length / 2).toCharArray()
            val compartment2 = line.drop(line.length / 2).toCharArray()

            val compartment1Items = compartment1.toHashSet()
            val compartment2Items = compartment2.toHashSet()

            val sharedItem = compartment1Items.intersect(compartment2Items).first()
            getItemPriority(sharedItem)
        }
        return backpackPriorities.sum()
    }

    override fun parse2() = puzzleInput.lines

    override fun compute2(data: List<String>): Any {
        val groupItemChars = mutableListOf<Char>()
        var currentMember = 0
        var itemChars1 = emptySet<Char>()
        var itemChars2 = emptySet<Char>()

        data.forEach { line ->
            val itemChars = line.toCharArray().toSortedSet()

            when (currentMember) {
                0 -> itemChars1 = itemChars
                1 -> itemChars2 = itemChars
                2 -> {
                    val charLists = listOf(itemChars, itemChars1, itemChars2).sortedBy { it.size }
                    groupItemChars.add(findSharedItemChar(charLists[0], charLists[1], charLists[2]))
                }
            }

            currentMember = (currentMember + 1) % 3
        }
        return groupItemChars.sumOf(::getItemPriority)
    }

    private fun findSharedItemChar(
        shortestItemChars: Set<Char>,
        otherItemChars1: Set<Char>,
        otherItemChars2: Set<Char>,
    ): Char {
        shortestItemChars.forEach {
            if (otherItemChars1.contains(it) && otherItemChars2.contains(it))
                return it
        }
        error("No shared character found")
    }

    private fun getItemPriority(char: Char): Int {
        return if (char.isLowerCase())
            char.code - 96
        else
            char.code - 38
    }
}