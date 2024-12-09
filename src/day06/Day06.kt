package day06

import println
import readInput

fun main() {
    val day = "06"

//    2162
    fun part1(input: List<String>): Int {
        val m = Matrix(input)
        return Game(m).walk1()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput1 = readInput("day$day/test")
    val testInput2 = readInput("day$day/test")
    val trueInput = readInput("day$day/input")

    val part1 = part1(testInput1)
    check(part1 == 41) { "Test part 1 failed: $part1" }
    part1(trueInput).println()

    check(part2(testInput2) == 1) { "Test part 2 failed: ${part2(testInput2)}" }
    part2(trueInput).println()
}

typealias Point = Direction

class Game(val board: Matrix) {
    fun walk1(): Int {
        var playerLocation = board.filter { it == "^" }[0]
        val directions = getEndlessDirections()
        val path = mutableListOf(playerLocation)

        directions.takeWhile { direction ->
            var newLocation = playerLocation + direction
            while (board.isFreeCell(newLocation) && board.isInBounds(newLocation)) {
                path.add(newLocation)
                newLocation += direction
                playerLocation = newLocation
            }

            if (!board.isInBounds(newLocation)) {
                playerLocation
            }

            playerLocation -= direction

            return@takeWhile board.isInBounds(newLocation)
        }.toList()

        path.forEach { println(it) }

        return path.toSet().size
    }

    private fun getEndlessDirections() = sequence {
        while (true) {
            yieldAll(Direction.directions)
        }
    }
}

class Matrix(input: List<String>) {
    private val matrix: List<List<String>> = input.map { it.split("").filter { it.isNotEmpty() } }

    operator fun get(i: Int, j: Int): String {
        if (i < 0 || i >= matrix.size || j < 0 || j >= matrix[0].size) {
            return ""
        }
        return matrix[j][i]
    }

    operator fun get(p: Point): String {
        if (p.x < 0 || p.x >= matrix[0].size || p.y < 0 || p.y >= matrix.size) {
            return ""
        }
        return matrix[p.y][p.x]
    }

    fun filter(predicate: (String) -> Boolean) =
        sequence {
            for (row in matrix.withIndex()) {
                for (cell in row.value.withIndex()) {
                    if (predicate.invoke(cell.value)) {
                        yield(Point(cell.index, row.index))
                    }
                }
            }
        }.toList()

    fun isInBounds(p: Point) =
        0 <= p.x && p.x < matrix.size && 0 <= p.y && p.y < matrix[0].size

    fun isFreeCell(p: Point) =
        this[p] == "." || this[p] == "^"

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

data class Direction(val x: Int, val y: Int) {
    infix operator fun times(scalar: Int) =
        Direction(x * scalar, y * scalar)

    infix operator fun plus(that: Direction) =
        Direction(x + that.x, y + that.y)

    infix operator fun minus(that: Direction) =
        Direction(x - that.x, y - that.y)

    companion object {
        val LEFT = Direction(-1, 0)
        val RIGHT = Direction(1, 0)
        val UP = Direction(0, -1)
        val DOWN = Direction(0, 1)

        val directions = listOf(UP, RIGHT, DOWN, LEFT)
    }
}
