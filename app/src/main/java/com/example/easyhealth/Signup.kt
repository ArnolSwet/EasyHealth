package com.example.easyhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val intent = Intent(this, MainTrainer::class.java)
            } else {
                val intent = Intent(this, MainClient::class.java)
            }
        }
    }

    fun anarmain(view: View) {
        val intent = Intent(this, MainClient::class.java)
            if (switch1.isChecked) {
                val intent = Intent(this, MainTrainer::class.java)
            } else {
                val intent = Intent(this, MainClient::class.java)
            }
        startActivity(intent)
    }

    fun goback(view: View) {
        finish()
    }


}
