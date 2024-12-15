package problem.day15

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 15
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay15Part1, ::solveDay15Part2)
}

fun solveDay15Part1(input: List<String>): Long {
    return calculateGPSCoordinates(input)
}

fun solveDay15Part2(input: List<String>): Long {
    return calculateGPSCoordinates2(input)
}

private fun calculateGPSCoordinates(input: List<String>): Long {
    val map = input.takeWhile { it.contains('#') }.map { it.toCharArray() }
    val moves = input.dropWhile { it.contains('#') }.joinToString("").toCharArray()
    val robot = map.withIndex().flatMap { (y, row) -> row.withIndex().map { (x, c) -> Triple(x, y, c) } }
        .first { it.third == '@' }.let { Pair(it.first, it.second) }
    val boxes = map.withIndex().flatMap { (y, row) ->
        row.withIndex().mapNotNull { (x, c) -> if (c == 'O') x to y else null }
    }.toMutableSet()

    val directions = mapOf('<' to (-1 to 0), '>' to (1 to 0), '^' to (0 to -1), 'v' to (0 to 1))
    var rx = robot.first
    var ry = robot.second

    fun firstEmptySpaceAfterBoxes(x: Int, y: Int, dx: Int, dy: Int): Pair<Int, Int>? {
        var nx = x
        var ny = y
        while (nx to ny in boxes) {
            nx += dx
            ny += dy
            if (map[ny][nx] == '#') return null
        }
        return nx to ny
    }

    for (move in moves) {
        val (dx, dy) = directions[move]!!
        val (nx, ny) = rx + dx to ry + dy
        if (map[ny][nx] == '#') continue
        if (nx to ny in boxes) {
            val e = firstEmptySpaceAfterBoxes(nx, ny, dx, dy)
            if (e != null) {
                val (cx, cy) = e
                boxes.add(cx to cy)
                boxes.remove(nx to ny)
                rx = nx
                ry = ny
            }
        } else {
            rx = nx
            ry = ny
        }
    }
    map.forEachIndexed { y, chars ->
        chars.forEachIndexed { x, c ->
            if (x to y in boxes) {
                print('O')
            } else if (x to y == rx to ry) {
                print('@')
            } else if (c == '#') {
                print(c)
            } else {
                print('.')
            }
        }
        println()
    }
    return boxes.sumOf { (x, y) -> (y * 100 + x) }.toLong()
}

private fun calculateGPSCoordinates2(input: List<String>): Long {
    val originalMap = input.takeWhile { it.contains('#') }
    val moves = input.dropWhile { it.contains('#') }.joinToString("")
    val newMap = originalMap.map { row ->
        row.flatMap { c ->
            when (c) {
                '#' -> "##".toList()
                'O' -> "[]".toList()
                '.' -> "..".toList()
                '@' -> "@.".toList()
                else -> emptyList()
            }
        }.joinToString("").toCharArray()
    }

    val robot = newMap.withIndex().flatMap { (y, row) ->
        row.withIndex().mapNotNull { (x, c) -> if (c == '@') x to y else null }
    }.first()
    val boxes = newMap.withIndex().flatMap { (y, row) ->
        row.withIndex().mapNotNull { (x, c) -> if (row.getOrNull(x + 1) == ']' && c == '[') x to y else null }
    }.toMutableSet()

    val directions = mapOf('<' to (-1 to 0), '>' to (1 to 0), '^' to (0 to -1), 'v' to (0 to 1))
    var rx = robot.first
    var ry = robot.second

    fun canPushBoxes(x: Int, y: Int, dx: Int, dy: Int): Boolean {
        return when {
            newMap[y + dy][x + dx] == '#' || newMap[y + dy][x + dx + 1] == '#' -> false
            (dy == 0) && x + 2 * dx to y in boxes -> canPushBoxes(x + 2 * dx, y, dx, 0)
            (dy == 0) -> true
            x to y + dy in boxes -> canPushBoxes(x, y + dy, 0, dy)
            x - 1 to y + dy in boxes && x + 1 to y + dy in boxes -> canPushBoxes(x - 1, y + dy, 0, dy) &&
                canPushBoxes(x + 1, y + dy, 0, dy)
            x - 1 to y + dy in boxes -> canPushBoxes(x - 1, y + dy, 0, dy)
            x + 1 to y + dy in boxes -> canPushBoxes(x + 1, y + dy, 0, dy)
            else -> true
        }
    }

    fun pushBoxes(x: Int, y: Int, dx: Int, dy: Int) {
        if ((dy == 0) && x + 2 * dx to y in boxes) pushBoxes(x + 2 * dx, y, dx, 0)
        if (dy != 0) {
            if (x to y + dy in boxes) pushBoxes(x, y + dy, 0, dy)
            if (x - 1 to y + dy in boxes) pushBoxes(x - 1, y + dy, 0, dy)
            if (x + 1 to y + dy in boxes) pushBoxes(x + 1, y + dy, 0, dy)
        }
        boxes.remove(x to y)
        boxes.add(x + dx to y + dy)
    }

    for (move in moves) {
        val (dx, dy) = directions[move]!!
        val (nx, ny) = rx + dx to ry + dy
        if (newMap[ny][nx] == '#') continue
        when {
            (nx to ny in boxes) && canPushBoxes(nx, ny, dx, dy) -> pushBoxes(nx, ny, dx, dy)
            dx == -1 && (nx - 1 to ny in boxes) && canPushBoxes(nx - 1, ny, -1, 0) -> pushBoxes(nx - 1, ny, -1, 0)
            (dy != 0) && (nx - 1) to ny in boxes && canPushBoxes(nx - 1, ny, 0, dy) -> pushBoxes(nx - 1, ny, 0, dy)
        }
        if (nx to ny !in boxes && nx - 1 to ny !in boxes) {
            rx = nx
            ry = ny
        }
    }

    var sum = 0
    newMap.forEachIndexed { y, chars ->
        chars.forEachIndexed { x, c ->
            if (x to y == rx to ry) {
                print('@')
            } else if (x to y in boxes) {
                sum += y * 100 + x
                print('[')
            } else if (x - 1 to y in boxes) {
                print(']')
            } else if (c == '#') {
                print('#')
            } else {
                print('.')
            }
        }
        println()
    }
    return sum.toLong()
}
