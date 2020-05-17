package com.example.appEasyHealth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import appEasyHealth.Logica.ClientForTrainerAdapter
import com.example.easyhealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*

class MainTrainer : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var txtName:  TextView
    private lateinit var txtMssgs:  TextView
    private lateinit var txtNumClass: TextView
    private lateinit var txtNumClients: TextView
    //private val llistaClients: ArrayList<Client> = ArrayList()

    // llista de noms client + codi
    //private var clientNames = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_trainer)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        val userDB = databaseReference.child(user?.uid!!)


        // Canviar Nom Variables arreglar
        txtName = findViewById(R.id.txtClientName)
        txtNumClass = findViewById(R.id.txtCliSubscriptionNum)
        txtMssgs = findViewById(R.id.txtCliHeightNum)
        txtNumClients = findViewById(R.id.txtCliWeightNum)

        /* PER AFEGIR UN CLIENT AL PRINCIPI DE PROVA
        userDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                trainer = p0.getValue(Trainer::class.java)!!
                trainer.addClient("5Cby")
            }
        })*/

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
                if (trainer.llistaClients?.size ?: 0 != 0) {
                    getClientNames(trainer,clientNames)
                }
            }
        })
    }

    fun getClientNames(trainer :Trainer, clientNames :ArrayList<String>) {
        for (client in trainer.llistaClients!!) {
            var string = ""
            string = client.name.toString() + " #" + client.id.toString()
            if (!clientNames.contains(string)) clientNames.add(string)
        }
        initReyclerView(trainer, clientNames)
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
