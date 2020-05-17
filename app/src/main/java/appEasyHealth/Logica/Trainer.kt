package com.example.appEasyHealth

import android.widget.Toast
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
    var llistaClients: MutableList<Client>? = ArrayList()
    ) : Usuari(name, username, email, id) {

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
                        if (client.trainer == null) {
                            llistaClients?.plusAssign(client)
                            userTrainer.child("llistaClients").setValue(llistaClients)
                            val userDB = reference.child(snapshot.key!!)
                            userDB.child("trainer").setValue(this@Trainer)
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

    fun deleteClient(client: Client) {
        this.llistaClients?.remove(client)
    }

    // fun addPersonalDiet
    // fun removePersonalDiet

}








