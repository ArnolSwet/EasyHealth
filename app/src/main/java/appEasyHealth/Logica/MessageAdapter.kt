package appEasyHealth.Logica

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.my_message.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.appEasyHealth.Client
import com.example.appEasyHealth.Trainer
import com.example.easyhealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MessageAdapter(private val context: Context,private val dataSource: ArrayList<Message>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var rowView = inflater.inflate(R.layout.their_message,parent,false)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")
        auth = FirebaseAuth.getInstance()
        val message = getItem(position) as Message
        val user:FirebaseUser? = auth.currentUser
        if (user?.uid!! == message.originID) {
            rowView = inflater.inflate(R.layout.my_message,parent,false)

        } else if (user.uid == message.destinyID) {
            message.visto = true
        }
        val messageView = rowView.findViewById(R.id.message_body) as TextView
        messageView.text = message.text

        return rowView

    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}