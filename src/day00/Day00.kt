package day00

import println
import readInput

fun main() {
    val day = "00"

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput1 = readInput("day$day/test")
    val testInput2 = readInput("day$day/test")
    val trueInput = readInput("day$day/input")

    check(part1(testInput1) == 1) { "Test part 1 failed: ${part1(testInput1)}" }
    part1(trueInput).println()

    check(part2(testInput2) == 1) { "Test part 1 failed: ${part2(testInput2)}" }
    part2(trueInput).println()
}
