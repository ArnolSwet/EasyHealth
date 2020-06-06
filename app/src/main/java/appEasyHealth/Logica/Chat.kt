package com.example.appEasyHealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import appEasyHealth.Logica.Message
import appEasyHealth.Logica.MessageAdapter
import com.example.easyhealth.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class Chat : AppCompatActivity() {
    private lateinit var chatEditText: TextInputEditText
    private lateinit var recycler: RecyclerView
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat)

        createFirebaseListener()
        database = FirebaseDatabase.getInstance()
        reference = database.reference
        chatEditText = findViewById(R.id.edittext_chatbox)
        recycler = findViewById(R.id.recyclerChat)
    }


    fun goBack(view: View){
        finish()
    }

    fun sendMessage(view: View) {

        if (!chatEditText.text.toString().isEmpty()){
            sendData()
        }else{
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
        }

    }

    private fun sendData() {
        reference?.child("messages")?.child(java.lang.String.valueOf(System.currentTimeMillis()))?.setValue(
            Message(chatEditText.text.toString())
        )

        //clear the text
        chatEditText.setText("")
    }

    private fun setupAdapter(data: ArrayList<Message>){
        val linearLayoutManager = LinearLayoutManager(this)
        recycler.layoutManager = linearLayoutManager
        recycler.adapter = MessageAdapter(data) {
            Toast.makeText(this, "${it.text} clicked", Toast.LENGTH_SHORT).show()
        }

        //scroll to bottom
        recycler.scrollToPosition(data.size - 1)
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