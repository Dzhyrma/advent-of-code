package problem.day09

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day09Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "2333133121414131402",
    )

    val sampleSolutionPart1 = 1928

    val sampleSolutionPart2 = 2858

    "Solving day 9" - {
        "part 1 for the sample input should return the correct output" {
            solveDay09Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay09Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
