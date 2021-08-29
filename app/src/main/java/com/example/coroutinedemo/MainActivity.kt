package com.example.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.coroutinedemo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var btnCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnClick.setOnClickListener {
                tvCount.text = (++btnCount).toString()
            }
            btnDownload.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {
                    /*
                    https://developer.android.com/reference/android/os/Looper#:~:text=android.os.Looper,until%20the%20loop%20is%20stopped.
                    UI thread가 아닌 곳에서 UI 호출을 시도하면 "Can’t create handler inside thread that has not called Looper.prepare()" 발생,
                    Looper - Message loop를 실행시키기 위해 사용됨.
                    */

                    download()
                    Looper.prepare()
                    Toast.makeText(baseContext, "Job Finished", Toast.LENGTH_SHORT).show()
                    Looper.loop()

                }
                // download() // It will start at main thread
            }
        }
    }

    private suspend fun download() {
        for ( i in 0 .. 1000) {
            Log.e("TAG", "Downloading user $i in ${Thread.currentThread().name}")
            // Coroutine은 withContext() 함수로 쉽게 쓰레드 변경을 할 수 있다.
            withContext(Dispatchers.Main) {
                // UI에 text를 사용하기 위해 쓰레드 변경
                binding.tvUserMessage.text = "Downloading user $i in ${Thread.currentThread().name}"
            }
        }
    }
}