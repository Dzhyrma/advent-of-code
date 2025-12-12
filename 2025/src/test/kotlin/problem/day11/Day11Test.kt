package problem.day11

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day11Test :
    FreeSpec({

        val sampleInput1: List<String> =
            listOf(
                "aaa: you hhh",
                "you: bbb ccc",
                "bbb: ddd eee",
                "ccc: ddd eee fff",
                "ddd: ggg",
                "eee: out",
                "fff: out",
                "ggg: out",
                "hhh: ccc fff iii",
                "iii: out",
            )

        val sampleInput2: List<String> =
            listOf(
                "svr: aaa bbb",
                "aaa: fft",
                "fft: ccc",
                "bbb: tty",
                "tty: ccc",
                "ccc: ddd eee",
                "ddd: hub",
                "hub: fff",
                "eee: dac",
                "dac: fff",
                "fff: ggg hhh",
                "ggg: out",
                "hhh: out",
            )

        val sampleSolutionPart1 = 5L

        val sampleSolutionPart2 = 2L

        "Solving day 11" - {
            "part 1 for the sample input should return the correct output" {
                solveDay11Part1(sampleInput1) shouldBe sampleSolutionPart1
            }

            "part 2 for the sample input should return the correct output" {
                solveDay11Part2(sampleInput2) shouldBe sampleSolutionPart2
            }
        }
    })
