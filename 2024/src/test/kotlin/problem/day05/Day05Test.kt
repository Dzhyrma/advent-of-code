package problem.day05

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day05Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "47|53",
        "97|13",
        "97|61",
        "97|47",
        "75|29",
        "61|13",
        "75|53",
        "29|13",
        "97|29",
        "53|29",
        "61|53",
        "97|53",
        "61|29",
        "47|13",
        "75|47",
        "97|75",
        "47|61",
        "75|61",
        "47|29",
        "75|13",
        "53|13",
        "",
        "75,47,61,53,29",
        "97,61,53,29,13",
        "75,29,13",
        "75,97,47,61,53",
        "61,13,29",
        "97,13,75,29,47",
    )

    val sampleSolutionPart1 = 143

    val sampleSolutionPart2 = 123

    "Solving day 5" - {
        "part 1 for the sample input should return the correct output" {
            solveDay05Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay05Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
