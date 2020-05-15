package com.example.appEasyHealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.media.Image
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
    private lateinit var txtName:  TextView
    private lateinit var txtSuscription: TextView
    private lateinit var txtWeight: TextView
    private lateinit var txtHeight: TextView
    private val foodList: ArrayList<Food> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val user:FirebaseUser? = auth.currentUser
        val userDB = databaseReference.child(user?.uid!!)
        setContentView(R.layout.activity_main_client)
        txtName = findViewById(R.id.txtClientName)
        txtSuscription = findViewById(R.id.txtCliSubscriptionNum)
        txtWeight = findViewById(R.id.txtCliWeightNum)
        txtHeight = findViewById(R.id.txtCliHeightNum)
        var food = Food("Macarrons",25.6,"23/4/2020", "android.resource://" + packageName + "/" + R.mipmap.macarrons,"Dinner")
        var food2 = Food("Amanida",15.3,"23/4/2020", "android.resource://" + packageName + "/" + R.mipmap.ensalada,"Lunch")
        foodList.plusAssign(food)
        foodList.plusAssign(food2)
        userDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(p0: DataSnapshot) {
                val client  = p0.getValue(Client::class.java)!!
                userDB.child("foodlist").setValue(foodList)

                if (client?.name != null) {
                    txtName.text = client.name
                }
                if (client?.weight != null) {
                    txtWeight.text = client.weight.toString()
                }
                if (client?.height != null) {
                    txtHeight.text = client.height.toString()
                }
                if (client?.suscription != null) {
                    txtSuscription.text = client.suscription
                }
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
