package problem.day25

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day25Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "#####",
        ".####",
        ".####",
        ".####",
        ".#.#.",
        ".#...",
        ".....",
        "",
        "#####",
        "##.##",
        ".#.##",
        "...##",
        "...#.",
        "...#.",
        ".....",
        "",
        ".....",
        "#....",
        "#....",
        "#...#",
        "#.#.#",
        "#.###",
        "#####",
        "",
        ".....",
        ".....",
        "#.#..",
        "###..",
        "###.#",
        "###.#",
        "#####",
        "",
        ".....",
        ".....",
        ".....",
        "#....",
        "#.#..",
        "#.#.#",
        "#####",
    )

    val sampleSolutionPart1 = 3

    "Solving day 25" - {
        "part 1 for the sample input should return the correct output" {
            solveDay25Part1(sampleInput1) shouldBe sampleSolutionPart1
        }
    }
})
