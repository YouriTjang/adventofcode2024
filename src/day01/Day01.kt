package day01

import kotlin.math.abs
import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val pairs = input
            .map { line -> line.split("   ") }
            .map { it[0].toInt() to it[1].toInt() }

        val keys = pairs.map { it.first }.sorted().toList()
        val values = pairs.map { it.second }.sorted().toList()

        val zips = keys.zip(values)
        zips.println()
        return zips
            .sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int {
        val pairs = input
            .map { line -> line.split("   ") }
            .map { it[0].toInt() to it[1].toInt() }

        val keys = pairs.map { it.first }.sorted().toList()
        val values = pairs.map { it.second }.sorted().toList()

        val keyCounts = keys.toSet().associateWith { key -> key * values.filter { it == key }.size }
        val sumOf = keys.sumOf { key -> keyCounts[key]!! }
        return sumOf
    }

    val testInput = readInput("day01/test")
    check(part1(testInput) == 11) { "Test 1 failed" }

    val input = readInput("day01/input")
    part1(input).println()


    val part2TestInput = readInput("day01/test")
    check(part2(part2TestInput) == 31) { "Test 2 failed" }
    part2(input).println()
}
