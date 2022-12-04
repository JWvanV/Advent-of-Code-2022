package day2.model

enum class Move(val opponentChar: Char, val myChar: Char, val score: Int) {
    ROCK('A', 'X', 1),
    PAPER('B', 'Y', 2),
    SCISSORS('C', 'Z', 3);

    companion object {
        fun fromChar(char: Char) = values().first { char == it.opponentChar || char == it.myChar }
    }
}