package com.example.coroutinedemo

import kotlinx.coroutines.*

class UserDataManager1 {
    suspend fun getTotalUserCount(): Int {
        var count = 0
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            count += 50 // 새로운 코루틴 생성으로 결과가 안들어감
        }

        val deferred = CoroutineScope(Dispatchers.IO).async {
            delay(3000)
            return@async 80
        }

        /*
        처음에 기존 count값 0을 반환하고 3초 후 deferred 값을 반환
        그래도 Unstructured Concurrency에 대해서는 괜찮지 않음
        Unstructured Concurrency에서는 에러 예외처리 할 방법이 없다.
        그렇기때문에 Unstructured Concurrency는 추천하지 않는다.
         */
        return count + deferred.await()
    }
}