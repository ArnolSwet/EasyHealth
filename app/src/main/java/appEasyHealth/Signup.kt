package appEasyHealth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appEasyHealth.Login
import com.example.appEasyHealth.MainClient
import com.example.appEasyHealth.MainTrainer
import com.example.appEasyHealth.WaitingProgress
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

    private var itsClient = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        switch1.setOnCheckedChangeListener { compoundButton, onSwitch ->
            itsClient = !onSwitch
        }
            txtName = findViewById(R.id.txtName)
            txtUser = findViewById(R.id.txtUser)
            txtEmail = findViewById(R.id.txtEmail)
            txtPassword = findViewById(R.id.txtPass)
            txtRepeatPassword = findViewById(R.id.txtRepeatPass)


            database = FirebaseDatabase.getInstance()
            auth = FirebaseAuth.getInstance()
            dbReference = database.reference.child("User")
    }

    private fun createNewAccount() {
        val name:String = txtName.text.toString()
        val username:String = txtUser.text.toString()
        val email:String = txtEmail.text.toString()
        val pass:String = txtPassword.text.toString()
        val repeatPass:String = txtRepeatPassword.text.toString()


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && pass == repeatPass) {

            var intentProgress = Intent(this,WaitingProgress::class.java)
            startActivity(intentProgress)

            auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener {
                    
                    task ->

                    if (task.isComplete) {
                        val user:FirebaseUser?=auth.currentUser
                        // Fins que no s'envia correctament el Email, no continua. Ho comento
                        if(sendEmail(user)) {
                            val userBD = dbReference.child(user?.uid!!)
                            userBD.child("Name").setValue(name)
                            userBD.child("UserName").setValue(username)
                        }
                        var intent = Intent(this, Login::class.java)
                        startActivity(intent)

                    }
            }

        }else if(pass != repeatPass){
            Toast.makeText(applicationContext,"Passwords does not match", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(applicationContext,"Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmail(user: FirebaseUser?): Boolean {
        Toast.makeText(applicationContext,"Sending mail", Toast.LENGTH_SHORT).show()
        var correctEmail : Boolean = false
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) {
                task ->

                if (task.isComplete) {
                    Toast.makeText(applicationContext,"Verify your mail", Toast.LENGTH_LONG).show()
                    correctEmail = true
                }
                else {
                    Toast.makeText(applicationContext,"Error sending the mail", Toast.LENGTH_LONG).show()
                    correctEmail = false
                }
            }
        return correctEmail

    }

    fun anarServei(view: View) {
        createNewAccount()
    }

    fun goback(view: View) {
        finish()
    }
}