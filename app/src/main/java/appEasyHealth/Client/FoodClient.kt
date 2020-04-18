package com.example.appEasyHealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.easyhealth.R

class FoodClient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_client)
    }

    fun addFood(view: View) {
        val intent = Intent(this, AddFood::class.java)
        startActivity(intent)
    }
    fun goBack(view: View) {
        finish()
    }
}
