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

    private fun forEachLine(forEach: (line: String) -> Unit) {
        File(filePath).forEachLine { line ->
            forEach(line)
        }
    }
}

