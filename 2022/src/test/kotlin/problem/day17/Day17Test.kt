package problem.day17

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day17Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>",
    )

    val sampleSolutionPart1 = 3068

    val sampleSolutionPart2 = 1514285714288

    "Solving day 17" - {
        "part 1 for the sample input should return the correct output" {
            solveDay17Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay17Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
