package problem.day20

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 20
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay20Part1, ::solveDay20Part2)
}

private val DELTA_COORDINATES = listOf(
    1 to 1,
    0 to 1,
    -1 to 1,
    1 to 0,
    0 to 0,
    -1 to 0,
    1 to -1,
    0 to -1,
    -1 to -1,
)

data class Image(
    val invertedPixels: Set<Pair<Int, Int>>,
    val backgroundPixel: Char = '.',
    val invertedPixel: Char = if (backgroundPixel == '.') '#' else '.',
) {
    private val dimensionX = invertedPixels.minOf { it.first } .. invertedPixels.maxOf { it.first }
    private val dimensionY = invertedPixels.minOf { it.second } .. invertedPixels.maxOf { it.second }

    fun enhance(algorithm: String): Image {
        require(algorithm.length >= 512)
        val newInvertedPixels = mutableSetOf<Pair<Int, Int>>()
        val newBackgroundPixel = algorithm[if (backgroundPixel == '.') 0 else 511]

        (dimensionX.first - 1 .. dimensionX.last + 1).forEach { x ->
            (dimensionY.first - 1 .. dimensionY.last + 1).forEach { y ->
                val index = DELTA_COORDINATES.foldIndexed(0) { i, acc, (dx, dy) ->
                    val pixel = if (invertedPixels.contains(x + dx to y + dy)) invertedPixel else backgroundPixel
                    if (pixel == '#') acc or (1 shl i) else acc
                }
                if (algorithm[index] != newBackgroundPixel) newInvertedPixels.add(x to y)
            }
        }
        return Image(newInvertedPixels, newBackgroundPixel)
    }

    override fun toString(): String {
        return buildString {
            dimensionY.forEach { y ->
                dimensionX.forEach { x ->
                    append(if (invertedPixels.contains(x to y)) invertedPixel else backgroundPixel)
                }
                appendLine()
            }
        }
    }
}

fun solveDay20Part1(input: List<String>): Int {
    val decodeString = input.first()

    var image = parseImage(input.drop(2))
    repeat(2) {
        image = image.enhance(decodeString)
    }
    return image.invertedPixels.size
}

fun solveDay20Part2(input: List<String>): Int {
    val decodeString = input.first()

    var image = parseImage(input.drop(2))
    repeat(50) {
        image = image.enhance(decodeString)
    }
    return image.invertedPixels.size
}

private fun parseImage(input: List<String>): Image {
    return Image(
        invertedPixels = input.foldIndexed(mutableSetOf()) { y, acc, line ->
            acc.addAll(line.mapIndexedNotNull { x, char ->
                if (char == '#') x to y else null
            })
            acc
        }
    )
}