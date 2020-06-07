package com.example.appEasyHealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import appEasyHealth.Client.FoodClient
import appEasyHealth.Logica.FoodForClientAdapter
import com.example.easyhealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class MainClient : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var txtName:  TextView
    private lateinit var txtSuscription: TextView
    private lateinit var txtWeight: TextView
    private lateinit var txtHeight: TextView
    private lateinit var txtLocation: TextView
    private lateinit var txtEmpty: TextView
    private lateinit var recycler: RecyclerView
    private var trainerID: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val user:FirebaseUser? = auth.currentUser
        val userDB = databaseReference.child(user?.uid!!)
        setContentView(R.layout.activity_main_client)

        txtName = findViewById(R.id.txtClientName)
        txtLocation = findViewById(R.id.txtCliLocation)
        txtSuscription = findViewById(R.id.txtCliSubscriptionNum)
        txtWeight = findViewById(R.id.txtCliWeightNum)
        txtHeight = findViewById(R.id.txtCliHeightNum)
        txtEmpty = findViewById(R.id.nameEmptyClient)
        recycler = findViewById(R.id.recycler_view_food)
        val currentDate = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted = currentDate.format(formatter)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(p0: DataSnapshot) {
                val client = p0.getValue(Client::class.java)!!
                if (!client?.trainer.equals("")) {
                    trainerID = client.trainer!!
                    val trainerDB = databaseReference.child(trainerID)
                    trainerDB.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val trainer = p0.getValue(Trainer::class.java)
                            Toast.makeText(applicationContext, "Your Trainer is: " + trainer!!.name, Toast.LENGTH_LONG).show()
                        }

                    })

                }
            }
        })


        userDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(p0: DataSnapshot) {
                val client  = p0.getValue(Client::class.java)!!

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
                if (client.foodlist.isNullOrEmpty()) {
                    recycler.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.VISIBLE);
                } else {
                    recycler.setVisibility(View.VISIBLE);
                    txtEmpty.setVisibility(View.GONE);
                    var todayFood = client.getFoodListonDay(formatted)
                    var foodNames = ArrayList<String>()
                    var foodTypes = ArrayList<String>()

                    for (food in todayFood) {
                        foodNames.add(food.name.toString())
                        foodTypes.add(food.tipus.toString())
                    }
                    initReyclerView(client, foodNames, foodTypes)
                }
                if (client?.location != null) {
                    txtLocation.text = client.location
                }
            }
        })
    }

    fun initReyclerView(
        client: Client,
        foodNames: ArrayList<String>,
        foodTypes: ArrayList<String>
    ) {
        var managerLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var recyclerView =findViewById<RecyclerView>(R.id.recycler_view_food)
        recyclerView.layoutManager = managerLayout
        var adapter = FoodForClientAdapter(this, foodNames, foodTypes)
        recyclerView.adapter = adapter
    }

    fun goback(view: View) {
        finish()
    }

    fun gotochat(view: View) {
        val intent = Intent(this, Chat::class.java)
        intent.putExtra("DestinyID",trainerID)
        ContextCompat.startActivity(this,intent, Bundle())
    }

    fun settingsClient(view: View) {
        val intent = Intent(this, SettingsClient::class.java)
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
