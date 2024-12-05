package problem.day03

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import problem.day03.solveDay03Part1
import problem.day03.solveDay03Part2

class Day03Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))",
    )

    val sampleInput2: List<String> = listOf(
        "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))",
    )

    val sampleSolutionPart1 = 161

    val sampleSolutionPart2 = 48

    "Solving day 3" - {
        "part 1 for the sample input should return the correct output" {
            solveDay03Part1(sampleInput1) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay03Part2(sampleInput2) shouldBe sampleSolutionPart2
        }
    }
})
