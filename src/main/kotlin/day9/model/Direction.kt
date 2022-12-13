package day9.model

enum class Direction(private val char: Char) {
    UP('U'),
    LEFT('L'),
    RIGHT('R'),
    DOWN('D');

    companion object {
        fun byChar(char: Char) = values().first { it.char == char }
    }
}