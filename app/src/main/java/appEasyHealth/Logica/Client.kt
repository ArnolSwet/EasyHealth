package com.example.appEasyHealth

import appEasyHealth.Logica.GymClass
import appEasyHealth.Logica.Usuari
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

data class Client(
    override var name: String? = "",
    override var username: String? = "",
    override var email: String? = "",
    override var id: String? = "",
    override var type: String? = "Client",
    var suscription: String? = "Basic",
    var trainer: Trainer? = null,
    var height: Double? = null,
    var weight: Double? = null,
    override var notif: Boolean? = false,
    override var classesReservades: MutableList<GymClass>? = ArrayList(),
    var foodlist: MutableList<Food>? = ArrayList()
    ) : Usuari(name, username, email, id, notif,type, classesReservades) {


    fun addFood(food: Food) {
        this.foodlist?.plusAssign(food)
    }

    fun deleteFood(food: Food) {
        this.foodlist?.remove(food)
    }
    override fun addReservedClass(gymClass: GymClass) {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var reference : DatabaseReference = database.getReference("User")
        var auth : FirebaseAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        var userClient = reference.child(user?.uid!!)
        gymClass.clientID = user?.uid!!
        if (this?.trainer != null) {
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    println("Failed to add Class")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (snapshot: DataSnapshot in p0.children) {
                        val trainer = snapshot.getValue(Trainer::class.java)
                        if (trainer?.id == this@Client.trainer!!.id) {
                            val trainerDB = reference.child(snapshot.key!!)
                            gymClass.trainerID = snapshot.key
                            this@Client.classesReservades?.plusAssign(gymClass)
                            trainer?.addReservedClass(gymClass)
                            trainerDB.setValue(trainer)
                            userClient.setValue(this@Client)
                        }
                    }
                }

            })
        }else {
            println("No trainer syncronized")
        }
    }

    fun getFoodListonDay(date: String) : List<Food> {
        val foodForDay : MutableList<Food> = ArrayList()
        for (food in this.foodlist!!) {
            if (food.date == date) {
                foodForDay += food
            }
        }
        return foodForDay
    }


}








