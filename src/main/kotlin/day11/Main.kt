package day11

import common.model.Input
import common.model.Puzzle
import common.util.log
import day11.model.Monkey
import day11.model.Operation

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle<List<Monkey>, List<Monkey>>(11, Input.ASSIGNMENT) {

    override fun parse1(lines: List<String>) = getMonkeys(lines)

    override fun compute1(data: List<Monkey>) = getMonkeyBusiness(data, 20, 3UL)

    override fun parse2(lines: List<String>) = getMonkeys(lines)

    override fun compute2(data: List<Monkey>) = getMonkeyBusiness(data, 10_000)

    private fun getMonkeyBusiness(monkeys: List<Monkey>, rounds: Int, worryReduction: ULong = 1UL): ULong {
        val simplifierValue = getSimplifierValue(monkeys)
        val monkeyInspections = getMonkeyInspections(monkeys, rounds, worryReduction, simplifierValue)
        return getMonkeyBusiness(monkeyInspections)
    }

    private fun getSimplifierValue(monkeys: List<Monkey>): ULong {
        var value = 1UL
        monkeys.forEach { value *= it.testDivisibleValue }
        return value
    }

    private fun getMonkeyBusiness(inspections: Array<ULong>) =
        inspections.sortedDescending().subList(0, 2).reduce { acc, i -> acc * i }

    private fun getMonkeyInspections(
        monkeys: List<Monkey>,
        rounds: Int,
        worryReduction: ULong,
        worrySimplifier: ULong,
    ): Array<ULong> {
        val monkeyInspections = Array(monkeys.size) { 0UL }

        repeat(rounds) { round ->
            log("Round ${round + 1}")
            monkeys.forEachIndexed { index, monkey ->
//                log("Monkey $index: ${monkey.items} ${monkey.divisibleMonkeyIndex} / ${monkey.nonDivisibleMonkeyIndex}")
                monkey.items.forEach { item ->
                    // Monkey inspects
                    monkeyInspections[index]++
                    val inspectedWorryLevel = monkey.operation(item)
                    //Monkey gets bored
                    val reducedWorryLevel = if (worryReduction == 1UL) {
                        inspectedWorryLevel % worrySimplifier
                    } else {
                        inspectedWorryLevel / worryReduction
                    }
                    // Monkey throws
                    if (reducedWorryLevel % monkey.testDivisibleValue == 0UL) {
                        monkeys[monkey.divisibleMonkeyIndex]
                    } else {
                        monkeys[monkey.nonDivisibleMonkeyIndex]
                    }.items.add(reducedWorryLevel)
                }
                monkey.items.clear()
            }

//            log("After round ${round + 1}")
//            monkeys.forEachIndexed { index, monkey ->
//                log("Monkey $index: [${monkeyInspections[index]}] ${monkey.items} ")
//            }
        }

        return monkeyInspections
    }

    private fun getMonkeys(lines: List<String>): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()

        var tempItems: List<ULong> = emptyList()
        var tempOperation: Operation = Operation.Plus(0UL)
        var tempDivisibleValue = 0UL
        var tempTrueMonkeyIndex = 0

        lines.forEach {
            when {
                it.startsWith("  Starting") -> tempItems = it.drop(18).split(", ").map { it.toULong() }
                it.startsWith("  Operation") -> {
                    val operation = it.drop(23)
                    val value = operation.drop(2)
                    tempOperation = when {
                        operation == "* old" -> Operation.Power()
                        operation[0] == '+' -> Operation.Plus(value.toULong())
                        operation[0] == '*' -> Operation.Times(value.toULong())
                        else -> error("Unknown operation")
                    }
                }

                it.startsWith("  Test") -> tempDivisibleValue = it.drop(21).toULong()
                it.startsWith("    If true") -> tempTrueMonkeyIndex = it.drop(29).toInt()
                it.startsWith("    If false") -> monkeys.add(
                    Monkey(
                        items = tempItems.toMutableList(),
                        operation = tempOperation,
                        testDivisibleValue = tempDivisibleValue,
                        divisibleMonkeyIndex = tempTrueMonkeyIndex,
                        nonDivisibleMonkeyIndex = it.drop(30).toInt()
                    )
                )

                else -> Unit
            }
        }

        return monkeys
    }
}