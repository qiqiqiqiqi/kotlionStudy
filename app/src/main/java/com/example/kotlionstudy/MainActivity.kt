package com.example.kotlionstudy

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.kotlionstudy.data.User
import com.example.kotlionstudy.kotlionstudy.custom.scale.ScaleMap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dd = User("dd", 23)
        dd.copy(age = 24)
        //组件函数允许数据类在解构声明中使用：
        val (name, age) = dd
        println("$name,$age")
        val scaleMap = findViewById<ScaleMap>(R.id.scale_map)
        Glide.with(this).asBitmap()
            .load("https://cti-device-map.oss-cn-shenzhen.aliyuncs.com/test/19e54bf5-4bda-4883-baa1-fd6a3534f299/cf592c78-910f-4a8b-b2f2-99b3884b73d7.png")
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    scaleMap.mapBitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

    }
}