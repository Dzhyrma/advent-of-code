package problem.day19

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day19Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "r, wr, b, g, bwu, rb, gb, br",
        "",
        "brwrr",
        "bggr",
        "gbbr",
        "rrbgbr",
        "ubwu",
        "bwurrg",
        "brgr",
        "bbrgwb",
    )

    val sampleSolutionPart1 = 6

    val sampleSolutionPart2 = 16L

    "Solving day 19" - {
        "part 1 for the sample input should return the correct output" {
            solveDay19Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay19Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
