package problem.day16

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.LinkedList
import java.util.PriorityQueue

fun main(args: Array<String>) {
    val day = 16
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay16Part1, ::solveDay16Part2)
}

fun solveDay16Part1(input: List<String>): Int {
    return dijkstra(input).first
}

fun solveDay16Part2(input: List<String>): Int {
    return dijkstra(input).second
}

private fun dijkstra(input: List<String>): Pair<Int, Int> {
    val maze = input.map { it.toCharArray() }
    val directions = listOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1))
    var start = Pair(0, 0)
    var end = Pair(0, 0)
    maze.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            when (c) {
                'S' -> start = Pair(x, y)
                'E' -> end = Pair(x, y)
            }
        }
    }

    var minCost = Int.MAX_VALUE
    val costMap = Array(4) { Array(maze.size) { IntArray(maze.first().size) { Int.MAX_VALUE } } }
    val pq = PriorityQueue(compareBy<Pair<Int, State>> { it.first })
    pq.add(0 to State(start.first, start.second, 0))

    while (pq.isNotEmpty()) {
        val (cost, state) = pq.poll()
        val (x, y, dir) = state
        if (costMap[dir][x][y] < Int.MAX_VALUE) continue
        costMap[dir][x][y] = cost
        maze[y][x] = 'O'
        if (Pair(x, y) == end) {
            if (cost > minCost) break
            minCost = cost
        }
        directions.forEachIndexed { d, (dx, dy) ->
            if (d == dir) {
                val nx = x + dx
                val ny = y + dy
                if (maze.getOrNull(ny)?.getOrNull(nx) != '#') pq.add(cost + 1 to State(nx, ny, dir))
            } else {
                pq.add(cost + 1000 to State(x, y, d))
            }
        }
    }

    if (costMap.all { it[end.first][end.second] == Int.MAX_VALUE }) return Int.MAX_VALUE to 0
    val visited = mutableSetOf<State>()
    val queue = LinkedList<State>()
    costMap.forEachIndexed { dir, map ->
        if (map[end.first][end.second] == minCost) {
            visited.add(State(end.first, end.second, dir))
            queue.add(State(end.first, end.second, dir))
        }
    }
    while (queue.isNotEmpty()) {
        val state = queue.remove()
        directions.forEachIndexed { dir, (dx, dy) ->
            val n = State(state.x - dx, state.y - dy, dir)
            if (n.y in maze.indices && n.x in maze[n.y].indices && !visited.contains(n)) {
                if ((state.dir == dir && costMap[dir][n.x][n.y] + 1 == costMap[dir][state.x][state.y]) ||
                    (state.dir != dir && costMap[dir][n.x][n.y] + 1001 == costMap[state.dir][state.x][state.y])
                ) {
                    visited.add(n)
                    queue.add(n)
                }
            }
        }
    }
    return minCost to visited.map { it.x to it.y }.toSet().size
}

private data class State(val x: Int, val y: Int, val dir: Int)
