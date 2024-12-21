package problem.day21

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day21Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "029A",
        "980A",
        "179A",
        "456A",
        "379A",
    )

    val sampleSolutionPart1 = 126384

    val sampleSolutionPart2 = 154115708116294L

    "Solving day 21" - {
        "part 1 for the sample input should return the correct output" {
            solveDay21Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay21Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
