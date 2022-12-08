package day8

import common.model.Input
import common.model.Puzzle

typealias HeightMap = Array<IntArray>

fun main() {
    puzzle.solve()
}

val puzzle = object : Puzzle<HeightMap, HeightMap>(8, Input.ASSIGNMENT) {

    override fun parse1(lines: List<String>) = getHeightMap(lines)

    override fun compute1(data: HeightMap): Any {
        val visibilityMap = Array(data.size) { BooleanArray(data[0].size) }
        val mapHeight = data.size
        val mapWidth = data.first().size

        // Check from top to bottom
        data.forEachIndexed { row, heights ->
            // From the left
            var rowMaxHeight = 9
            var maxHeight = 0
            run ltrRowBreak@{
                heights.forEachIndexed { column, treeHeight ->
                    if (column == 0 || treeHeight > maxHeight) {
                        visibilityMap[row][column] = true
                        maxHeight = treeHeight

                        if (maxHeight == rowMaxHeight) {
                            return@ltrRowBreak
                        }
                    }
                }
            }
            // From the right
            rowMaxHeight = maxHeight
            maxHeight = 0
            run rtlRowBreak@{
                heights.reversed().forEachIndexed { reversedColumn, treeHeight ->
                    if (reversedColumn == 0 || treeHeight > maxHeight) {
                        visibilityMap[row][mapWidth - reversedColumn - 1] = true
                        maxHeight = treeHeight

                        if (maxHeight == rowMaxHeight) {
                            return@rtlRowBreak
                        }
                    }
                }
            }
        }

        // Check from Left to Right
        for (column in 0 until mapWidth) {
            // From top
            var rowMaxHeight = 9
            var maxHeight = 0
            run ttbBreaking@{
                for (row in 0 until mapHeight) {
                    val treeHeight = data[row][column]
                    if (row == 0 || treeHeight > maxHeight) {
                        visibilityMap[row][column] = true
                        maxHeight = treeHeight

                        if (maxHeight == rowMaxHeight) {
                            return@ttbBreaking
                        }
                    }
                }
            }

            // From bottom
            rowMaxHeight = maxHeight
            maxHeight = 0
            run bttBreaking@{
                for (row in (0 until mapHeight).reversed()) {
                    val treeHeight = data[row][column]
                    if (row == mapHeight - 1 || treeHeight > maxHeight) {
                        visibilityMap[row][column] = true
                        maxHeight = treeHeight

                        if (maxHeight == rowMaxHeight) {
                            return@bttBreaking
                        }
                    }
                }
            }
        }
        return visibilityMap.sumOf { row -> row.count { it } }
    }

    override fun parse2(lines: List<String>) = getHeightMap(lines)

    override fun compute2(data: HeightMap): Any {
        val scenicScoreMap = Array(data.size) { IntArray(data[0].size) }
        val mapHeight = data.size
        val mapWidth = data.first().size

        data.forEachIndexed { treeHouseRow, heights ->
            heights.forEachIndexed { treeHouseColumn, treeHouseHeight ->
                val onEdge =
                    treeHouseRow == 0 || treeHouseRow == mapHeight - 1 || treeHouseColumn == 0 || treeHouseColumn == mapWidth - 1

                if (onEdge) {
                    scenicScoreMap[treeHouseRow][treeHouseColumn] = 0
                } else {

                    var scenicScore = 1

                    // Look up
                    run upBreaking@{
                        var viewingDistance = 0
                        for (checkRow in (0 until treeHouseRow).reversed()) {
                            viewingDistance++

                            val checkTreeHeight = data[checkRow][treeHouseColumn]
                            if (checkRow == 0 || checkTreeHeight >= treeHouseHeight) {
                                scenicScore *= viewingDistance
                                return@upBreaking
                            }
                        }
                    }

                    // Look left
                    run leftBreaking@{
                        var viewingDistance = 0
                        for (checkColumn in (0 until treeHouseColumn).reversed()) {
                            viewingDistance++

                            val checkTreeHeight = data[treeHouseRow][checkColumn]
                            if (checkColumn == 0 || checkTreeHeight >= treeHouseHeight) {
                                scenicScore *= viewingDistance
                                return@leftBreaking
                            }
                        }
                    }

                    // Look right
                    run rightBreaking@{
                        var viewingDistance = 0
                        for (checkColumn in treeHouseColumn + 1 until mapWidth) {
                            viewingDistance++

                            val checkTreeHeight = data[treeHouseRow][checkColumn]
                            if (checkColumn == mapWidth - 1 || checkTreeHeight >= treeHouseHeight) {
                                scenicScore *= viewingDistance
                                return@rightBreaking
                            }
                        }
                    }

                    // Look down
                    run downBreaking@{
                        var viewingDistance = 0
                        for (checkRow in treeHouseRow + 1 until mapHeight) {
                            viewingDistance++

                            val checkTreeHeight = data[checkRow][treeHouseColumn]
                            if (checkRow == mapHeight - 1 || checkTreeHeight >= treeHouseHeight) {
                                scenicScore *= viewingDistance
                                return@downBreaking
                            }
                        }
                    }

                    scenicScoreMap[treeHouseRow][treeHouseColumn] = scenicScore
                }
            }
        }

        return scenicScoreMap.maxOf { it.maxOf { it } }
    }

    private fun getHeightMap(lines: List<String>): HeightMap =
        Array(lines.size) { row -> lines[row].map { it.digitToInt() }.toIntArray() }
}