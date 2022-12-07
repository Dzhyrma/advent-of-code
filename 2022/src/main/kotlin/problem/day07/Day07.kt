package problem.day07

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 7
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay07Part1, ::solveDay07Part2)
}

data class Directory(val name: String, val parent: Directory? = null) {
    val files = mutableListOf<File>()
    val directories = mutableListOf<Directory>()

    val allSubDirectories: List<Directory>
        get() = directories.fold(directories.toList()) { acc, directory -> acc + directory.allSubDirectories }

    fun totalSize(): Int = files.sumOf { it.size } + directories.sumOf { it.totalSize() }
}

data class File(val name: String, val size: Int)

fun solveDay07Part1(input: List<String>): Int {
    val root = Directory("/")

    process(input, root)

    return root.allSubDirectories.map { it.totalSize() }.filter { it <= 100000 }.sum()
}

fun solveDay07Part2(input: List<String>): Int {
    val root = Directory("/")

    process(input, root)

    val unusedSpace = 70000000 - root.totalSize()
    val requiredSpace = 30000000

    return root.allSubDirectories.map { it.totalSize() }.filter { it >= requiredSpace - unusedSpace }.min()
}

private fun process(input: List<String>, root: Directory) {
    var current = root

    input.forEach { line ->
        val words = line.split(" ")

        when {
            line.startsWith("$") && words[1] == "cd" -> {
                current = when (words[2]) {
                    ".." -> current.parent ?: root
                    "/" -> root
                    else -> current.directories.first { it.name == words[2] }
                }
            }

            line.startsWith("$") && words[1] == "ls" -> {}
            words[0] == "dir" -> current.directories.add(Directory(words[1], current))
            else -> current.files.add(File(words[1], words[0].toInt()))
        }
    }
}
