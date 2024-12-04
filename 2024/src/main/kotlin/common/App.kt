@file:JvmName("App")

package common

import kotlin.system.exitProcess

fun main() {

    val day: Int = try { System.getProperty("day").toInt() } catch (_: Exception) {
        println("Please specify which day should be executed by adding '-Dday=1' to your run command.")
        exitProcess(1)
    }

    val sessionCookie: String = try { System.getProperty("sessionCookie") } catch (_: Exception) {
        println("Please set the session cookie in the gradle.properties file or by adding '-DsessionCookie=yourCookie' to your run command.")
        exitProcess(1)
    }

    when (day) {
        1 -> problem.day01.main(arrayOf(sessionCookie))
        2 -> problem.day02.main(arrayOf(sessionCookie))
        3 -> problem.day02.main(arrayOf(sessionCookie))
        4 -> problem.day02.main(arrayOf(sessionCookie))
        else -> println("Invalid day.")
    }
}
