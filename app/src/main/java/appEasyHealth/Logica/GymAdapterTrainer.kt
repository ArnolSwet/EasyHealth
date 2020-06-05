package appEasyHealth.Logica

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.appEasyHealth.Client
import com.example.appEasyHealth.Trainer
import com.example.easyhealth.R
import com.google.firebase.database.*

class GymAdapterTrainer(private val context: Context, private val dataSource: List<GymClass>, private var trainer: Trainer) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.class_layout,parent,false)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("User")

        val hourTextView = rowView.findViewById(R.id.txtTime) as TextView

        val clientTextView = rowView.findViewById(R.id.txtClient) as TextView

        val cancelClass = rowView.findViewById(R.id.btnCancel) as ImageButton

        val gymClass = getItem(position) as GymClass

        var clientDB = databaseReference.child(gymClass.clientID!!)
        clientDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context,"Fail to read data", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val client = p0.getValue(Client::class.java)
                clientTextView.text = client!!.name
            }

        })
        hourTextView.text = gymClass.hora
        cancelClass.setOnClickListener{
            trainer.cancelReservedClass(gymClass)
        }
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