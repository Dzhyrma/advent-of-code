package problem.day25

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day25Test : FreeSpec({

    val sampleInput: List<String> = """
        1=-0-2
        12111
        2=0=
        21
        2=01
        111
        20012
        112
        1=-1=
        1-12
        12
        1=
        122
    """.trimIndent().split('\n')

    val sampleSolutionPart1 = "2=-1=0"

    "Solving day 25" - {
        "part 1 for the sample input should return the correct output" {
            solveDay25Part1(sampleInput) shouldBe sampleSolutionPart1
        }
    }
})
