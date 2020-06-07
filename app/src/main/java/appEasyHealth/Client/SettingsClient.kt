package com.example.appEasyHealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.preference.*
import com.example.easyhealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_statistics.*

class SettingsClient : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var txtWeight : EditTextPreference
    private lateinit var txtClientID : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_client)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val user:FirebaseUser? = auth.currentUser
        userDB = reference.child(user?.uid!!)
        txtClientID = findViewById(R.id.txtcode)
        userDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(p0: DataSnapshot) {
                val client  = p0.getValue(Client::class.java)
                if (client?.id != null) {
                    txtClientID.text = "#" + client.id
                }
                /*if (client?.weight != null) {
                    txtWeight.summary = client.weight.toString()
                }else {
                    txtWeight.summary = "ND"
                }
                if (client?.height != null) {
                    txtHeight.summary = client.height.toString()
                } else {
                    txtWeight.summary = "ND"
                }
                if (client?.notif != null) {
                    switchNotif.setDefaultValue(client.notif)
                } else {
                    switchNotif.setDefaultValue(false)
                } */
            }
        })

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_settings,ClientPreference())
                .commit()
        }else{
            title = savedInstanceState.getCharSequence(TAG_TITLE)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0){
                setTitle(R.string.ClientSettings)
            }
        }
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

    class ClientPreference() : PreferenceFragmentCompat(){

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings_client_layout,rootKey)
            bindPreferenceSummaryToValue(findPreference("height")!!)
            bindPreferenceSummaryToValue(findPreference("weight")!!)
            bindPreferenceSummaryToValue(findPreference("location")!!)
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

    companion object{

        private val TAG_TITLE = "SETTINGS_CLIENT"
        private lateinit var userDB: DatabaseReference


        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->

            val stringValue = value.toString()

            if (preference is EditTextPreference) {
                if (!TextUtils.isEmpty(stringValue)) {
                    preference.setSummary(stringValue)
                    if (preference.key == "weight") {
                        val weight = stringValue.toDoubleOrNull()
                        userDB.child("weight").setValue(weight)

                    }else if (preference.key == "height") {
                        val height = stringValue.toDoubleOrNull()
                        userDB.child("height").setValue(height)
                    }else if (preference.key == "location") {
                        val location = stringValue.toDoubleOrNull()
                        userDB.child("location").setValue(location)
                    }
                }
            }/*else if (preference is SwitchPreference) {
                val notif : Boolean = preference.isChecked
                //userDB.child("Notif").setValue(notif)
            }*/
            true
        }

        private fun bindPreferenceSummaryToValue(
            preference: Preference
        ) {
            PreferenceManager.setDefaultValues(preference.context,R.xml.settings_client_layout,true)
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener


            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                        .getDefaultSharedPreferences(preference.context)
                        .getString(preference.key,""))
        }
    }

    fun goBack(view: View){
        finish()
    }
}
