package day05

import println
import readInput

fun main() {
    val day = "05"

    fun preprocessData(input: List<String>): Pair<Map<String, Set<String>>, List<List<String>>> {
        val orderRulesInput = mutableListOf<String>()
        val updatesInput = mutableListOf<String>()
        var isTopSection = true

        for (line in input) {
            if (line.isEmpty()) {
                isTopSection = false
                continue
            }
            if (isTopSection) {
                orderRulesInput.add(line)
            } else {
                updatesInput.add(line)
            }
        }

        val orders: List<Pair<String, String>> = orderRulesInput.map { line ->
            val split = line.split("|")
            split[0] to split[1]
        }
        val ordersByInput = orders
            .groupBy({ it.first }) { it.second }
            .map { it.key to it.value.toSet() }
            .toMap()

        val updates = updatesInput.map { line -> line.split(",") }

        return ordersByInput to updates
    }

    fun part1(input: List<String>): Int {
        val (orders, updates) = preprocessData(input)

        return updates.filter { update ->
            update.mapIndexed { index, value ->
                val before = update.slice(0 until index)
                val beforeIsOk = before.none { orders[value]?.contains(it) ?: false }

                val after = update.slice(index + 1 until update.size)
                val afterIsOk = after.all { orders[value]?.contains(it) ?: false }

                beforeIsOk && afterIsOk
            }
                .all { it }
        }
            .map { update -> update[update.size / 2] }
            .map {it.toInt()}
            .sum()
    }

    fun part2(input: List<String>): Int {
        val (orders: Map<String, Set<String>>, updates) = preprocessData(input)

        val filter = updates.filter { update ->
            update.mapIndexed { index, value ->
                val before = update.slice(0 until index)
                val beforeIsOk = before.none { orders[value]?.contains(it) ?: false }

                val after = update.slice(index + 1 until update.size)
                val afterIsOk = after.all { orders[value]?.contains(it) ?: false }

                !beforeIsOk || !afterIsOk
            }
                .any { it }
        }

        val sortedWith = filter.map {
            it.sortedWith { a: String, b ->
                if (orders[a] != null && orders[a]!!.contains(b)) -1 else 1
            }
        }

        return sortedWith
            .map { update -> update[update.size / 2] }
            .map {it.toInt()}
            .sum()

    }



    val testInput1 = readInput("day$day/test")
    val testInput2 = readInput("day$day/test")
    val trueInput = readInput("day$day/input")

    check(part1(testInput1) == 143) { "Test part 1 failed: ${part1(testInput1)}" }
    part1(trueInput).println()

    check(part2(testInput2) == 123) { "Test part 1 failed: ${part2(testInput2)}" }
    part2(trueInput).println()
}
