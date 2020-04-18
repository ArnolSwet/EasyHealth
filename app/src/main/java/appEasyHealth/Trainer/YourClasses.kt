package com.example.appEasyHealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.easyhealth.R

class YourClasses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_classes)
    }

    fun goback(view: View) {
        finish()
    }
}
