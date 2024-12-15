package problem.day14

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day14Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        "p=0,4 v=3,-3",
        "p=6,3 v=-1,-3",
        "p=10,3 v=-1,2",
        "p=2,0 v=2,-1",
        "p=0,0 v=1,3",
        "p=3,0 v=-2,-2",
        "p=7,6 v=-1,-3",
        "p=3,0 v=-1,-2",
        "p=9,3 v=2,3",
        "p=7,3 v=-1,2",
        "p=2,4 v=2,-3",
        "p=9,5 v=-3,-3",
    )

    val sampleSolutionPart1 = 12

    "Solving day 14" - {
        "part 1 for the sample input should return the correct output" {
            solveDay14Part1(sampleInput1, 11, 7) shouldBe sampleSolutionPart1
        }
    }
})
