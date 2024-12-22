package problem.day22

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day22Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "1",
        "10",
        "100",
        "2024",
    )

    val sampleInput2: List<String> = listOf(
        "1",
        "2",
        "3",
        "2024",
    )

    val sampleSolutionPart1 = 37327623

    val sampleSolutionPart2 = 23

    "Solving day 22" - {
        "part 1 for the sample input should return the correct output" {
            solveDay22Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay22Part2(sampleInput2) shouldBe sampleSolutionPart2
        }
    }
})
