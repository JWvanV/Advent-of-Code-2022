package day11.model

data class Monkey(
    val items: MutableList<ULong>,
    val operation: Operation,
    val testDivisibleValue: ULong,
    val divisibleMonkeyIndex: Int,
    val nonDivisibleMonkeyIndex: Int,
)