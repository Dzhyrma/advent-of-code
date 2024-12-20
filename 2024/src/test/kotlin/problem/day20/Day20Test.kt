package problem.day20

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day20Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "###############",
        "#...#...#.....#",
        "#.#.#.#.#.###.#",
        "#S#...#.#.#...#",
        "#######.#.#.###",
        "#######.#.#...#",
        "#######.#.###.#",
        "###..E#...#...#",
        "###.#######.###",
        "#...###...#...#",
        "#.#####.#.###.#",
        "#.#...#.#.#...#",
        "#.#.#.#.#.#.###",
        "#...#...#...###",
        "###############",
    )

    val sampleSolutionPart1 = 1

    val sampleSolutionPart2 = 32 + 31 + 29 + 39 + 25 + 23 + 20 + 19 + 12 + 14 + 12 + 22 + 4 + 3

    "Solving day 20" - {
        "part 1 for the sample input should return the correct output" {
            solveDay20Part1(sampleInput1, 64) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay20Part2(sampleInput1, 50) shouldBe sampleSolutionPart2
        }
    }
})
