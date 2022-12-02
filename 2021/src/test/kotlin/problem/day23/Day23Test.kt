package problem.day23

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day23Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "#############",
        "#...........#",
        "###B#C#B#D###",
        "  #A#D#C#A#  ",
        "  #########  ",
    )

    val sampleSolutionPart1 = 12521

    val sampleSolutionPart2 = 44169

    "Solving day 23" - {
        "part 1 for the sample input should return the correct output" {
            solveDay23Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay23Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
