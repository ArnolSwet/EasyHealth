package appEasyHealth.Logica

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import appEasyHealth.Client.FoodClient
import com.example.appEasyHealth.ClientForTrainer
import com.example.easyhealth.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class FoodForClientAdapter(
    private val cContext: Context,
    private val foodNames: ArrayList<String>
) : RecyclerView.Adapter<FoodForClientAdapter.ViewHolder>() {
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
        holder.image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                var intent = Intent(cContext, FoodClient::class.java)
                intent.putExtra("Client", foodNames[position])
                startActivity(cContext, intent, Bundle())
            }
        })
    }

    override fun getItemCount(): Int {
        return foodNames.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var image: CircleImageView
        var name: TextView

        init {
            image = itemView.findViewById(R.id.imageFood)
            name = itemView.findViewById(R.id.nameFood)
        }
    }

}