package problem.day10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day10Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "89010123",
        "78121874",
        "87430965",
        "96549874",
        "45678903",
        "32019012",
        "01329801",
        "10456732",
    )

    val sampleSolutionPart1 = 36

    val sampleSolutionPart2 = 81

    "Solving day 10" - {
        "part 1 for the sample input should return the correct output" {
            solveDay10Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay10Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
