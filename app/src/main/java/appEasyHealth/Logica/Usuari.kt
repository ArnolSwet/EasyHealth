package appEasyHealth.Logica

import com.example.appEasyHealth.Client
import com.example.appEasyHealth.ReservedClasses
import com.example.appEasyHealth.Trainer
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
open class Usuari(
    open var name: String? = "",
    open var username: String? = "",
    open var email: String? = "",
    open var id: String? = "",
    open var notif: Boolean? = false,
    open var type: String? = "",
    open var location: String? = "",
    open var classesReservades : MutableList<GymClass>? = ArrayList()
) {

    open fun addReservedClass(rClass:GymClass){
        this.classesReservades?.plusAssign(rClass)

    }
    fun deleteReservedClass(rClass: GymClass) {
        this.classesReservades?.remove(rClass)
    }

    fun getClassesOnDay(date: String): List<GymClass>{
        val classForDay : MutableList<GymClass> = ArrayList()
        for (gymClass in this.classesReservades!!) {
            if (gymClass.date == date) {
                classForDay += gymClass
            }
        }
        return classForDay
    }

}