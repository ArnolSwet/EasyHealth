package appEasyHealth.Logica

import com.example.appEasyHealth.Client
import com.example.appEasyHealth.Trainer

class GymClass constructor(private var horari: String, private var tipus: String, private var material: ArrayList<String>, private var trainer: Trainer, private var client: Client, private var minSubscription: Int) {

    fun setHorari(horari: String) {
        this.horari = horari
    }

    fun getHorari(): String {
        return this.horari
    }

    fun setTipus(tipus: String) {
        this.tipus = tipus
    }

    fun getTipus(): String {
        return this.tipus
    }

    fun setMaterial(material: ArrayList<String>) {
        this.material = material
    }

    fun getMaterial(): ArrayList<String> {
        return this.material
    }

    fun setClient(client: Client) {
        this.client = client
    }

    fun getClient(): Client {
        return this.client
    }

    fun setTrainer(trainer: Trainer) {
        this.trainer = trainer
    }

    fun getTrainer(): Trainer {
        return this.trainer
    }

    fun setminSubscription(minSubscription: Int) {
        this.minSubscription = minSubscription
    }

    fun getminSubscription(): Int {
        return this.minSubscription
    }




}