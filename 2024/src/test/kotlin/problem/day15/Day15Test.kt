package problem.day15

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day15Test : FreeSpec({

    val sampleInput1: List<String> = listOf()

    val sampleSolutionPart1 = -1L

    val sampleSolutionPart2 = -1L

    "Solving day 15" - {
        "part 1 for the sample input should return the correct output" {
            solveDay15Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay15Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
