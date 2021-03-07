package com.example.kotlionstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlionstudy.data.User

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dd = User("dd", 23)
        dd.copy(age = 24)
        //组件函数允许数据类在解构声明中使用：
        val (name, age) = dd
        println("$name,$age")

    }
}