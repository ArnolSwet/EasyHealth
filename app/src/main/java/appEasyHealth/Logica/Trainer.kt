package com.example.appEasyHealth

import android.content.Context
import android.widget.Toast
import appEasyHealth.Logica.Usuari
import com.google.firebase.database.*


data class Trainer (
    override var name: String? = "",
    override var username: String? = "",
    override var email: String? = "",
    override var id: String? = "",
    override var notif : Boolean? = false,
    var disponibility : String? = "All",
    var llistaClients: MutableList<Client>? = ArrayList()
    ) : Usuari(name, username, email, id) {

    fun addClient(id: String) : Boolean {
        var pass : Boolean = false
        var database : FirebaseDatabase = FirebaseDatabase.getInstance()
        var reference :DatabaseReference = database.getReference("User")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("Failed to add Client: " + p0.code)
                pass = false
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot : DataSnapshot in p0.children) {
                    val client = snapshot.getValue(Client::class.java)
                    if (client?.id == id) {
                        llistaClients?.plusAssign(client)
                        pass = true
                        break
                    } else {
                         pass = false
                    }
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








