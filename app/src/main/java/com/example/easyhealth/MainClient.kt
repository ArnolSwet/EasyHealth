package com.example.easyhealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View

class MainClient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_client)
    }

    fun goback(view: View) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    fun gostats(view: View) {
        val intent = Intent(this, Statistics::class.java)
        startActivity(intent)
    }
    fun gotochat(view: View) {
        val intent = Intent(this, Chat::class.java)
        startActivity(intent)
    }
}
