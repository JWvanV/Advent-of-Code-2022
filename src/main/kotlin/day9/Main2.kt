package day9

import java.io.File
import kotlin.math.hypot

private val input = File("inputs/day9/assignment.txt").readLines()

private val moves = parseInput {
    input
}.map { it.toMove() }

fun parseInput(input: () -> List<String>) = input()

data class Node2(val x: Int, val y: Int, var next: Node2? = null)

fun Node2.calcDistance(other: Node2) = hypot((other.x - this.x).toDouble(), (other.y - this.y).toDouble()).toInt()

fun Node2.moveUp() = this.copy(y = y - 1)

fun Node2.moveDown() = this.copy(y = y + 1)

fun Node2.moveRight() = this.copy(x = x + 1)

fun Node2.moveLeft() = this.copy(x = x - 1)

sealed class Direction {
    sealed class Vertical : Direction() {
        object Up : Vertical()
        object Down : Vertical()
    }

    sealed class Horizontal : Direction() {
        object Left : Horizontal()

        object Right : Horizontal()
    }
}

fun String.toDirection() = when (this) {
    "U" -> Direction.Vertical.Up
    "D" -> Direction.Vertical.Down
    "L" -> Direction.Horizontal.Left
    "R" -> Direction.Horizontal.Right
    else -> error("Invalid direction")
}

fun Node2.move(direction: Direction): Node2 = run {
    val newCurrent = when (direction) {
        Direction.Horizontal.Left -> moveLeft()
        Direction.Horizontal.Right -> moveRight()
        Direction.Vertical.Down -> moveDown()
        Direction.Vertical.Up -> moveUp()
    }
    // after a move sync children
    newCurrent.link(newCurrent.next?.sync(newCurrent))
    newCurrent
}

fun Node2.sync(with: Node2): Node2 {
    // if positions are the same no sync necessary
    if (this == with) return this

    val new = when (with.calcDistance(this)) {
        0, 1 -> this
        else -> {
            when {
                (with.x > this.x) -> moveRight()
                (with.x < this.x) -> moveLeft()
                else -> this
            }.run {
                when {
                    (with.y < this.y) -> moveUp()
                    (with.y > this.y) -> moveDown()
                    else -> this
                }
            }
        }
    }

    // current node has moved, recursively sync all children nodes
    new.link(new.next?.sync(new))

    return new
}

const val STARTING_X = 0
const val STARTING_Y = 0

private fun String.toMove() = split(" ").run { Move2(component1().toDirection(), component2().toInt()) }

private fun Node2.play(moves: List<Move2>): Int {
    var head = this
    val uniquePositionSet = mutableSetOf<Node2>()

    moves.forEach { move ->
        repeat(move.count) {
            head = head.move(move.direction).also {
                uniquePositionSet.add(head.last())
            }
        }
    }

    return uniquePositionSet.count()
}

fun Node2.last(): Node2 = when (val right = this.next) {
    null -> this
    else -> right.last()
}

private data class Move2(val direction: Direction, val count: Int)

fun Node2.link(next: Node2?) {
    this.next = next
}

fun main() {
    val day91head = Node2(STARTING_X, STARTING_Y).apply {
        link(Node2(STARTING_X, STARTING_Y))
    }
    println(day91head.play(moves))
    val day92head = Node2(STARTING_X, STARTING_Y).apply {
        repeat(9) {
            last().link(Node2(STARTING_X, STARTING_Y))
        }
    }
    println(day92head.play(moves))
}