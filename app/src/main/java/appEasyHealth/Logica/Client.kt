package com.example.appEasyHealth

class Client constructor(private var id: Int, private var suscription: String, private var high: Float,
                         private var weight: Float, private var llistaMenjar: ArrayList<Food>, private var classesReservades: ArrayList<ReservedClasses>, private var notific: Boolean) {

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return this.id
    }

    fun setSuscription(id: String) {
        this.suscription = suscription
    }

    fun getSuscription(): String {
        return this.suscription
    }


    fun setHigh(high: Float) {
        this.high = high
    }

    fun getHigh(): Float {
        return this.high
    }

    fun setWeight(weight: Float) {
        this.weight = weight
    }

    fun getWeight(): Float {
        return this.weight
    }

    fun setNotif(notific: Boolean) {
        this.notific = notific
    }

    fun getNotific(): Boolean {
        return this.notific
    }

    // fun addFood
    // fun deleteFood
    // fun addClasses
    // fun removeClasses










}








