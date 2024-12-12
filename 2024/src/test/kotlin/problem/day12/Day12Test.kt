package problem.day12

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day12Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "RRRRIICCFF",
        "RRRRIICCCF",
        "VVRRRCCFFF",
        "VVRCCCJFFF",
        "VVVVCJJCFE",
        "VVIVCCJJEE",
        "VVIIICJJEE",
        "MIIIIIJJEE",
        "MIIISIJEEE",
        "MMMISSJEEE",
    )

    val sampleSolutionPart1 = 1930

    val sampleSolutionPart2 = 1206

    "Solving day 12" - {
        "part 1 for the sample input should return the correct output" {
            solveDay12Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay12Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
