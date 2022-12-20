package problem.day20

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day20Test : FreeSpec({

    val sampleInput: List<String> = """
        1
        2
        -3
        3
        -2
        0
        4
    """.trimIndent().split('\n')

    val sampleSolutionPart1 = 3

    val sampleSolutionPart2 = 1623178306

    "Solving day 20" - {
        "part 1 for the sample input should return the correct output" {
            solveDay20Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay20Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
