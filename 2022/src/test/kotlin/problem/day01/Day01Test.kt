package problem.day01

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day01Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "1000",
        "2000",
        "3000",
        "",
        "4000",
        "",
        "5000",
        "6000",
        "",
        "7000",
        "8000",
        "9000",
        "",
        "10000",
    )

    val sampleSolutionPart1 = 24000

    val sampleSolutionPart2 = 45000

    "Solving day 1" - {
        "part 1 for the sample input should return the correct output" {
            solveDay01Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay01Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
