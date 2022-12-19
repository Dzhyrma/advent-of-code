package problem.day19

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day19Test : FreeSpec({

    val sampleInput: List<String> = """
        Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
        Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
    """.trimIndent().split('\n')

    val sampleSolutionPart1 = 33

    val sampleSolutionPart2 = 3472

    "Solving day 19" - {
        "part 1 for the sample input should return the correct output" {
            solveDay19Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay19Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
