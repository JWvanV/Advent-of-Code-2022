package day2

import common.Puzzle
import day2.model.Move
import day2.model.Outcome
import day2.model.Round1
import day2.model.Round2

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle<List<Round1>, List<Round2>>(2, Input.ASSIGNMENT) {

    override fun parse1(lines: List<String>) = lines.map {
        val chars = it.toCharArray()
        Round1(
            opponentMove = Move.fromChar(chars[0]),
            myMove = Move.fromChar(chars[2])
        )
    }

    override fun compute1(data: List<Round1>) = data.sumOf(::calculateScore1)

    override fun parse2(lines: List<String>) = lines.map {
        val chars = it.toCharArray()
        Round2(
            opponentMove = Move.fromChar(chars[0]),
            outcome = Outcome.fromChar(chars[2])
        )
    }

    override fun compute2(data: List<Round2>) = data.sumOf(::calculateScore2)

    private fun calculateScore1(round: Round1) = with(round) {
        myMove.score + getOutcome(opponentMove, myMove).score
    }

    private fun calculateScore2(round: Round2) = with(round) {
        getMyMove(opponentMove, outcome).score + outcome.score
    }

    private fun getOutcome(opponentMove: Move, myMove: Move): Outcome {
        return when (opponentMove) {
            Move.ROCK -> when (myMove) {
                Move.ROCK -> Outcome.DRAW
                Move.PAPER -> Outcome.WIN
                Move.SCISSORS -> Outcome.LOSE
            }

            Move.PAPER -> when (myMove) {
                Move.ROCK -> Outcome.LOSE
                Move.PAPER -> Outcome.DRAW
                Move.SCISSORS -> Outcome.WIN
            }

            Move.SCISSORS -> when (myMove) {
                Move.ROCK -> Outcome.WIN
                Move.PAPER -> Outcome.LOSE
                Move.SCISSORS -> Outcome.DRAW
            }
        }
    }

    private fun getMyMove(opponentMove: Move, outcome: Outcome): Move {
        return when (opponentMove) {
            Move.ROCK -> when (outcome) {
                Outcome.WIN -> Move.PAPER
                Outcome.DRAW -> Move.ROCK
                Outcome.LOSE -> Move.SCISSORS
            }

            Move.PAPER -> when (outcome) {
                Outcome.WIN -> Move.SCISSORS
                Outcome.DRAW -> Move.PAPER
                Outcome.LOSE -> Move.ROCK
            }

            Move.SCISSORS -> when (outcome) {
                Outcome.WIN -> Move.ROCK
                Outcome.DRAW -> Move.SCISSORS
                Outcome.LOSE -> Move.PAPER
            }
        }
    }
}