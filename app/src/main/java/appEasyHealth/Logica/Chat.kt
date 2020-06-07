package com.example.appEasyHealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import appEasyHealth.Logica.Message
import appEasyHealth.Logica.MessageAdapter
import appEasyHealth.Logica.Usuari
import com.example.easyhealth.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class Chat : AppCompatActivity() {
    private lateinit var chatEditText: EditText
    private lateinit var recycler: ListView
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var originID: String
    private lateinit var user: FirebaseUser
    private lateinit var destinyID: String
    private lateinit var txtUser: TextView
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance()
        reference = database.reference
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        originID = user?.uid
        destinyID = intent.getSerializableExtra("DestinyID").toString()
        createFirebaseListener()
        setContentView(R.layout.activity_chat)
        chatEditText = findViewById(R.id.edittext_chatbox)
        recycler = findViewById(R.id.recyclerChat)
        txtUser = findViewById(R.id.txtDestinyChat)
        var userDB = reference.child("User").child(destinyID)
        userDB.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(Usuari::class.java)
                if (user?.name != null) {
                    txtUser.text = user.name
                }
            }

        })

    }


    fun goBack(view: View){
        finish()
    }

    fun sendMessage(view: View) {

        if (!chatEditText.text!!.isEmpty()){
            sendData()
        }else{
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
        }

    }

    private fun sendData() {
        val time = java.lang.String.valueOf(System.currentTimeMillis())
        reference?.child("messages")?.child(time)?.setValue(
            Message(chatEditText.text.toString(),originID,destinyID,false,time)
        )

        //clear the text
        chatEditText.setText("")
    }

    private fun setupAdapter(data: ArrayList<Message>){
        var yourChat: ArrayList<Message> = ArrayList()
        for (message in data) {
            if ((message.destinyID == user?.uid!!) || (message.originID == user?.uid)) {
                yourChat.plusAssign(message)
            }
        }
        recycler.adapter = MessageAdapter(this,yourChat)

        //scroll to bottom
    }

    private fun createFirebaseListener(){
        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //log Error
            }

            override fun onDataChange(p0: DataSnapshot) {
                val toReturn: ArrayList<Message> = ArrayList();

                for(data in p0.children){
                    val messageData = data.getValue<Message>(Message::class.java)

                    //unwrap
                    val message = messageData?.let { it } ?: continue

                    toReturn.add(message)
                }

                //sort so newest at bottom
                toReturn.sortBy { message ->
                    message.timestamp
                }

                setupAdapter(toReturn)
            }

        }

        reference?.child("messages")?.addValueEventListener(postListener)


    }


}