package com.example.appEasyHealth
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import appEasyHealth.Logica.FoodAdapter
import appEasyHealth.Logica.GymAdapterClient
import appEasyHealth.Logica.GymClass
import com.example.easyhealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ReservedClasses : AppCompatActivity() {

    private lateinit var classList: List<GymClass>
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var dpd : DatePickerDialog
    private lateinit var client: Client
    private lateinit var listClassClient: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        val userDB = databaseReference.child(user?.uid!!)
        setContentView(R.layout.activity_reservedclasses)
        listClassClient = findViewById(R.id.listClass)
        val currentDate = LocalDateTime.now()
        val formatterDay = DateTimeFormatter.ofPattern("dd")
        val formattedDay = currentDate.format(formatterDay)
        val formatterMonth = DateTimeFormatter.ofPattern("MM")
        val formattedMonth = currentDate.format(formatterMonth)
        val formatterYear = DateTimeFormatter.ofPattern("yyyy")
        val formattedYear = currentDate.format(formatterYear)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        userDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                client  = p0.getValue(Client::class.java)!!
                if (client.trainer != null) {
                    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(applicationContext,"Fail to read data", Toast.LENGTH_SHORT).show()
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            for (snapshot : DataSnapshot in p0.children) {
                                val trainerDB = snapshot.getValue(Trainer::class.java)
                                if (trainerDB?.id == client.trainer!!.id) {
                                    userDB.child("trainer").setValue(trainerDB)
                                }
                            }
                        }
                    })
                    classList = client.trainer!!.getClassesOnDay("$formattedDay/$formattedMonth/$formattedYear")
                    listClassClient.adapter = GymAdapterClient(this@ReservedClasses,classList,client)
                } else {
                    Toast.makeText(applicationContext,"Add a Trainer First", Toast.LENGTH_LONG).show()
                }
            }

        })

        dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            if (client.trainer != null) {
                var any = year.toString()
                var mes = (monthOfYear +1).toString()
                var dia = dayOfMonth.toString()
                if (mes.length == 1) mes = "0$mes"
                if (dia.length == 1) dia = "0$dia"
                classList = client.trainer!!.getClassesOnDay("$dia/$mes/$any")
                listClassClient.adapter = GymAdapterClient(this@ReservedClasses,classList,client)
            } else {
                Toast.makeText(applicationContext,"Add a Trainer First", Toast.LENGTH_LONG).show()
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
