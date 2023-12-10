package problem.day10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day10Test : FreeSpec({

    val sampleInput1: List<String> = listOf(
        ".....",
        ".S-7.",
        ".|.|.",
        ".L-J.",
        ".....",
    )
    val sampleInput2: List<String> = listOf(
        "..F7.",
        ".FJ|.",
        "SJ.L7",
        "|F--J",
        "LJ...",
    )
    val sampleInput3: List<String> = listOf(
        "..........",
        ".S------7.",
        ".|F----7|.",
        ".||....||.",
        ".||....||.",
        ".|L-7F-J|.",
        ".|..||..|.",
        ".L--JL--J.",
        "..........",
    )
    val sampleInput4: List<String> = listOf(
        ".F----7F7F7F7F-7....",
        ".|F--7||||||||FJ....",
        ".||.FJ||||||||L7....",
        "FJL7L7LJLJ||LJ.L-7..",
        "L--J.L7...LJS7F-7L7.",
        "....F-J..F7FJ|L7L7L7",
        "....L7.F7||L7|.L7L7|",
        ".....|FJLJ|FJ|F7|.LJ",
        "....FJL-7.||.||||...",
        "....L---J.LJ.LJLJ...",
    )
    val sampleInput5: List<String> = listOf(
        "FF7FSF7F7F7F7F7F---7",
        "L|LJ||||||||||||F--J",
        "FL-7LJLJ||||||LJL-77",
        "F--JF--7||LJLJ7F7FJ-",
        "L---JF-JLJ.||-FJLJJ7",
        "|F|F-JF---7F7-L7L|7|",
        "|FFJF7L7F-JF7|JL---7",
        "7-L-JL7||F7|L7F-7F7|",
        "L.L7LFJ|||||FJL7||LJ",
        "L7JLJL-JLJLJL--JLJ.L",
    )

    val sample1SolutionPart1 = 4
    val sample2SolutionPart1 = 8

    val sample1SolutionPart2 = 1
    val sample3SolutionPart2 = 4
    val sample4SolutionPart2 = 8
    val sample5SolutionPart2 = 10

    "Solving day 10" - {
        "part 1 for the sample input should return the correct output" {
            solveDay10Part1(sampleInput1) shouldBe sample1SolutionPart1
            solveDay10Part1(sampleInput2) shouldBe sample2SolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay10Part2(sampleInput1) shouldBe sample1SolutionPart2
            solveDay10Part2(sampleInput3) shouldBe sample3SolutionPart2
            solveDay10Part2(sampleInput4) shouldBe sample4SolutionPart2
            solveDay10Part2(sampleInput5) shouldBe sample5SolutionPart2
        }
    }
})
