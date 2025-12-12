package problem.day10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day10Test :
    FreeSpec({

        val sampleInput1: List<String> =
            listOf(
                "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}",
                "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}",
                "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}",
            )

        val sampleSolutionPart1 = 7

        val sampleSolutionPart2 = 33

        "Solving day 10" - {
            "part 1 for the sample input should return the correct output" {
                solveDay10Part1(sampleInput1) shouldBe sampleSolutionPart1
            }

            "part 2 for the sample input should return the correct output" {
                solveDay10Part2(sampleInput1) shouldBe sampleSolutionPart2
            }
        }
    })
