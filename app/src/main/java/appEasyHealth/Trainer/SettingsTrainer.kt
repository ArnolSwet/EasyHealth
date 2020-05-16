package com.example.appEasyHealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.easyhealth.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class SettingsTrainer : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var trainer: Trainer
    private lateinit var userDB: DatabaseReference
    private lateinit var txtID: TextInputEditText
    private val TAG_TITLE = "SETTINGS_TRAINER"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_trainer)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val user:FirebaseUser? = auth.currentUser
        txtID = findViewById(R.id.txtClientN)
        userDB = reference.child(user?.uid!!)

        userDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
            }
            override fun onDataChange(p0: DataSnapshot) {
                trainer  = p0.getValue(Trainer::class.java)!!
            }
        })

    }

    //Va fora del onCreate?
    fun addClient(view: View){
        val txtClientID:String = txtID.text.toString()
        trainer.addClient(txtClientID)
    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putCharSequence(TAG_TITLE,title)
    }

    override fun onSupportNavigateUp(): Boolean {
        if(supportFragmentManager.popBackStackImmediate()){
            return true
        }
        return super.onSupportNavigateUp()
    }

    class TrainerPreference() : PreferenceFragmentCompat(){
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings_trainer_layout,rootKey)
            //Faltara l'array amb class availability
            //bindPreferenceSummaryToValue(findPreference("notif")!!)
        }
    }




    fun goBack(view: View) {
        finish()
    }


}
