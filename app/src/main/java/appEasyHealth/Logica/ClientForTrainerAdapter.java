package appEasyHealth.Logica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easyhealth.R;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientForTrainerAdapter extends RecyclerView.Adapter<ClientForTrainerAdapter.ViewHolder> {

    //Variables
    private ArrayList<String> clientNames;
    private Context cContext;

    public ClientForTrainerAdapter(Context cContext, ArrayList<String> clientNames) {
        this.clientNames = clientNames;
        this.cContext = cContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clientscroll_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(clientNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cContext,clientNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageClient);
            name = itemView.findViewById(R.id.nameClient);
        }
    }

}
