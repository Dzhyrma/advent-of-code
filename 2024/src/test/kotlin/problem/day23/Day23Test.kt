package problem.day23

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day23Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "ka-co",
        "ta-co",
        "de-co",
        "ta-ka",
        "de-ta",
        "ka-de",
    )

    val sampleSolutionPart1 = 3

    val sampleSolutionPart2 = "co,de,ka,ta"

    "Solving day 23" - {
        "part 1 for the sample input should return the correct output" {
            solveDay23Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay23Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
