package appEasyHealth.Logica

class Message {

    constructor() //empty for firebase

    constructor(messageText: String, origin: String, destiny: String){
        text = messageText
        originID = origin
        destinyID = destiny
    }
    var originID: String? = ""
    var destinyID: String? = ""
    var text: String? = null
    var visto: Boolean = false
    var timestamp: Long = System.currentTimeMillis()
}