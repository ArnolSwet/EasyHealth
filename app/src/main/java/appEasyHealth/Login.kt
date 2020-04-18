package com.example.appEasyHealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.easyhealth.R

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun anarSignup(view: View) {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }
}
