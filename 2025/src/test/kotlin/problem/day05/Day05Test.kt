package problem.day05

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day05Test :
    FreeSpec({

        val sampleInput1: List<String> =
            listOf(
                "3-5",
                "10-14",
                "16-20",
                "12-18",
                "",
                "1",
                "5",
                "8",
                "11",
                "17",
                "32",
            )

        val sampleSolutionPart1 = 3

        val sampleSolutionPart2 = 14

        "Solving day 5" - {
            "part 1 for the sample input should return the correct output" {
                solveDay05Part1(sampleInput1) shouldBe sampleSolutionPart1
            }

            "part 2 for the sample input should return the correct output" {
                solveDay05Part2(sampleInput1) shouldBe sampleSolutionPart2
            }
        }
    })
