package common

import java.util.*
import kotlin.system.exitProcess

private const val SESSION_COOKIE_KEY = "sessionCookie"

fun Array<String>.readSessionCookie(): String = try {
    if (this.isNotEmpty()) this[0] else (System.getProperty(SESSION_COOKIE_KEY) ?: readSessionCookieFromProperties()!!)
} catch (_: Exception) {
    println("Please pass your session cookie as a parameter.")
    exitProcess(1)
}

private fun readSessionCookieFromProperties(): String? {
    return object{}.javaClass.classLoader.getResourceAsStream("config.properties")?.use {
        val properties = Properties()
        properties.load(it)
        properties.getProperty(SESSION_COOKIE_KEY)
    }
}
