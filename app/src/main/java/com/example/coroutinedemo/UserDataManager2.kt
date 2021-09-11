package com.example.coroutinedemo

import kotlinx.coroutines.*

class UserDataManager2 {
    // coroutineScope : suspend fun
    // CoroutineScope : interface
    var count = 0
    lateinit var deferred: Deferred<Int>
    suspend fun getTotalUserCount(): Int {
        /*
        coroutineScope는 값을 return 하기 전에 Child Scope가 끝나는 것을 보장한다.
        콜러에게 return을 하기 전에 scope내의 coroutine을 다 실행시킨다.
         */
        coroutineScope {
            launch(Dispatchers.IO) {
                delay(1000)
                count += 50
            }

            deferred = async(Dispatchers.IO) {
                delay(3000)
                return@async 100
            }
        }

        return count + deferred.await()
    }
}