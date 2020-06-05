package com.example.appEasyHealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import appEasyHealth.Logica.Usuari
import appEasyHealth.Signup
import com.example.easyhealth.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class Login : AppCompatActivity() {

    private lateinit var txtName: TextInputEditText
    private lateinit var txtUser: TextInputEditText
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("User")
        txtName = findViewById(R.id.nameEdBreak)
        txtUser = findViewById(R.id.calEdBreak)
        auth = FirebaseAuth.getInstance()
    }

    fun login(view: View) {
        val email:String = txtName.text.toString()
        val password:String = txtUser.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    task ->
                    if(task.isSuccessful){
                        val user: FirebaseUser? = auth.currentUser
                        if (user?.isEmailVerified!!){
                            action(user)
                        }else {
                            Toast.makeText(this,"Please, verify you email",Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(this,"Error on authentication",Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun action(user: FirebaseUser?) {
        val userDB = reference.child(user?.uid!!)
        userDB.addListenerForSingleValueEvent(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                var usuari = p0.getValue(Usuari::class.java)
                txtUser.setText("")
                if (usuari?.type.equals("Client")) {
                    val intento = Intent(this@Login, MainClient::class.java)
                    startActivity(intento)
                } else if (usuari?.type.equals("Trainer")) {
                    val intento = Intent(this@Login, MainTrainer::class.java)
                    startActivity(intento)
                } else {
                    Toast.makeText(applicationContext,"User not defined", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    fun anarSignup(view: View) {
        txtUser.setText("")
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }
}
