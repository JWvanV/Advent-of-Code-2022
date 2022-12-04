package day2.model

enum class Outcome(val char: Char, val score: Int) {
    WIN('Z', 6),
    DRAW('Y', 3),
    LOSE('X', 0);

    companion object {
        fun fromChar(char: Char) = values().first { char == it.char }
    }
}