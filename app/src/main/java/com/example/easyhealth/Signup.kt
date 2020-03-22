package com.example.easyhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    fun anarclient(view: View) {
        val intent = Intent(this, MainClient::class.java)
        startActivity(intent)
    }

    fun goback(view: View) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }


}
