package com.example.easyhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class PersonalDiet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_diet)
    }

    fun mainClient(view: View){
        val intent = Intent(this, MainClient::class.java)
        startActivity(intent)
    }
}
