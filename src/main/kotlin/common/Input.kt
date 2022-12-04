package common

import java.io.File

class Input(private val filePath: String) {
    fun forEach(forEach: (line: String) -> Unit) {
        forEachLine(forEach)
    }

    fun <R> mapEach(map: (line: String) -> R): List<R> = mutableListOf<R>().apply {
        forEachLine { line ->
            add(map(line))
        }
    }

    fun count(predicate: (line: String) -> Boolean): Int {
        var count = 0
        forEachLine { if (predicate(it)) count++ }
        return count
    }

    private fun forEachLine(forEach: (line: String) -> Unit) {
        File(filePath).forEachLine { line ->
            forEach(line)
        }
    }

    companion object {
        const val EXAMPLE = "example"
        const val ASSIGNMENT = "assignment"
    }
}

