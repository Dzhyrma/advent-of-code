package problem.day13

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day13Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "Button A: X+94, Y+34",
        "Button B: X+22, Y+67",
        "Prize: X=8400, Y=5400",
        "",
        "Button A: X+26, Y+66",
        "Button B: X+67, Y+21",
        "Prize: X=12748, Y=12176",
        "",
        "Button A: X+17, Y+86",
        "Button B: X+84, Y+37",
        "Prize: X=7870, Y=6450",
        "",
        "Button A: X+69, Y+23",
        "Button B: X+27, Y+71",
        "Prize: X=18641, Y=10279",
    )

    val sampleSolutionPart1 = 480

    val sampleSolutionPart2 = 875318608908L

    "Solving day 13" - {
        "part 1 for the sample input should return the correct output" {
            solveDay13Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay13Part2(sampleInput1) shouldBe sampleSolutionPart2
        }
    }
})
