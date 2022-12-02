package problem.day12

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day12Test : FreeSpec({

    val simpleSampleInput: List<String> = listOf(
        "start-A",
        "start-b",
        "A-c",
        "A-b",
        "b-d",
        "A-end",
        "b-end",
    )

    val simpleSampleSolutionPart1 = 10

    val simpleSampleSolutionPart2 = 36

    val sampleInput: List<String> = listOf(
        "dc-end",
        "HN-start",
        "start-kj",
        "dc-start",
        "dc-HN",
        "LN-dc",
        "HN-end",
        "kj-sa",
        "kj-HN",
        "kj-dc",
    )

    val sampleSolutionPart1 = 19

    val sampleSolutionPart2 = 103

    val largeSampleInput: List<String> = listOf(
        "fs-end",
        "he-DX",
        "fs-he",
        "start-DX",
        "pj-DX",
        "end-zg",
        "zg-sl",
        "zg-pj",
        "pj-he",
        "RW-he",
        "fs-DX",
        "pj-RW",
        "zg-RW",
        "start-pj",
        "he-WI",
        "zg-he",
        "pj-fs",
        "start-RW",
    )

    val largeSampleSolutionPart1 = 226

    val largeSampleSolutionPart2 = 3509

    "Solving day 12" - {
        "part 1 for the simple sample input should return the correct output" {
            solveDay12Part1(simpleSampleInput) shouldBe simpleSampleSolutionPart1
        }

        "part 2 for the simple sample input should return the correct output" {
            solveDay12Part2(simpleSampleInput) shouldBe simpleSampleSolutionPart2
        }

        "part 1 for the sample input should return the correct output" {
            solveDay12Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay12Part2(sampleInput) shouldBe sampleSolutionPart2
        }

        "part 1 for the large sample input should return the correct output" {
            solveDay12Part1(largeSampleInput) shouldBe largeSampleSolutionPart1
        }

        "part 2 for the large sample input should return the correct output" {
            solveDay12Part2(largeSampleInput) shouldBe largeSampleSolutionPart2
        }
    }
})
