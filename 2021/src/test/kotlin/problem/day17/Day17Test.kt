package problem.day17

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day17Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "target area: x=20..30, y=-10..-5",
    )

    val sampleSolutionPart1 = 45

    val sampleSolutionPart2 = 112

    "Solving day 17" - {
        "part 1 for the sample input should return the correct output" {
            solveDay17Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay17Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
