package problem.day08

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day08Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "RL",
        "",
        "AAA = (BBB, CCC)",
        "BBB = (DDD, EEE)",
        "CCC = (ZZZ, GGG)",
        "DDD = (DDD, DDD)",
        "EEE = (EEE, EEE)",
        "GGG = (GGG, GGG)",
        "ZZZ = (ZZZ, ZZZ)",
    )

    val sampleInput2: List<String> = listOf(
        "LR",
        "",
        "11A = (11B, XXX)",
        "11B = (XXX, 11Z)",
        "11Z = (11B, XXX)",
        "22A = (22B, XXX)",
        "22B = (22C, 22C)",
        "22C = (22Z, 22Z)",
        "22Z = (22B, 22B)",
        "XXX = (XXX, XXX)",
    )

    val sampleSolutionPart1 = 2

    val sampleSolutionPart2 = 6

    "Solving day 8" - {
        "part 1 for the sample input should return the correct output" {
            solveDay08Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay08Part2(sampleInput2) shouldBe sampleSolutionPart2
        }
    }
})
