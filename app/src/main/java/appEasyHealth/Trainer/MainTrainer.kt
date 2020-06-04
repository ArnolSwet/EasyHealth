package com.example.appEasyHealth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import appEasyHealth.Logica.GymClass
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import appEasyHealth.Logica.ClientForTrainerAdapter
import com.example.easyhealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainTrainer : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var client: Client
    private lateinit var txtName:  TextView
    private lateinit var txtMssgs:  TextView
    private lateinit var txtNumClass: TextView
    private lateinit var txtNumClients: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        val userDB = databaseReference.child(user?.uid!!)
        setContentView(R.layout.activity_main_trainer)

        // Canviar Nom Variables arreglar

        val currentDate = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted = currentDate.format(formatter)

        txtName = findViewById(R.id.txtClientName)
        txtNumClass = findViewById(R.id.txtCliSubscriptionNum)
        txtMssgs = findViewById(R.id.txtCliHeightNum)
        txtNumClients = findViewById(R.id.txtCliWeightNum)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val trainer = p0.getValue(Trainer::class.java)!!
                trainer.addClient("5Cby")
                if (trainer.getClassesOnDay(formatted).isEmpty()) {
                    val gymClass1 = GymClass("08:00",formatted,user?.uid)
                    val gymClass2 = GymClass("11:00",formatted,user?.uid)
                    val gymClass3 = GymClass("17:00",formatted,user?.uid)
                    val gymClass4 = GymClass("20:00",formatted,user?.uid)
                    trainer.addReservedClass(gymClass1)
                    trainer.addReservedClass(gymClass2)
                    trainer.addReservedClass(gymClass3)
                    trainer.addReservedClass(gymClass4)
                    userDB.setValue(trainer)
                }

            }
        })


        userDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val trainer = p0.getValue(Trainer::class.java)!!
                var clientNames = ArrayList<String>()
                txtNumClients.text = trainer.llistaClients?.size.toString()
                if (trainer?.name != null) {
                    txtName.text = trainer.name
                }
                if (trainer.llistaClients?.size != 0) {
                    getClientNames(trainer,clientNames)
                }
            }
        })
    }

    fun getClientNames(trainer :Trainer, clientNames :ArrayList<String>) {
        for (client in trainer.llistaClients!!) {
            var clientDB = databaseReference.child(client)
            clientDB.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    this@MainTrainer.client = p0.getValue(Client::class.java)!!
                    var string = this@MainTrainer.client.name + " #" + this@MainTrainer.client.id.toString()
                    if (!clientNames.contains(string)) clientNames.add(string)
                    initReyclerView(trainer, clientNames)
                }

            })

        }

    }

    fun initReyclerView(trainer :Trainer, clientNames: ArrayList<String>) {
        var managerLayout = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        var recyclerView =findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = managerLayout
        var adapter = ClientForTrainerAdapter(this, clientNames)
        recyclerView.adapter = adapter
    }

    fun goClient(){
        val intent = Intent(this, ClientForTrainer::class.java)
        startActivity(intent)
    }

    fun yourClasses(view: View){
        val intent = Intent(this, YourClasses::class.java)
        startActivity(intent)
    }

    fun goBack(view: View){
        finish()
    }

    fun goSettings(view: View) {
        val intent = Intent(this, SettingsTrainer::class.java)
        startActivity(intent)
    }
}
