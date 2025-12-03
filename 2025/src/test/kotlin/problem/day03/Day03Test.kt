package problem.day03

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day03Test :
    FreeSpec({

        val sampleInput: List<String> =
            listOf(
                "987654321111111",
                "811111111111119",
                "234234234234278",
                "818181911112111",
            )

        val sampleSolutionPart1 = 357L

        val sampleSolutionPart2 = 3121910778619L

        "Solving day 3" - {
            "part 1 for the sample input should return the correct output" {
                solveDay03Part1(sampleInput) shouldBe sampleSolutionPart1
            }

            "part 2 for the sample input should return the correct output" {
                solveDay03Part2(sampleInput) shouldBe sampleSolutionPart2
            }
        }
    })
