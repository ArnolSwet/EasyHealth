package com.example.easyhealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class FoodTrainer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_trainer)
    }

    fun goBack(view: View) {
        finish()
    }
}
