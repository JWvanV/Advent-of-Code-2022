package template

import common.Input
import common.Puzzle

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle<Any, Any>(0, Input.EXAMPLE) {

    override fun parse1(): Any {
        return Unit
    }

    override fun compute1(data: Any): Any {
        return Unit
    }

    override fun parse2(): Any {
        return Unit
    }

    override fun compute2(data: Any): Any {
        return Unit
    }
}