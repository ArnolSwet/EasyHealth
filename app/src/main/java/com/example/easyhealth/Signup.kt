package com.example.easyhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.widget.EditText
import android.view.View
import android.widget.Switch
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_waiting_progress.*
import java.lang.ref.PhantomReference

class Signup : AppCompatActivity() {

    private lateinit var txtName:TextInputEditText
    private lateinit var txtUser:TextInputEditText
    private lateinit var txtEmail:TextInputEditText
    private lateinit var txtPassword:TextInputEditText
    private lateinit var txtRepeatPassword:TextInputEditText
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth
    private lateinit var progress: WaitingProgress
    private var itsClient = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        txtName=findViewById(R.id.txtName)
        txtUser=findViewById(R.id.txtUser)
        txtEmail=findViewById(R.id.txtEmail)
        txtPassword=findViewById(R.id.txtPass)
        txtRepeatPassword=findViewById(R.id.txtRepeatPass)

        progress = WaitingProgress()

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        dbReference = database.reference.child("User")
        switch1.setOnCheckedChangeListener { compoundButton, onSwitch ->
            itsClient = !onSwitch
        }
    }

    fun signup(view: View) {
        createNewAccount()
    }

    private fun createNewAccount() {
        val name:String = txtName.text.toString()
        val username:String = txtUser.text.toString()
        val email:String = txtEmail.text.toString()
        val pass:String = txtPassword.text.toString()
        val repeatPass:String = txtRepeatPassword.text.toString()

        if (pass != repeatPass) {

        }

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {

            var intentProgress = Intent(this, WaitingProgress::class.java)
            startActivity(intentProgress)

            auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener {
                    
                    task ->

                    if (task.isComplete) {
                        val user:FirebaseUser?=auth.currentUser
                        verifyEmail(user)

                        val userBD = dbReference.child(user?.uid!!)

                        userBD.child("Name").setValue(name)
                        userBD.child("User").setValue(username)
                        if (itsClient) {
                            userBD.child("Service").setValue("Client")
                        } else {
                            userBD.child("Service").setValue("Trainer")
                        }
                        action()
                    }

            }

        }
    }

    private fun action() {

        var intent = Intent(this, MainClient::class.java)
        if (itsClient) {
            intent = Intent(this, MainClient::class.java)
        } else {
            intent = Intent(this, MainTrainer::class.java)
        }
        startActivity(intent)

    }

    private fun verifyEmail(user:FirebaseUser?){

        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) {
                task ->

                if (task.isComplete) {
                    Toast.makeText(this,"Email enviado", Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(this,"error al enviar el mail", Toast.LENGTH_LONG).show()

                }
            }

    }

    fun goback(view: View) {
        finish()
    }


}
