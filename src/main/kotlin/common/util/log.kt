package common.util

var loggingEnabled = true

fun log(message: Any) {
    if (loggingEnabled)
        println(message)
}