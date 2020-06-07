package com.example.appEasyHealth

import android.widget.Toast
import appEasyHealth.Logica.GymClass
import appEasyHealth.Logica.Message
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
    override var location: String? = "Set Your Location",
    var disponibility : String? = "All",
    var llistaClients: MutableList<String>? = ArrayList(),
    override var classesReservades: MutableList<GymClass>? = ArrayList()
    ) : Usuari(name, username, email, id, notif,type,location, classesReservades) {

    fun addClient(id: String) : Int {
        var pass : Int = 0
        var database : FirebaseDatabase = FirebaseDatabase.getInstance()
        var reference :DatabaseReference = database.getReference("User")
        // Afegir client a usuari trainer
        var auth : FirebaseAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        var userTrainer = reference.child(user?.uid!!)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("Failed to add Client: " + p0.code)
                // Case 0; error en la actualització, o simplement no actualització
                pass = 0
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot : DataSnapshot in p0.children) {
                    val client = snapshot.getValue(Client::class.java)
                    if (client?.id == id) {
                        if (client.trainer.equals("")) {
                            llistaClients?.add(snapshot.key!!)
                            userTrainer.child("llistaClients").setValue(llistaClients)
                            val userDB = reference.child(snapshot.key!!)
                            userDB.child("trainer").setValue(user?.uid!!)
                            // Case 1; actualizació correcte
                            pass = 1
                        } else {
                            // Case 2: no s'ha actualitzat, client existent
                            pass = 2
                        }
                    }
                    if (pass != 0) break
                }
            }
        })
        return pass
    }

    fun deleteClient(client: String) {
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

    fun getReservedClassesOnDay(date: String) : List<GymClass> {
        val reservedClasses: MutableList<GymClass> = ArrayList()
        for (gymClass in super.getClassesOnDay(date)) {
            if (!(gymClass.clientID?.equals("")!!)) {
                reservedClasses += gymClass
            }
        }
        return reservedClasses
    }

    fun cancelReservedClass(gymClass: GymClass) {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var reference : DatabaseReference = database.getReference("User")
        var auth : FirebaseAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        var userDB = reference.child(user?.uid!!)
        var clientDB = reference.child(gymClass.clientID!!)
        gymClass.disponibilitat = "Available"
        gymClass.clientID = ""
        addReservedClass(gymClass)
        userDB.setValue(this)
        clientDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("Fail to read data")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val client = p0.getValue(Client::class.java)
                client!!.cancelClass(gymClass)
                clientDB.setValue(client)
            }

        })
    }


}








