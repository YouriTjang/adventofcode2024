package day04

import day04.Direction.Companion.directions
import println
import readInput

fun main() {
    val day = "04"

    fun part1(input: List<String>): Int {
        val matrix = Matrix(input)
        matrix.print()

        var sum = 0

        for (i in matrix.matrix.indices) {
            for (j in matrix.matrix[0].indices) {
                if(matrix[i, j] == "X") {
                    val xmasses = matrix.findXmasses(Point(i, j))
                    print(xmasses)
                    sum += xmasses
                } else {
                    print(".")
                }
            }
            print("\n")
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput1 = readInput("day$day/test")
    val testInput2 = readInput("day$day/test")
    val trueInput = readInput("day$day/input")

    check(part1(testInput1) == 18) { "Test part 1 failed: ${part1(testInput1)}" }
    part1(trueInput).println()

    check(part2(testInput2) == 1) { "Test part 1 failed: ${part2(testInput2)}" }
    part2(trueInput).println()
}

class Matrix(input: List<String>) {
    val matrix: List<List<String>>
    val chars = "XMAS".split("").filter { it.isNotEmpty() }

    init {
        matrix = input.map { it.split("").filter { it.isNotEmpty() } }
    }

    operator fun get(i: Int, j: Int): String {
        if (i < 0 || i >= matrix.size || j < 0 || j >= matrix[0].size) {
            return ""
        }
        return matrix[i][j]
    }

    operator fun get(p: Direction): String {
        if (p.i < 0 || p.i >= matrix.size || p.j < 0 || p.j >= matrix[0].size) {
            return ""
        }
        return matrix[p.i][p.j]
    }

    fun findXmasses(start: Point) =
        directions.count { findXmas(start, it) }

    fun findXmas(start: Point, direction: Direction): Boolean =
        chars.withIndex().all { this[start + (direction * it.index)] == it.value }


    fun print() {
        for (row in matrix) {
            for (cell in row) {
                print(cell)
            }
            print("\n")
        }
        print("\n")
    }


}

typealias Point = Direction

data class Direction(val i: Int, val j: Int) {
    infix operator fun times(scalar: Int) =
        Direction(i * scalar, j * scalar)

    infix operator fun plus(that: Direction) =
        Direction(i + that.i, j + that.j)

    companion object {
        val UP = Direction(-1, 0)
        val DOWN = Direction(1, 0)
        val LEFT = Direction(0, -1)
        val RIGHT = Direction(0, 1)
        val UP_LEFT = UP + LEFT
        val UP_RIGHT = UP + RIGHT
        val DOWN_LEFT = DOWN + LEFT
        val DOWN_RIGHT = DOWN + RIGHT

        val directions = listOf(UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT)
    }
}