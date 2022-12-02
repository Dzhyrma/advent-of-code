package problem.day16

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day16Test : FreeSpec({

    val sampleInputsPart1: List<List<String>> = listOf(
        listOf("8A004A801A8002F478"),
        listOf("620080001611562C8802118E34"),
        listOf("C0015000016115A2E0802F182340"),
        listOf("A0016C880162017C3686B18A3D4780"),
    )

    val sampleSolutionsPart1 = listOf(16, 12, 23, 31)

    val sampleInputsPart2: List<List<String>> = listOf(
        listOf("C200B40A82"),
        listOf("04005AC33890"),
        listOf("880086C3E88112"),
        listOf("CE00C43D881120"),
        listOf("D8005AC2A8F0"),
        listOf("F600BC2D8F"),
        listOf("9C005AC2F8F0"),
        listOf("9C0141080250320F1802104A08"),
    )

    val sampleSolutionsPart2 = listOf(3, 54, 7, 9, 1, 0, 0, 1)

    "Solving day 16" - {
        "part 1 for the sample input should return the correct output" {
            sampleInputsPart1.forEachIndexed { index, input ->
                solveDay16Part1(input) shouldBe sampleSolutionsPart1[index]
            }
        }

        "part 2 for the sample input should return the correct output" {
            sampleInputsPart2.forEachIndexed { index, input ->
                solveDay16Part2(input) shouldBe sampleSolutionsPart2[index]
            }
        }
    }
})
