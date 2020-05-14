package appEasyHealth.Logica

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.example.appEasyHealth.Food
import com.example.easyhealth.R
import javax.sql.DataSource

class FoodAdapter(private val context: Context, private val dataSource: List<Food>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.food_layout,parent,false)

        val nameTextView = rowView.findViewById(R.id.txtName) as TextView

        val caloriesTextView = rowView.findViewById(R.id.txtCal) as TextView

        val foodImage = rowView.findViewById(R.id.imageFood) as ImageView

        val food = getItem(position) as Food

        nameTextView.text = food.name
        caloriesTextView.text = food.calories.toString()
        foodImage.setImageURI(food.path?.toUri())

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