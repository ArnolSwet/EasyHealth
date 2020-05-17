package com.example.appEasyHealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.easyhealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.ArrayList

class ClientForTrainer : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var txtName:  TextView
    private lateinit var txtSuscription: TextView
    private lateinit var txtWeight: TextView
    private lateinit var txtHeight: TextView
    private val foodList: ArrayList<Food> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_for_trainer)
        val client = getIntent().getSerializableExtra("Client").toString()
        val clientID = client.subSequence(client.indexOf("#")+1 ,client.length)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")
        var userDB = databaseReference

        txtName = findViewById(R.id.txtClientName)
        txtSuscription = findViewById(R.id.txtCliSubscriptionNum)
        txtWeight = findViewById(R.id.txtCliWeightNum)
        txtHeight = findViewById(R.id.txtCliHeightNum)

        userDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot : DataSnapshot in p0.children) {
                    val client = snapshot.getValue(Client::class.java)
                    if (client?.id == clientID) {
                        userDB = databaseReference.child(snapshot.key!!)
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
                }
            }
        })
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
