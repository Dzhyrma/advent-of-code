package problem.day01

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day01Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "1abc2",
        "pqr3stu8vwx",
        "a1b2c3d4e5f,",
        "treb7uchet",
    )

    val sampleInput2: List<String> = listOf(
        "two1nine",
        "eightwothree",
        "abcone2threexyz",
        "xtwone3four",
        "4nineeightseven2",
        "zoneight234",
        "7pqrstsixteen",
    )

    val sampleSolutionPart1 = 142

    val sampleSolutionPart2 = 281

    "Solving day 1" - {
        "part 1 for the sample input should return the correct output" {
            solveDay01Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay01Part2(sampleInput2) shouldBe sampleSolutionPart2
        }
    }
})
