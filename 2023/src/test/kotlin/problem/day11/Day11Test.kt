package problem.day11

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day11Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "...#......",
        ".......#..",
        "#.........",
        "..........",
        "......#...",
        ".#........",
        ".........#",
        "..........",
        ".......#..",
        "#...#.....",
    )

    val sampleSolutionPart1 = 374

    val sample1SolutionPart2 = 1030
    val sample2SolutionPart2 = 8410

    "Solving day 11" - {
        "part 1 for the sample input should return the correct output" {
            solveDay11Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay11Part2(sampleInput, 10) shouldBe sample1SolutionPart2
            solveDay11Part2(sampleInput, 100) shouldBe sample2SolutionPart2
        }
    }
})
