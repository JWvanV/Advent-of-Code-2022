package day10.model

sealed interface Instruction {
    object NoOp : Instruction

    data class AddX(val dx: Int) : Instruction
}
