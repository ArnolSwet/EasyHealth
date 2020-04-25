package com.example.appEasyHealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.View
import androidx.preference.*
import com.example.easyhealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SettingsClient : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_client)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val user:FirebaseUser? = auth.currentUser
        userDB = reference.child(user?.uid!!)

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
                        val weight : String = stringValue
                        userDB.child("Weight").setValue(weight)

                    }else if (preference.key == "height") {
                        val height : String = stringValue
                        userDB.child("Height").setValue(height)
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
