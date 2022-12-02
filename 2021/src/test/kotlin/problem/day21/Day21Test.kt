package problem.day21

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day21Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "Player 1 starting position: 4",
        "Player 2 starting position: 8",
    )

    val sampleSolutionPart1 = 739785

    val sampleSolutionPart2 = 444356092776315

    "Solving day 21" - {
        "part 1 for the sample input should return the correct output" {
            solveDay21Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay21Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
