package day02

import println
import readInput


fun main() {
    fun List<String>.parseToDomain(): List<List<Int>> {
        return this.map { line -> line.split(" ").map { it.toInt() } }
    }

    fun part1(input: List<String>): Int {
        val lines = input.parseToDomain()
        println(lines)

        val derivatives = lines.map { line -> line to line.windowed(2).map { it[0] - it[1] } }
        val filteredAsc = derivatives.filter { (_, line) ->
            line.all { (it in 1..3) }
        }
        val filteredDesc = derivatives.filter { (_, line) ->
            line.all { (it in -3..-1) }
        }

        val filtered = filteredAsc + filteredDesc
        filtered.println()
        return filtered.count()
    }


    fun part2(input: List<String>): Int {
        val lines = input.parseToDomain()
        println(lines)

        fun List<Int>.permutationsMinusOne(): List<List<Int>> {
            val result = mutableListOf(this)
            val minusOnes: List<List<Int>> = List(this.size) { index ->
                val copy: MutableList<Int> = this.toMutableList()
                copy.removeAt(index)
                copy.toList()
            }
            result.addAll(minusOnes)
            return result
        }

        fun x(derivatives: List<Pair<List<Int>, List<Int>>>, condition: (Int) -> Boolean) =
            derivatives
                .map { line ->
                    line.second
                        .permutationsMinusOne()
                        .any { permutedLine ->
                            val count = permutedLine.count(condition)
                            (permutedLine.size - 2) > count
                        }
                }
                .filter { it }


        val derivatives: List<Pair<List<Int>, List<Int>>> = lines.map { line -> line to line.windowed(2).map { it[0] - it[1] } }
        val filteredAsc = x(derivatives) { it in 1..3 }
        val filteredDesc = x(derivatives) { it in -3..-1 }

        val filtered = filteredAsc + filteredDesc
        filtered.println()
        return filtered.count()
    }

    val testInput = readInput("day02/test")
    val input = readInput("day02/input")

    //part 1
//    check(part1(testInput) == 2)
//    part1(input).println()

    //part 2
    val part2 = part2(testInput)
    check(part2 == 4) { "check failed, result was $part2" }
    part2(input).println()
}
