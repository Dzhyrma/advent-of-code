package problem.day24

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day24Test : FreeSpec({

    val sampleInput1: List<String> = listOf()

    val sampleSolutionPart1 = -1L

    val sampleSolutionPart2 = -1L

    "Solving day 24" - {
        "part 1 for the sample input should return the correct output" {
            solveDay24Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay24Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
