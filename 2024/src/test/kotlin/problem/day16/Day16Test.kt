package problem.day16

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day16Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "###############",
        "#.......#....E#",
        "#.#.###.#.###.#",
        "#.....#.#...#.#",
        "#.###.#####.#.#",
        "#.#.#.......#.#",
        "#.#.#####.###.#",
        "#...........#.#",
        "###.#.#####.#.#",
        "#...#.....#.#.#",
        "#.#.#.###.#.#.#",
        "#.....#...#.#.#",
        "#.###.#.#.#.#.#",
        "#S..#.....#...#",
        "###############",
    )
    val sampleInput2: List<String> = listOf(
        "#################",
        "#...#...#...#..E#",
        "#.#.#.#.#.#.#.#.#",
        "#.#.#.#...#...#.#",
        "#.#.#.#.###.#.#.#",
        "#...#.#.#.....#.#",
        "#.#.#.#.#.#####.#",
        "#.#...#.#.#.....#",
        "#.#.#####.#.###.#",
        "#.#.#.......#...#",
        "#.#.###.#####.###",
        "#.#.#...#.....#.#",
        "#.#.#.#####.###.#",
        "#.#.#.........#.#",
        "#.#.#.#########.#",
        "#S#.............#",
        "#################",
    )

    val sampleSolutionPart1_1 = 7036
    val sampleSolutionPart1_2 = 11048

    val sampleSolutionPart2_1 = 45
    val sampleSolutionPart2_2 = 64

    "Solving day 16" - {
        "part 1 for the sample input should return the correct output" {
            solveDay16Part1(sampleInput1) shouldBe sampleSolutionPart1_1
            solveDay16Part1(sampleInput2) shouldBe sampleSolutionPart1_2
        }

        "part 2 for the sample input should return the correct output" {
            solveDay16Part2(sampleInput1) shouldBe sampleSolutionPart2_1
            solveDay16Part2(sampleInput2) shouldBe sampleSolutionPart2_2
        }
    }
})
