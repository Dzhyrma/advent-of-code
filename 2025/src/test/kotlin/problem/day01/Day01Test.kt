package problem.day01

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day01Test :
    FreeSpec({

        val sampleInput1: List<String> =
            listOf(
                "L68",
                "L30",
                "R48",
                "L5",
                "R60",
                "L55",
                "L1",
                "L99",
                "R14",
                "L82",
            )

        val sampleSolutionPart1 = 3

        val sampleSolutionPart2 = 6

        "Solving day 1" - {
            "part 1 for the sample input should return the correct output" {
                solveDay01Part1(sampleInput1) shouldBe sampleSolutionPart1
            }

            "part 2 for the sample input should return the correct output" {
                solveDay01Part2(sampleInput1) shouldBe sampleSolutionPart2
            }
        }
    })
