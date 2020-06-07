package appEasyHealth.Logica

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.appEasyHealth.Client
import com.example.appEasyHealth.Food
import com.example.easyhealth.R

class FoodAdapter(private val context: Context, private val dataSource: List<Food>, private val client: Client) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.food_layout,parent,false)

        var nameTextView = rowView.findViewById(R.id.txtNameMeal) as TextView
        var caloriesTextView = rowView.findViewById(R.id.txtCalMeal) as TextView
        var foodImage = rowView.findViewById(R.id.imageFoodMeal) as ImageView
        var deleteFood = rowView.findViewById(R.id.btnCancelMeal) as ImageButton
        var food = getItem(position) as Food

        nameTextView.text = food.name
        caloriesTextView.text = food.calories.toString()

        val img_bfst: Int = R.drawable.breakfast
        val img_lun: Int = R.drawable.lunch
        val img_din: Int = R.drawable.dinner
        val img_sna: Int = R.drawable.snack

        if (food.tipus == "Breakfast") {
            foodImage.setImageResource(img_bfst)
        }
        if (food.tipus == "Lunch") {
            foodImage.setImageResource(img_lun)
        }
        if (food.tipus == "Dinner") {
            foodImage.setImageResource(img_din)
        }
        if (food.tipus == "Snack") {
            foodImage.setImageResource(img_sna)
        }

        deleteFood.setOnClickListener{
            client.deleteFood(food)

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