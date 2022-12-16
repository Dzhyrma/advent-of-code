package problem.day16

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day16Test : FreeSpec({

    val sampleInput: List<String> = """
        Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        Valve BB has flow rate=13; tunnels lead to valves CC, AA
        Valve CC has flow rate=2; tunnels lead to valves DD, BB
        Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
        Valve EE has flow rate=3; tunnels lead to valves FF, DD
        Valve FF has flow rate=0; tunnels lead to valves EE, GG
        Valve GG has flow rate=0; tunnels lead to valves FF, HH
        Valve HH has flow rate=22; tunnel leads to valve GG
        Valve II has flow rate=0; tunnels lead to valves AA, JJ
        Valve JJ has flow rate=21; tunnel leads to valve II
    """.trimIndent().split("\n")

    val sampleSolutionPart1 = 1651

    val sampleSolutionPart2 = 1707

    "Solving day 16" - {
        "part 1 for the sample input should return the correct output" {
            solveDay16Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay16Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }
})
