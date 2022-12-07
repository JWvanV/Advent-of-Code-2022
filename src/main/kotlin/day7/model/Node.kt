package day7.model

import java.math.BigInteger

sealed class Node {
    abstract val parent: Directory?
    abstract val name: String

    fun requireParent() = parent!!

    abstract fun getPathName(): String

    abstract fun getSize(): BigInteger

    data class Directory(override val parent: Directory?, override val name: String) : Node() {
        private val childNodes = mutableListOf<Node>()
        private var size = BigInteger.ZERO

        fun addChild(child: Node) {
            childNodes.add(child)
            recalculateSize()
        }

        private fun recalculateSize() {
            size = childNodes.sumOf { it.getSize() }
            parent?.recalculateSize()
        }

        override fun getPathName(): String = parent
            ?.let { parent.getPathName() + name }
            ?: run { name }

        override fun getSize(): BigInteger = size
    }

    data class File(override val parent: Directory, override val name: String, private val size: BigInteger) : Node() {
        override fun getPathName(): String = parent.getPathName() + name
        override fun getSize() = size
    }
}