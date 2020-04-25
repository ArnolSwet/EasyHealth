package appEasyHealth.Logica

import com.example.appEasyHealth.ReservedClasses

open class Usuari constructor(private var id: Int, private var suscription: String, private var classesReservades: ArrayList<ReservedClasses>, private var notificacio: Boolean) {

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return this.id
    }

    fun setSuscription(suscription: String) {
        this.suscription = suscription
    }

    fun getSuscription(): String {
        return this.suscription
    }

    fun setReservedClasses(classesReservades: ArrayList<ReservedClasses>){
        this.classesReservades = classesReservades
    }

    fun getReservedClasses(): ArrayList<ReservedClasses>{
        return this.classesReservades
    }

}