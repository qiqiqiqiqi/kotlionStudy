package com.example.kotlionstudy

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.example.kotlionstudy.kotlionstudy.coroutine.log
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), MainScoped {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.scale_map)
//        mainScope手动取消
//        textView.setOnClickListener {
//            mainScope.launch {
//                log(1)
//                textView.text = async(Dispatchers.IO) {
//                    log(2)
//                    delay(10000)
//                    log(3)
//                    "Hello1111"
//                }.await()
//                log(4)
//            }
//        }

//        自动监听界面的生命周期取消协程(新建的协程需要在MainScoped内，确保继承了外部作用域，否则无法自动取消)
//        textView.setOnClickListener {
//            withScope {
//                launch {
//                    log(1)
//                    textView.text = async(Dispatchers.IO) {
//                        log(2)
//                        delay(10000)
//                        log(3)
//                        "Hello1111"
//                    }.await()
//                    log(4)
//                }
//            }
//        }

//      自动监听view的AttachState取消协程（比较灵活，没有作用域限制）
        textView.onClick {
            try {
                log(1)
                textView.text = async(Dispatchers.IO) {
                    log(2)
                    delay(10000)
                    log(3)
                    "Hello1111"
                }.await()
                log(4)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}