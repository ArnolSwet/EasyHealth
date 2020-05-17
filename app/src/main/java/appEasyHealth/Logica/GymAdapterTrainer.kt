package appEasyHealth.Logica

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.appEasyHealth.Trainer
import com.example.easyhealth.R

class GymAdapterTrainer(private val context: Context, private val dataSource: List<GymClass>, private var trainer: Trainer) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.class_layout,parent,false)

        val hourTextView = rowView.findViewById(R.id.txtTime) as TextView

        val clientTextView = rowView.findViewById(R.id.txtClient) as TextView

        val cancelClass = rowView.findViewById(R.id.btnCancel) as ImageButton

        val gymClass = getItem(position) as GymClass


        hourTextView.text = gymClass.hora
        clientTextView.text = gymClass.disponibilitat
        cancelClass.setOnClickListener{
            gymClass.disponibilitat = "Available"
            gymClass.clientID = ""
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