package com.example.appEasyHealth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easyhealth.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*


class Signup : AppCompatActivity() {

    private lateinit var txtName: TextInputEditText
    private lateinit var txtUser: TextInputEditText
    private lateinit var txtEmail: TextInputEditText
    private lateinit var txtPassword: TextInputEditText
    private lateinit var txtRepeatPassword: TextInputEditText
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var progress: WaitingProgress

    private var itsClient = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        switch1.setOnCheckedChangeListener { compoundButton, onSwitch ->
            itsClient = !onSwitch

            txtName = findViewById(R.id.txtName)
            txtUser = findViewById(R.id.txtUser)
            txtEmail = findViewById(R.id.txtEmail)
            txtPassword = findViewById(R.id.txtPass)
            txtRepeatPassword = findViewById(R.id.txtRepeatPass)

            progress = WaitingProgress()

            database = FirebaseDatabase.getInstance()
            auth = FirebaseAuth.getInstance()
            dbReference = database.reference.child("User")
        }
    }

    private fun createNewAccount() {
        val name:String = txtName.text.toString()
        val username:String = txtUser.text.toString()
        val email:String = txtEmail.text.toString()
        val pass:String = txtPassword.text.toString()
        val repeatPass:String = txtRepeatPassword.text.toString()


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && pass == repeatPass) {

            //var intentProgress = Intent(this.progress.intent)
            //startActivity(intentProgress)

            auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener {
                    
                    task ->

                    if (task.isComplete) {
                        val user:FirebaseUser?=auth.currentUser
                        // Fins que no es verifica el Email, no continua. Ho comento
                        if(sendEmail(user)) {
                            val userBD = dbReference.child(user?.uid!!)
                            userBD.child("Name").setValue(name)
                            userBD.child("UserName").setValue(username)
                            if (itsClient) {
                                userBD.child("Type").setValue("Client")
                            } else {
                                userBD.child("Type").setValue("Trainer")
                            }
                        }
                    } else {
                        Toast.makeText(this.progress,"Error en Registre", Toast.LENGTH_LONG).show()
                    }

            }

        } else if (pass != repeatPass) {
            Toast.makeText(this.progress,"Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
        }
    }

    private fun sendEmail(user: FirebaseUser?): Boolean {
        Toast.makeText(this.progress,"Enviando email", Toast.LENGTH_SHORT).show()
        var correctEmail : Boolean = false
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) {
                task ->

                if (task.isComplete) {
                    Toast.makeText(this.progress,"Email enviado", Toast.LENGTH_LONG).show()
                    correctEmail = true
                }
                else {
                    Toast.makeText(this.progress,"Error al enviar el mail", Toast.LENGTH_LONG).show()
                    correctEmail = false
                }
            }
        return correctEmail

    }

    fun anarServei(view: View) {
        createNewAccount()
        if (itsClient) {
            var intent = Intent(this,MainClient::class.java)
            startActivity(intent)
        } else {
            var intent = Intent(this,MainTrainer::class.java)
            startActivity(intent)
        }
    }

    fun goback(view: View) {
        finish()
    }
}