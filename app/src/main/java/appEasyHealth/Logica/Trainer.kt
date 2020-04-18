package com.example.appEasyHealth

class Trainer constructor(private var id: Int, private var suscription: String, private var notificacio: Boolean,
                          private var llistaClients: ArrayList<Client>, private var classesReservades: ArrayList<ReservedClasses>) {

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

    fun setLlistaClients(llistaClients: ArrayList<Client>) {
        this.llistaClients = llistaClients
    }

    fun getLlistaClients(): ArrayList<Client> {
        return this.llistaClients
    }

    fun setClassesReservades(classesReservades: ArrayList<ReservedClasses>) {
        this.classesReservades = classesReservades
    }

    fun getClassesReservades(): ArrayList<ReservedClasses> {
        return this.classesReservades
    }

    // fun addClient
    // fun deleteClient
    // fun addClassesRev
    // fun deleteClassesRev
    // fun addPersonalDiet
    // fun removePersonalDiet

}








