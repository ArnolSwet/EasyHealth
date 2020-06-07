package com.example.appEasyHealth

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easyhealth.R
import com.google.android.material.textfield.TextInputEditText


class AddFood : AppCompatActivity() {

    private lateinit var txtName: TextInputEditText
    private lateinit var txtCalories: TextInputEditText
    private lateinit var typeMeal: String
    private lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        txtName = findViewById(R.id.nameEdBreak)
        txtCalories = findViewById(R.id.calEdBreak)

        val spinner = findViewById<View>(R.id.spinLunch) as Spinner
        typeMeal = spinner.selectedItem.toString()
        val image: ImageView = findViewById<View>(R.id.imageTypeMeal) as ImageView
        val img_bfst: Int = R.drawable.breakfast
        val img_lun: Int = R.drawable.lunch
        val img_din: Int = R.drawable.dinner
        val img_sna: Int = R.drawable.snack
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                        image.setImageResource(img_bfst)
                }
                if (position == 1) {
                    image.setImageResource(img_lun)
                }
                if (position == 2) {
                    image.setImageResource(img_din)
                }
                if (position == 3) {
                    image.setImageResource(img_sna)
                }
            }

        }

        date = getIntent().getSerializableExtra("Date").toString()
        var clientID = getIntent().getSerializableExtra("Client").toString()
        Toast.makeText(this,clientID, Toast.LENGTH_LONG).show()
    }

    fun addFood( view: View) {

    }

    fun goback(view: View) {
        finish()
    }
}
