package problem.day01

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day01Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "3   4",
        "4   3",
        "2   5",
        "1   3",
        "3   9",
        "3   3",
    )

    val sampleSolutionPart1 = 11

    val sampleSolutionPart2 = 31

    "Solving day 1" - {
        "part 1 for the sample input should return the correct output" {
            solveDay01Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay01Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
