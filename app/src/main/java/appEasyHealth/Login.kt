package com.example.appEasyHealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import appEasyHealth.Signup
import com.example.easyhealth.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Login : AppCompatActivity() {

    private lateinit var txtName: TextInputEditText
    private lateinit var txtUser: TextInputEditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
        val intent = Intent(this, MainClient::class.java)
        startActivity(intent)
    }

    fun anarSignup(view: View) {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }
}
