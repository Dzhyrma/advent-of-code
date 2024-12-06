package problem.day06

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 6
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay06Part1, ::solveDay06Part2)
}

fun solveDay06Part1(input: List<String>): Int {
    val (grid, startPos, startDir) = parseInput(input)
    return when (val result = traverse(grid, startPos, startDir)) {
        is GuardLoops -> error("Guard goes in loops")
        is GuardLeaves -> result.visited.size
    }
}

fun solveDay06Part2(input: List<String>): Int {
    val (grid, startPos, startDir) = parseInput(input)
    return when (val result = traverse(grid, startPos, startDir)) {
        is GuardLoops -> error("Guard goes in loops")
        is GuardLeaves -> result.visited.count { (obstruction, _) ->
            obstruction != startPos && traverse(grid, startPos, startDir, obstruction) == GuardLoops
        }
    }
}

private fun parseInput(input: List<String>): Triple<Array<CharArray>, Pair<Int, Int>, Char> {
    val grid = input.map { it.toCharArray() }.toTypedArray()
    grid.forEachIndexed { y, row ->
        row.forEachIndexed { x, char ->
            if (DIRECTIONS.contains(char)) {
                return Triple(grid, Pair(y, x), grid[y][x])
            }
        }
    }
    error("No start positions found")
}

private fun traverse(
    grid: Array<CharArray>,
    startPos: Pair<Int, Int>,
    startDir: Char,
    obstruction: Pair<Int, Int> = -1 to -1,
): GuardTraverseResult {
    val visited = mutableMapOf<Pair<Int, Int>, MutableSet<Char>>().withDefault { mutableSetOf() }

    var pos = startPos
    var facing = startDir
    visited[pos] = visited.getValue(pos).also { it.add(facing) }

    while (true) {
        val (dy, dx) = DIRECTIONS.getValue(facing)
        val newPos = pos.first + dy to pos.second + dx

        when {
            visited.getValue(newPos).contains(facing) -> return GuardLoops
            newPos.first !in grid.indices || newPos.second !in grid[newPos.first].indices -> break // Guard has left the map
            grid[newPos.first][newPos.second] == '#' || newPos == obstruction -> {
                facing = RIGHT_TURNS.getValue(facing)
            }
            else -> {
                pos = newPos
                visited[pos] = visited.getValue(pos).also { it.add(facing) }
            }
        }
    }

    return GuardLeaves(visited)
}

private sealed interface GuardTraverseResult

private object GuardLoops : GuardTraverseResult

private data class GuardLeaves(val visited: Map<Pair<Int, Int>, MutableSet<Char>>) : GuardTraverseResult

private val DIRECTIONS = mapOf(
    '^' to Pair(-1, 0),
    '>' to Pair(0, 1),
    'v' to Pair(1, 0),
    '<' to Pair(0, -1),
)
private val RIGHT_TURNS = mapOf(
    '^' to '>',
    '>' to 'v',
    'v' to '<',
    '<' to '^',
)
