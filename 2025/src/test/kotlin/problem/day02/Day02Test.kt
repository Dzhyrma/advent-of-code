package problem.day02

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day02Test :
    FreeSpec({

        val sampleInput1: List<String> =
            listOf(
                "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124",
            )

        val sampleSolutionPart1 = 1227775554

        val sampleSolutionPart2 = 4174379265

        "Solving day 2" - {
            "part 1 for the sample input should return the correct output" {
                solveDay02Part1(sampleInput1) shouldBe sampleSolutionPart1
            }

            "part 2 for the sample input should return the correct output" {
                solveDay02Part2(sampleInput1) shouldBe sampleSolutionPart2
            }
        }
    })
