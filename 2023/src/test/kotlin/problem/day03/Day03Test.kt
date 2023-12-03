package problem.day03

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day03Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...\$.*....",
        ".664.598..",
    )

    val sampleSolutionPart1 = 4361

    val sampleSolutionPart2 = 467835

    "Solving day 3" - {
        "part 1 for the sample input should return the correct output" {
            solveDay03Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay03Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
