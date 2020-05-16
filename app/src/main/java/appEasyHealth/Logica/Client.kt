package com.example.appEasyHealth

import appEasyHealth.Logica.GymClass
import appEasyHealth.Logica.Usuari

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
    var foodlist: MutableList<Food>? = ArrayList()
    ) : Usuari(name, username, email, id) {


    fun addFood(food: Food) {
        this.foodlist?.plusAssign(food)
    }

    fun deleteFood(food: Food) {
        this.foodlist?.remove(food)
    }
    fun addReservedClass(hora:String, date: String) {
        val gymClass : GymClass? = GymClass(hora,date, this.trainer,this)
        if (gymClass?.trainer != null) {
            super.addReservedClass(gymClass)
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








