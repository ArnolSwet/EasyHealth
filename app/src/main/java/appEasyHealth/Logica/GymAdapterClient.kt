package appEasyHealth.Logica

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.example.appEasyHealth.Client
import com.example.appEasyHealth.Food
import com.example.easyhealth.R

class GymAdapterClient(private val context: Context, private val dataSource: List<GymClass>, private var client: Client) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.class_client_layout,parent,false)

        val hourTextView = rowView.findViewById(R.id.txtTime) as TextView

        val availabilityTextView = rowView.findViewById(R.id.txtClient) as TextView

        val addClass = rowView.findViewById(R.id.btnafegir) as ImageButton

        val gymClass = getItem(position) as GymClass

        if (gymClass.disponibilitat.equals("Unavailable")) {
            addClass.visibility = View.GONE
        }
        hourTextView.text = gymClass.hora
        if (client.exists(gymClass)) {
            availabilityTextView.text = "Reserved"
        } else{
            availabilityTextView.text = gymClass.disponibilitat
        }
        addClass.setOnClickListener{
            gymClass.disponibilitat = "Unavailable" 
            client.addReservedClass(gymClass)
            availabilityTextView.text = "Reserved"
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