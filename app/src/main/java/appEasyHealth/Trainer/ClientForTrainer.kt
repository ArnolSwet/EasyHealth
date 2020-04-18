package com.example.appEasyHealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.easyhealth.R

class ClientForTrainer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_for_trainer)
    }

    fun goFoodClient(view: View) {
        val intent = Intent(this, FoodTrainer::class.java)
        startActivity(intent)
    }

    fun statistics(view: View) {
        val intent = Intent(this, Statistics::class.java)
        startActivity(intent)
    }

    fun chatClient(view: View) {
        val intent = Intent(this, Chat::class.java)
        startActivity(intent)
    }

    fun personalDietTrainer(view: View) {
        val intent = Intent(this, PersonalDietTrainer::class.java)
        startActivity(intent)
    }

    fun goBack(view: View) {
        finish()
    }


}
