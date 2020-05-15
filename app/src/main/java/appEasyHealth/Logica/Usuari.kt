package appEasyHealth.Logica

import com.example.appEasyHealth.ReservedClasses
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
open class Usuari(
    open var name: String? = "",
    open var username: String? = "",
    open var email: String? = "",
    open var id: String? = "",
    open var notif: Boolean? = false
) {
    private val classesReservades: MutableList<GymClass> = ArrayList()

    open fun addReservedClass(rClass:GymClass){
        this.classesReservades += rClass
    }
    fun deleteReservedClass(rClass: GymClass) {
        this.classesReservades.remove(rClass)
    }

    fun getReservedClasses(date: String): List<GymClass>{
        val classForDay : MutableList<GymClass> = ArrayList()
        for (gymClass in this.classesReservades) {
            if (gymClass.date == date) {
                classForDay += gymClass
            }
        }
        return classForDay
    }

}