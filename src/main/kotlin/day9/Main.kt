package day9

import common.model.Input
import common.model.Puzzle
import common.util.log
import day9.model.Direction
import day9.model.Node
import day9.model.Position
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    puzzle.solve()
}

typealias Move = Pair<Direction, Int>

val puzzle = object : Puzzle<List<Move>, List<Move>>(9, Input.ASSIGNMENT) {

    override fun parse1(lines: List<String>) = lines.map(::getMove)

    override fun compute1(data: List<Move>) = getTailPositionsAfterMoves(data, 1)

    override fun parse2(lines: List<String>) = lines.map(::getMove)

    override fun compute2(data: List<Move>): Any {
        return getTailPositionsAfterMoves(data, 9)
    }

    private fun getMove(line: String) = Direction.byChar(line[0]) to line.drop(2).toInt()

    private fun getTailPositionsAfterMoves(moves: List<Move>, tailLength: Int): Int {
        log("GetTailPositionsAfterMoves $tailLength")
        val tailPositions = mutableSetOf<Position>()

        var tail: Node? = null
        repeat(tailLength) {
            tail = Node(Position(0, 0), tail)
        }
        val head = Node(Position(0, 0), tail)

        fun moveChild(parent: Node) {
            if (parent.child == null) {
                tailPositions.add(parent.position.copy())
                log(tailPositions)
            } else {
                val child = parent.child
                val dx = parent.position.x - child.position.x
                val dy = parent.position.y - child.position.y
                val signX = sign(dx.toDouble()).toInt()
                val signY = sign(dy.toDouble()).toInt()
                val tailDistanceX = abs(dx)
                val tailDistanceY = abs(dy)

                if (tailDistanceX > 1) {
                    child.position.x += signX
                    child.position.y = parent.position.y
                } else if (tailDistanceY > 1) {
                    child.position.x = parent.position.x
                    child.position.y += signY
                }

                log("- Moving child to ${child.position}")

                moveChild(child)
            }
        }

        var dx: Int
        var dy: Int
        moves.forEach { move ->
            log("- Moving $move")
            dx = 0
            dy = 0

            // Determine head delta
            when (move.first) {
                Direction.UP -> dy = 1
                Direction.LEFT -> dx = -1
                Direction.RIGHT -> dx = 1
                Direction.DOWN -> dy = -1
            }

            // Move head and tail
            repeat(move.second) {
                // Move head
                head.position.x += dx
                head.position.y += dy

                log("- (${it + 1})Moving Head to ${head.position}")

                moveChild(head)
            }
        }

        log(tailPositions)

        return tailPositions.size
    }
}