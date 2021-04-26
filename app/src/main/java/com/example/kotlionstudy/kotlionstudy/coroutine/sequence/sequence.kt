package com.example.kotlionstudy.kotlionstudy.coroutine.sequence

import com.example.kotlionstudy.kotlionstudy.coroutine.log

val fibonacci = sequence {
    yield(1L) // first Fibonacci number
    var cur = 1L
    var next = 1L
    while (true) {
        yield(next) // next Fibonacci number
        val tmp = cur + next
        cur = next
        next = tmp
    }
}

fun main() {
    fibonacci.take(5).forEach { log(it) }
}

