package com.example.easyhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {

    var itsClient = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        switch1.setOnCheckedChangeListener { compoundButton, onSwitch ->
            itsClient = !onSwitch
        }
    }

    fun anarmain(view: View) {
        var intent = Intent(this, MainClient::class.java)
        if (itsClient) {
            intent = Intent(this, MainClient::class.java)
        } else {
            intent = Intent(this, MainTrainer::class.java)
        }
        startActivity(intent)
    }

    fun goback(view: View) {
        finish()
    }


}
