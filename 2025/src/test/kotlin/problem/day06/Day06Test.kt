package problem.day06

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day06Test :
    FreeSpec({

        val sampleInput1: List<String> =
            listOf(
                "123 328  51 64",
                " 45 64  387 23",
                "  6 98  215 314",
                "*   +   *   +",
            )

        val sampleSolutionPart1 = 4277556L

        val sampleSolutionPart2 = 3263827L

        "Solving day 6" - {
            "part 1 for the sample input should return the correct output" {
                solveDay06Part1(sampleInput1) shouldBe sampleSolutionPart1
            }

            "part 2 for the sample input should return the correct output" {
                solveDay06Part2(sampleInput1) shouldBe sampleSolutionPart2
            }
        }
    })
