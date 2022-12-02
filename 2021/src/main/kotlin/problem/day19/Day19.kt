package problem.day19

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.abs

fun main(args: Array<String>) {
    val day = 19
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay19Part1, ::solveDay19Part2)
}

data class Vector(
    val x: Int,
    val y: Int,
    val z: Int,
) {

    operator fun plus(other: Vector): Vector {
        return Vector(x + other.x, y + other.y, z + other.z)
    }

    operator fun minus(other: Vector): Vector {
        return Vector(x - other.x, y - other.y, z - other.z)
    }

    fun rotate(type: Int): Vector {
        return when (type) {
            0 -> this
            1 -> Vector(+y, -x, +z)
            2 -> Vector(-x, -y, +z)
            3 -> Vector(-y, +x, +z)
            4 -> Vector(-x, +y, -z)
            5 -> Vector(+y, +x, -z)
            6 -> Vector(+x, -y, -z)
            7 -> Vector(-y, -x, -z)
            8 -> Vector(-z, +y, +x)
            9 -> Vector(+y, +z, +x)
            10 -> Vector(+z, -y, +x)
            11 -> Vector(-y, -z, +x)
            12 -> Vector(+z, +y, -x)
            13 -> Vector(+y, -z, -x)
            14 -> Vector(-z, -y, -x)
            15 -> Vector(-y, +z, -x)
            16 -> Vector(+x, -z, +y)
            17 -> Vector(-z, -x, +y)
            18 -> Vector(-x, +z, +y)
            19 -> Vector(+z, +x, +y)
            20 -> Vector(-x, -z, -y)
            21 -> Vector(-z, +x, -y)
            22 -> Vector(+x, +z, -y)
            23 -> Vector(+z, -x, -y)
            else -> error("Wron rotation type")
        }
    }
}

fun solveDay19Part1(input: List<String>): Int {
    val scanners = parseInput(input)
    findScannerCoordinaates(scanners)
    return scanners[0].size
}

fun solveDay19Part2(input: List<String>): Int {
    val scanners = parseInput(input)
    val scannerCoordinates = findScannerCoordinaates(scanners)
    return scannerCoordinates.dropLast(1).mapIndexed { index, coord1 -> index to coord1 }.maxOf { (index, coord1) ->
        scannerCoordinates.drop(index + 1).maxOf { coord2 ->
            abs(coord1.x - coord2.x) + abs(coord1.y - coord2.y) + abs(coord1.z - coord2.z)
        }
    }
}

private fun findScannerCoordinaates(scanners: MutableList<Set<Vector>>): List<Vector> {
    val scannerCoordinates = mutableListOf(Vector(0, 0, 0))

    while (scanners.size > 1) {
        lateinit var scannerCoordinate: Vector
        lateinit var rotatedVectors: MutableSet<Vector>
        var otherVectorsIndex: Int = -1
        val matchFound = scanners.mapIndexed { index, vectors -> index to vectors }.find { (i, vectors1) ->
            scanners.drop(i + 1).any { vectors2 ->
                (0..23).any { rotationType ->
                    rotatedVectors = vectors2.map { it.rotate(rotationType) }.toMutableSet()
                    rotatedVectors.any { rotatedBaseVector ->
                        vectors1.find { baseVector ->
                            scannerCoordinate = baseVector - rotatedBaseVector
                            (rotatedVectors.map { it + scannerCoordinate }.toSet() intersect vectors1).size >= 12
                        } != null
                    }
                }.also {
                    if (it) {
                        otherVectorsIndex = scanners.indexOf(vectors2)
                    }
                }
            }
        }
        if (matchFound != null) {
            val (index, vectors1) = matchFound
            val newVectors = rotatedVectors.map { it + scannerCoordinate }.toMutableSet().also { it.addAll(vectors1) }
            scanners[index] = newVectors
            scanners.removeAt(otherVectorsIndex)
            scannerCoordinates.add(scannerCoordinate)
        } else {
            error("Couldn't find matched scanners")
        }
    }
    return scannerCoordinates
}

fun parseInput(input: List<String>): MutableList<Set<Vector>> {
    val result = mutableListOf<Set<Vector>>()
    var lines = input.drop(1)
    do {
        result.add(lines.takeWhile { it.isNotEmpty() }.map { line ->
            line.split(',').map { it.toInt() }.let {
                Vector(it[0], it[1], it[2])
            }
        }.toMutableSet())
        lines = lines.drop(result.last().size + 2)
    } while (lines.isNotEmpty())
    return result
}