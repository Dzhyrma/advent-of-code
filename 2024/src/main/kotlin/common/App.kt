@file:JvmName("App")

package common

import kotlin.system.exitProcess

fun main() {
    val day: Int = try {
        System.getProperty("day").toInt()
    } catch (_: Exception) {
        println("Please specify which day should be executed by adding '-Dday=1' to your run command.")
        exitProcess(1)
    }

    val sessionCookie: String = try {
        System.getProperty("sessionCookie")
    } catch (_: Exception) {
        println(
            "Please set the session cookie in the gradle.properties file or by adding '-DsessionCookie=yourCookie' to your run command.",
        )
        exitProcess(1)
    }

    when (day) {
        1 -> problem.day01.main(arrayOf(sessionCookie))
        2 -> problem.day02.main(arrayOf(sessionCookie))
        3 -> problem.day03.main(arrayOf(sessionCookie))
        4 -> problem.day04.main(arrayOf(sessionCookie))
        5 -> problem.day05.main(arrayOf(sessionCookie))
        6 -> problem.day06.main(arrayOf(sessionCookie))
        7 -> problem.day07.main(arrayOf(sessionCookie))
        8 -> problem.day08.main(arrayOf(sessionCookie))
        9 -> problem.day09.main(arrayOf(sessionCookie))
        10 -> problem.day10.main(arrayOf(sessionCookie))
        11 -> problem.day11.main(arrayOf(sessionCookie))
        12 -> problem.day12.main(arrayOf(sessionCookie))
        13 -> problem.day13.main(arrayOf(sessionCookie))
        14 -> problem.day14.main(arrayOf(sessionCookie))
        15 -> problem.day15.main(arrayOf(sessionCookie))
        16 -> problem.day16.main(arrayOf(sessionCookie))
        17 -> problem.day17.main(arrayOf(sessionCookie))
        else -> println("Invalid day.")
    }
}
