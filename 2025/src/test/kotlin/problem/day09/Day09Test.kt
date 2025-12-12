package problem.day09

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day09Test :
    FreeSpec({

        val sampleInput1: List<String> =
            listOf(
                "7,1",
                "11,1",
                "11,7",
                "9,7",
                "9,5",
                "2,5",
                "2,3",
                "7,3",
            )

        val sampleSolutionPart1 = 50L

        val sampleSolutionPart2 = 24L

        "Solving day 9" - {
            "part 1 for the sample input should return the correct output" {
                solveDay09Part1(sampleInput1) shouldBe sampleSolutionPart1
            }

            "part 2 for the sample input should return the correct output" {
                solveDay09Part2(sampleInput1) shouldBe sampleSolutionPart2
            }
        }
    })
