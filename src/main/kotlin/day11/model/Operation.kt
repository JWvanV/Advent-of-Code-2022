package day11.model

sealed class Operation {

    abstract operator fun invoke(oldValue: ULong): ULong

    abstract val operationValue: ULong

    data class Plus(override val operationValue: ULong) : Operation() {
        override fun invoke(oldValue: ULong) = oldValue + operationValue
    }

    data class Times(override val operationValue: ULong) : Operation() {
        override fun invoke(oldValue: ULong) = oldValue * operationValue
    }

    data class Power(override val operationValue: ULong = 2UL) : Operation() {
        override fun invoke(oldValue: ULong) = oldValue * oldValue
    }
}