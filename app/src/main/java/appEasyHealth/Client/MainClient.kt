package com.example.appEasyHealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.easyhealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MainClient : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var name:  TextView
    private lateinit var suscription: TextView
    private lateinit var weight: TextView
    private lateinit var height: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val user:FirebaseUser? = auth.currentUser
        val userdB = databaseReference.child(user?.uid!!)
        val nameClient = userdB.child("Name")
        val weightClient = userdB.child("Weight")
        val heightClient = userdB.child("Height")
        setContentView(R.layout.activity_main_client)
        name = findViewById(R.id.txtClientName)
        suscription = findViewById(R.id.txtCliSubscriptionNum)
        weight = findViewById(R.id.txtCliWeightNum)
        height = findViewById(R.id.txtCliHeightNum)
        nameClient.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.value != null) {
                    name.text = p0.value.toString()
                }
            }
        })
        weightClient.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.value != null) weight.text = p0.value.toString()

            }
        })
        heightClient.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.value != null)height.text = p0.value.toString()
            }
        })
    }

    fun goback(view: View) {
        finish()
    }

    fun gostats(view: View) {
        val intent = Intent(this, Statistics::class.java)
        startActivity(intent)
    }
    fun gotochat(view: View) {
        val intent = Intent(this, Chat::class.java)
        startActivity(intent)
    }

    fun settingsClient(view: View) {
        val intent = Intent(this, SettingsClient::class.java)
        startActivity(intent)
    }
    fun personalDiet(view: View) {
        val intent = Intent(this, PersonalDiet::class.java)
        startActivity(intent)
    }

    fun reserveClasses(view: View) {
        val intent = Intent(this, ReservedClasses::class.java)
        startActivity(intent)
    }

    fun goFood(view: View) {
        val intent = Intent(this, FoodClient::class.java)
        startActivity(intent)
    }
}
