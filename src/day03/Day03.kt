package day03

import println
import readInput

fun main() {
    val rexPart1 = Regex("""mul\((\d*),(\d*)\)""")
    val rexPart2 = Regex("""mul\((\d*),(\d*)\)|do\(\)|don't\(\)""")

    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            rexPart1
                .findAll(line)
                .map { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
                .sum()
        }

    fun part2(input: List<String>): Int {
        val tokens: List<MatchResult> = input.flatMap { line ->
            rexPart2.findAll(line).toList()
        }

        println(tokens)

        var `do` = true
        var sum = 0
        for (token in tokens) {
            when {
                token.value.startsWith("mul") && `do` -> {
                    val a = token.groupValues[1].toInt()
                    val b = token.groupValues[2].toInt()
                    sum += a * b
                }
                token.value.startsWith("don't") -> `do` = false
                token.value.startsWith("do") -> `do` = true

            }
        }

        return sum
    }


    val testInput = readInput("day03/test")
    val testInput2 = readInput("day03/test_part2")
    val input = readInput("day03/input")

    check(part1(testInput) == 161) { "Test part 1 failed: ${part1(testInput)}" }
    part1(input).println()

    check(part2(testInput2) == 48) { "Test part 2 failed: ${part2(testInput2)}" }
    part2(input).println()
}
