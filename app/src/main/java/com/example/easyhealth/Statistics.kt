package com.example.easyhealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Statistics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
    }

    fun goback(view: View) {
        finish()
    }
}
