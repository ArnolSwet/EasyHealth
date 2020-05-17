package com.example.appEasyHealth

import android.content.Context
import android.widget.Toast
import appEasyHealth.Logica.GymClass
import appEasyHealth.Logica.Usuari
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


data class Trainer (
    override var name: String? = "",
    override var username: String? = "",
    override var email: String? = "",
    override var id: String? = "",
    override var notif : Boolean? = false,
    override var type: String? = "Trainer",
    var disponibility : String? = "All",
    var llistaClients: MutableList<Client>? = ArrayList(),
    override var classesReservades: MutableList<GymClass>? = ArrayList()
    ) : Usuari(name, username, email, id, notif,type, classesReservades) {

    fun addClient(id: String) : Boolean {
        var pass : Boolean = false
        var database : FirebaseDatabase = FirebaseDatabase.getInstance()
        var reference :DatabaseReference = database.getReference("User")
        // Afegir client a usuari trainer
        var auth : FirebaseAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        var userTrainer = reference.child(user?.uid!!)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("Failed to add Client: " + p0.code)
                pass = false
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot : DataSnapshot in p0.children) {
                    val client = snapshot.getValue(Client::class.java)
                    if (client?.id == id) {
                        llistaClients?.plusAssign(client)
                        userTrainer.child("llistaClients").setValue(llistaClients)
                        val userDB = reference.child(snapshot.key!!)
                        userDB.child("trainer").setValue(this@Trainer)
                        pass = true
                    }
                    if (pass) break
                }
            }
        })
        return pass
    }

    fun deleteClient(client: Client) {
        this.llistaClients?.remove(client)
    }

    override fun addReservedClass(rClass: GymClass) {
        var exists: Boolean = false
        for (gymClass in this.classesReservades!!) {
            if ((gymClass.date == rClass.date) && (gymClass.hora == rClass.hora)) {
                this.classesReservades!![this.classesReservades!!.indexOf(gymClass)] = rClass
                exists = true
            }
        }
        if (!exists) {
            this.classesReservades?.plusAssign(rClass)
        }
    }


    // fun addPersonalDiet
    // fun removePersonalDiet

}








