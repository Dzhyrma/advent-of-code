package problem.day13

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day13Test : FreeSpec({

    val sampleInput1: List<String> = listOf()

    val sampleSolutionPart1 = -1

    val sampleSolutionPart2 = -1

    "Solving day 13" - {
        "part 1 for the sample input should return the correct output" {
            solveDay13Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay13Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
