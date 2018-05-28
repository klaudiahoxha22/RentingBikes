package pdm.project.com.rentingbikes;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

import pdm.project.com.rentingbikes.Activities.MapActivity;
import pdm.project.com.rentingbikes.Activities.RentActivity;
import pdm.project.com.rentingbikes.Clase.Traseu;
import pdm.project.com.rentingbikes.DBConnection.DataBase;

public class ListaTraseeAdaptor extends RecyclerView.Adapter<ListaTraseeAdaptor.ViewHolder> {
    List<Traseu> trasee;

    public ListaTraseeAdaptor(List<Traseu> trasee) {
        this.trasee = trasee;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_trasee_row, parent, false);
        return new ListaTraseeAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapActivity.class);
                Traseu traseu = trasee.get(position);
                traseu.setListaPuncte(DataBase.getInstance(view.getContext()).getPuncte().getPunctePentruTraseul(traseu.getId()));
                intent.putExtra("traseu", traseu);
                view.getContext().startActivity(intent);
            }
        });
        if (holder.tvTraseuDistanta != null) {
            holder.tvTraseuDistanta.setText(trasee.get(position).getDenumire());
            String originalDate = String.valueOf(trasee.get(position).getDataStart());
            String date = originalDate.substring(3, 11);
            String an = originalDate.substring(originalDate.length() - 4, originalDate.length());
            holder.tvDataStart.setText(date + an);
        }
    }


    @Override
    public int getItemCount() {
        return trasee.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTraseuDistanta;
        TextView tvDataStart;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewRand);
            tvTraseuDistanta = itemView.findViewById(R.id.tvDistantaLocatie);
            tvDataStart = itemView.findViewById(R.id.tvTraseuStart);
        }
    }
}
