package problem.day12

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 12
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay12Part1, ::solveDay12Part2)
}

fun solveDay12Part1(input: List<String>): Int {
    val rows = input.size
    val cols = input[0].length
    val visited = Array(rows) { BooleanArray(cols) }
    var totalPrice = 0

    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

    fun isInBounds(x: Int, y: Int): Boolean {
        return x in 0 until rows && y in 0 until cols
    }

    fun dfs(x: Int, y: Int, type: Char): Pair<Int, Int> {
        val stack = mutableListOf(Pair(x, y))
        var area = 0
        var perimeter = 0

        while (stack.isNotEmpty()) {
            val (cx, cy) = stack.removeLast()

            if (visited[cx][cy]) continue
            visited[cx][cy] = true
            area++

            for ((dx, dy) in directions) {
                val nx = cx + dx
                val ny = cy + dy

                if (!isInBounds(nx, ny) || input[nx][ny] != type) {
                    perimeter++
                } else if (!visited[nx][ny]) {
                    stack.add(Pair(nx, ny))
                }
            }
        }

        return Pair(area, perimeter)
    }

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (!visited[i][j]) {
                val type = input[i][j]
                val (area, perimeter) = dfs(i, j, type)
                totalPrice += area * perimeter
            }
        }
    }

    return totalPrice
}

fun solveDay12Part2(map: List<String>): Int {
    val rows = map.size
    val cols = map[0].length
    val visited = Array(rows) { BooleanArray(cols) }
    var totalPrice = 0

    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

    fun isInBounds(x: Int, y: Int): Boolean {
        return x in 0 until rows && y in 0 until cols
    }

    fun Map<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>.calculateSides(): Int {
        var count = 0
        val sideMap = mutableMapOf<Pair<Int, Int>, MutableMap<Pair<Int, Int>, Int>>().withDefault { mutableMapOf() }
        entries.forEach { (side, direction) ->
            val (x, y) = side
            for ((dx, dy) in directions) {
                val nx = x + dx
                val ny = y + dy
                val intDirections = direction.intersect(this.getValue(nx to ny))
                val sideMapV = sideMap[nx to ny]
                if (intDirections.isNotEmpty() && sideMapV != null && sideMapV.keys.intersect(intDirections)
                        .isNotEmpty()
                ) {
                    sideMap[x to y] = sideMap.getValue(x to y).also { mapOfDirections ->
                        intDirections.forEach {
                            if (sideMapV.containsKey(it)) {
                                if (mapOfDirections.contains(it)) {
                                    count--
                                } else {
                                    mapOfDirections[it] = sideMapV.getValue(it)
                                }
                            }
                        }
                    }
                }
            }
            sideMap[x to y] = sideMap.getValue(x to y).also { mapOfDirections ->
                this.getValue(x to y).forEach { direction ->
                    if (!mapOfDirections.contains(direction)) {
                        mapOfDirections[direction] = count++
                    }
                }
            }
        }
        return count
    }

    fun dfs(x: Int, y: Int, type: Char): Pair<Int, Int> {
        val stack = mutableListOf(Pair(x, y))
        val perimeter = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>().withDefault { mutableSetOf() }
        var area = 0

        while (stack.isNotEmpty()) {
            val (cx, cy) = stack.removeLast()

            if (visited[cx][cy]) continue
            visited[cx][cy] = true
            area++

            for ((dx, dy) in directions) {
                val nx = cx + dx
                val ny = cy + dy

                if (!isInBounds(nx, ny) || map[nx][ny] != type) {
                    perimeter[Pair(nx, ny)] = perimeter.getValue(nx to ny).also { it.add(dx to dy) }
                } else if (!visited[nx][ny]) {
                    stack.add(Pair(nx, ny))
                }
            }
        }

        return Pair(area, perimeter.calculateSides())
    }

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (!visited[i][j]) {
                val type = map[i][j]
                val (area, sides) = dfs(i, j, type)
                println("$type: ($area, $sides)")
                totalPrice += area * sides
            }
        }
    }

    return totalPrice
}
