package com.example.appEasyHealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.easyhealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class SettingsTrainer : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var trainer: Trainer
    private lateinit var userDB: DatabaseReference
    private lateinit var txtID: EditText


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

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_settings, SettingsTrainer.TrainerPreference())
                .commit()
        }else{
            title = savedInstanceState.getCharSequence(SettingsTrainer.TAG_TITLE)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0){
                setTitle(R.string.ClientSettings)
            }
        }

    }

    //Va fora del onCreate?
    fun addClient(view: View){
        val txtClientID:String = txtID.text.toString()
        var res = trainer.addClient(txtClientID)

        /* GESTIO DE TOAST PER COMUNICACIÓ ,ERROR D'EXECUCIO SIMULATNIA ... no envia el toast alhora correcte
        if (res == 0) {
            Toast.makeText(this,"Malament...", Toast.LENGTH_SHORT).show()
        }
        if (res == 1) {
            Toast.makeText(this,"Client added succesfully!", Toast.LENGTH_SHORT).show()
        }
        if (res == 2) {
            Toast.makeText(this,"Client already has a Trainer assigned!", Toast.LENGTH_LONG).show()
        }
        */
        txtID.text.clear()
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

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat?,
        pref: Preference?
    ): Boolean {

        val args = pref?.extras
        val fragment = pref?.fragment?.let {
            supportFragmentManager.fragmentFactory.instantiate(
                classLoader,
                it
            ).apply {
                arguments = args
                setTargetFragment(caller,0)
            }
        }

        fragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_settings,it)
                .addToBackStack(null)
                .commit()
        }

        title = pref?.title
        return true
    }

    companion object {

        private val TAG_TITLE = "SETTINGS_TRAINER"

    }

    fun goBack(view: View) {
        finish()
    }


}
