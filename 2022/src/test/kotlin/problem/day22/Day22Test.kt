package problem.day22

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day22Test : FreeSpec({

    val sampleInput: List<String> = """
                ...#
                .#..
                #...
                ....
        ...#.......#
        ........#...
        ..#....#....
        ..........#.
                ...#....
                .....#..
                .#......
                ......#.
        
        10R5L5R10L4R5L5
    """.trimIndent().split('\n')

    val sampleSolutionPart1 = 6032

    "Solving day 22" - {
        "part 1 for the sample input should return the correct output" {
            solveDay22Part1(sampleInput) shouldBe sampleSolutionPart1
        }
    }
})
