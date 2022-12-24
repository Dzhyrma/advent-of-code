package problem.day24

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day24Test : FreeSpec({

    val sampleInput: List<String> = """
        #.######
        #>>.<^<#
        #.<..<<#
        #>v.><>#
        #<^v^^>#
        ######.#
    """.trimIndent().split('\n')

    val sampleSolutionPart1 = 18

    val sampleSolutionPart2 = 54

    "Solving day 24" - {
        "part 1 for the sample input should return the correct output" {
            solveDay24Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay24Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
