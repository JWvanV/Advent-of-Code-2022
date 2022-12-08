package day7

import common.model.Input
import common.model.Puzzle
import day7.model.FileSystem
import day7.model.Node
import java.math.BigInteger

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle<FileSystem, FileSystem>(7, Input.ASSIGNMENT) {

    override fun parse1(lines: List<String>) = getFileSystem(lines)

    override fun compute1(data: FileSystem) =
        data.getDirectories().filter { it.getSize() < BigInteger.valueOf(100000) }.sumOf { it.getSize() }.toInt()

    override fun parse2(lines: List<String>) = getFileSystem(lines)

    override fun compute2(data: FileSystem): Any {
        val totalUsedSpace = data.getRootDirectory().getSize()
        val totalFreeSpace = BigInteger.valueOf(70000000) - totalUsedSpace
        val requiredDeletedSpace = BigInteger.valueOf(30000000) - totalFreeSpace
        return data.getDirectories().filter { it.getSize() > requiredDeletedSpace }.minBy { it.getSize() }.getSize()
            .toInt()
    }

    private fun getFileSystem(lines: List<String>): FileSystem {
        val fileSystem = FileSystem()

        var currentDirectory = fileSystem.getRootDirectory()

        lines.forEach { line ->
            when {
                line.startsWith("$ cd /") ->
                    currentDirectory = fileSystem.getRootDirectory()

                line.startsWith("$ cd ..") ->
                    currentDirectory = currentDirectory.requireParent()

                line.startsWith("$ cd") ->
                    currentDirectory = fileSystem.getDirectory(currentDirectory.getPathName() + line.drop(5))

                line.startsWith("$") ->
                    Unit // Ignore other commands

                line.startsWith("dir") -> {
                    val directory = Node.Directory(currentDirectory, line.drop(4))
                    currentDirectory.addChild(directory)
                    fileSystem.addNode(directory)
                }

                else -> {
                    val split = line.split(" ")
                    val file = Node.File(currentDirectory, split[1], split[0].toBigInteger())
                    currentDirectory.addChild(file)
                    fileSystem.addNode(file)
                }
            }
        }

        return fileSystem
    }
}