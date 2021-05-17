package com.mathpresso.nunu.crashrxandroid

import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `구구단 만들기 with Observable`() {
        val myNumber = 7
        Observable.fromIterable((1..9))
            .map { it -> println("$it * $myNumber = ${it * myNumber}") }
            .subscribe()

        Observable.just(myNumber + 1)
            .flatMap { it1 ->
                Observable.range(1, 9).map { it2 ->
                    "$it1 * $it2 = ${it1 * it2}"
                }
            }.subscribe { println(it) }
    }
}