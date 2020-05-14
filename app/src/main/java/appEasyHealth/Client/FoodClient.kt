package com.example.appEasyHealth

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.plusAssign
import appEasyHealth.Logica.FoodAdapter
import com.example.easyhealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class FoodClient : AppCompatActivity() {

    private lateinit var foodList: List<Food>
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var dpd : DatePickerDialog
    private lateinit var client: Client
    private lateinit var listFoodClient: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        val userDB = databaseReference.child(user?.uid!!)
        setContentView(R.layout.activity_food_client)
        listFoodClient = findViewById(R.id.foodList)
        userDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(p0: DataSnapshot) {
                client  = p0.getValue(Client::class.java)!!
            }
        })

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            foodList = client.getFoodListonDay("$dayOfMonth/$monthOfYear/$year")
            listFoodClient.adapter = FoodAdapter(this,foodList)

        }, year, month, day)
    }

    fun addFood(view: View) {
        val intent = Intent(this, AddFood::class.java)
        startActivity(intent)
    }
    fun goBack(view: View) {
        finish()
    }

    fun calendar(view: View) {
        dpd.show()
    }
}
