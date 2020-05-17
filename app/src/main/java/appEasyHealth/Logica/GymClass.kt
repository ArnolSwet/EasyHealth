package appEasyHealth.Logica

import com.example.appEasyHealth.Client
import com.example.appEasyHealth.Trainer

data class GymClass (
    var hora: String = "",
    var date: String = "",
    var trainerID: String? = "",
    var clientID: String? = "",
    var disponibilitat: String = "Available"
) {


}