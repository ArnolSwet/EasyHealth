package com.example.appEasyHealth

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import appEasyHealth.Logica.GymAdapterClient
import appEasyHealth.Logica.GymAdapterTrainer
import appEasyHealth.Logica.GymClass
import com.example.easyhealth.R
import com.example.easyhealth.R.id.classesListTrainer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class YourClasses : AppCompatActivity() {

    private lateinit var classList: List<GymClass>
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var dpd : DatePickerDialog
    private lateinit var trainer: Trainer
    private lateinit var classesListTrainer: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        val userDB = databaseReference.child(user?.uid!!)
        setContentView(R.layout.activity_your_classes)
        classesListTrainer = findViewById(R.id.classesListTrainer)
        val currentDate = LocalDateTime.now()
        val formatterDay = DateTimeFormatter.ofPattern("dd")
        var formattedDay = currentDate.format(formatterDay)
        val formatterMonth = DateTimeFormatter.ofPattern("MM")
        var formattedMonth = currentDate.format(formatterMonth)
        val formatterYear = DateTimeFormatter.ofPattern("yyyy")
        var formattedYear = currentDate.format(formatterYear)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        userDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                trainer  = p0.getValue(Trainer::class.java)!!
                if (trainer.classesReservades?.isNotEmpty()!!) {

                    classList = trainer.getReservedClassesOnDay("$formattedDay/$formattedMonth/$formattedYear")
                    classesListTrainer.adapter = GymAdapterTrainer(this@YourClasses,classList,trainer)
                } else {
                    Toast.makeText(applicationContext,"Any Reserved class yet", Toast.LENGTH_LONG).show()
                }
            }

        })

        dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            if (trainer.classesReservades?.isNotEmpty()!!) {
                formattedYear = year.toString()
                formattedMonth = (monthOfYear +1).toString()
                formattedDay = dayOfMonth.toString()
                if (formattedMonth.length == 1) formattedMonth = "0$formattedMonth"
                if (formattedDay.length == 1) formattedDay = "0$formattedDay"
                classList = trainer.getReservedClassesOnDay("$formattedDay/$formattedMonth/$formattedYear")
                classesListTrainer.adapter = GymAdapterTrainer(this,classList,trainer)
            } else {
                Toast.makeText(applicationContext,"Any Reserved class yet", Toast.LENGTH_LONG).show()
            }

        }, year, month, day)
    }

    fun goback(view: View) {
        finish()
    }

    fun calendar(view: View) {
        dpd.show()
    }
}
