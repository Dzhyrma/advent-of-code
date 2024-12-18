package problem.day18

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day18Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "5,4",
        "4,2",
        "4,5",
        "3,0",
        "2,1",
        "6,3",
        "2,4",
        "1,5",
        "0,6",
        "3,3",
        "2,6",
        "5,1",
        "1,2",
        "5,5",
        "2,5",
        "6,5",
        "1,4",
        "0,4",
        "6,4",
        "1,1",
        "6,1",
        "1,0",
        "0,5",
        "1,6",
        "2,0",
    )

    val sampleSolutionPart1 = 22

    val sampleSolutionPart2 = "6,1"

    "Solving day 18" - {
        "part 1 for the sample input should return the correct output" {
            solveDay18Part1(sampleInput1, 7, 12) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay18Part2(sampleInput1, 7, 12) shouldBe sampleSolutionPart2
        }
    }
})
