package problem.day20

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day20Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##" +
                "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###" +
                ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#." +
                ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#....." +
                ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.." +
                "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#....." +
                "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#",
        "",
        "#..#.",
        "#....",
        "##..#",
        "..#..",
        "..###",
    )

    val sampleSolutionPart1 = 35

    val sampleSolutionPart2 = 3351

    "Solving day 20" - {
        "part 1 for the sample input should return the correct output" {
            solveDay20Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay20Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
