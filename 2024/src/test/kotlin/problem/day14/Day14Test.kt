package problem.day14

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day14Test : FreeSpec({

    val sampleInput1: List<String> = listOf()

    val sampleSolutionPart1 = -1

    val sampleSolutionPart2 = -1

    "Solving day 14" - {
        "part 1 for the sample input should return the correct output" {
            solveDay14Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay14Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
