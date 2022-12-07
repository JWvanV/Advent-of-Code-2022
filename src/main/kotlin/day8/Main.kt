package day8

import common.Puzzle

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle<Any, Any>(8, Input.EXAMPLE) {

    override fun parse1(lines: List<String>) = lines

    override fun compute1(data: Any): Any {
        return Unit
    }

    override fun parse2(lines: List<String>) = lines

    override fun compute2(data: Any): Any {
        return Unit
    }
}