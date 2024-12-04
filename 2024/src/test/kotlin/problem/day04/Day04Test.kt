package problem.day04

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import problem.day03.solveDay03Part1
import problem.day03.solveDay03Part2

class Day04Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "MMMSXXMASM",
        "MSAMXMSMSA",
        "AMXSXMAAMM",
        "MSAMASMSMX",
        "XMASAMXAMM",
        "XXAMMXXAMA",
        "SMSMSASXSS",
        "SAXAMASAAA",
        "MAMMMXMMMM",
        "MXMXAXMASX",
    )

    val sampleInput2: List<String> = listOf(
        ".M.S......",
        "..A..MSMS.",
        ".M.S.MAA..",
        "..A.ASMSM.",
        ".M.S.M....",
        "..........",
        "S.S.S.S.S.",
        ".A.A.A.A..",
        "M.M.M.M.M.",
        "..........",
    )

    val sampleSolutionPart1 = 18

    val sampleSolutionPart2 = 9

    "Solving day 1" - {
        "part 1 for the sample input should return the correct output" {
            solveDay04Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay04Part2(sampleInput2) shouldBe sampleSolutionPart2
        }
    }
})
