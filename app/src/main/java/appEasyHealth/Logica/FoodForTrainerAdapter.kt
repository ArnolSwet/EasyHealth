package appEasyHealth.Logica

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import appEasyHealth.Client.FoodClient
import com.example.easyhealth.R
import java.util.*

class FoodForTrainerAdapter(
    private val cContext: Context,
    private val foodNames: ArrayList<String>,
    private val foodTypes: ArrayList<String>
) : RecyclerView.Adapter<FoodForTrainerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.foodscroll_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.name.text = foodNames[position]

        val img_bfst: Int = R.drawable.breakfast
        val img_lun: Int = R.drawable.lunch
        val img_din: Int = R.drawable.dinner
        val img_sna: Int = R.drawable.snack

        if (foodTypes[position] == "Breakfast") {
            holder.image.setImageResource(img_bfst)
        }
        if (foodTypes[position] == "Lunch") {
            holder.image.setImageResource(img_lun)
        }
        if (foodTypes[position] == "Dinner") {
            holder.image.setImageResource(img_din)
        }
        if (foodTypes[position] == "Snack") {
            holder.image.setImageResource(img_sna)
        }

        holder.image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                var intent = Intent(cContext, FoodClient::class.java)
                startActivity(cContext, intent, Bundle())
            }
        })
    }

    override fun getItemCount(): Int {
        return foodNames.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var name: TextView

        init {
            image = itemView.findViewById(R.id.imageFoodMeals)
            name = itemView.findViewById(R.id.nameFood)
        }
    }

}