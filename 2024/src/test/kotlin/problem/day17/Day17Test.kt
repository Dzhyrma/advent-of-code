package problem.day17

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day17Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "Register A: 729",
        "Register B: 0",
        "Register C: 0",
        "",
        "Program: 0,1,5,4,3,0",
    )

    val sampleInput2: List<String> = listOf(
        "Register A: 2024",
        "Register B: 0",
        "Register C: 0",
        "",
        "Program: 0,3,5,4,3,0",
    )

    val sampleSolutionPart1_1 = "4,6,3,5,6,3,5,2,1,0"

    val sampleSolutionPart2 = 117440

    "Solving day 17" - {
        "part 1 for the sample input should return the correct output" {
            solveDay17Part1(sampleInput1) shouldBe sampleSolutionPart1_1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay17Part2(sampleInput2) shouldBe sampleSolutionPart2
        }
    }
})
