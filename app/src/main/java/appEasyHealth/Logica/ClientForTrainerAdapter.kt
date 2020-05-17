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
import com.example.appEasyHealth.ClientForTrainer
import com.example.easyhealth.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class ClientForTrainerAdapter(
    private val cContext: Context,
    private val clientNames: ArrayList<String>
) : RecyclerView.Adapter<ClientForTrainerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.clientscroll_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.name.text = clientNames[position]
        holder.image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                Toast.makeText(cContext, clientNames[position], Toast.LENGTH_SHORT).show()
                var intent = Intent(cContext, ClientForTrainer::class.java)
                intent.putExtra("Client", clientNames[position])
                startActivity(cContext, intent, Bundle())
            }
        })
    }

    override fun getItemCount(): Int {
        return clientNames.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var image: CircleImageView
        var name: TextView

        init {
            image = itemView.findViewById(R.id.imageClient)
            name = itemView.findViewById(R.id.nameClient)
        }
    }

}