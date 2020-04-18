package com.example.appEasyHealth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.easyhealth.R

class ReservedClasses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservedclasses)
    }

    fun goback(view: View) {
        finish()
    }
}
