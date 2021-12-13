package problem.day13

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 13
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay13Part1, ::solveDay13Part2)
}


fun solveDay13Part1(input: List<String>): Int {
    val (paper, folds) = convertInput(input)

    return foldPaper(paper, folds.first().first, folds.first().second).size
}

fun solveDay13Part2(input: List<String>): Int {
    val (paper, folds) = convertInput(input)

    return folds.fold(paper) { foldedPaper, (foldAxis, foldIndex) ->
        foldPaper(foldedPaper, foldAxis, foldIndex)
    }.also {
        repeat(6) { y ->
            repeat(40) { x ->
                print(if (it.contains(x to y)) "##" else "  ")
            }
            println()
        }
    }.size
}

private fun convertInput(input: List<String>): Pair<Set<Pair<Int, Int>>, List<Pair<String, Int>>> {
    return input.indexOf("")
        .let { input.take(it) to input.drop(it + 1) }
        .let { (coordinates, folds) ->
            coordinates.map { line -> line.split(',').map { it.toInt() }.let { it[0] to it[1] } }.toSet() to
                    folds.map { line -> line.drop(11).split("=").let { it[0] to it[1].toInt() } }
        }
}

private fun foldPaper(
    paper: Set<Pair<Int, Int>>,
    foldAxis: String,
    foldIndex: Int,
) = when (foldAxis) {
    "x" -> paper.mapNotNull { (x, y) ->
        when {
            x < foldIndex -> x to y
            x == foldIndex -> null
            else -> (2 * foldIndex - x) to y
        }
    }.toSet()
    "y" -> paper.mapNotNull { (x, y) ->
        when {
            y < foldIndex -> x to y
            y == foldIndex -> null
            else -> x to (2 * foldIndex - y)
        }
    }.toSet()
    else -> error("Wrong input")
}
