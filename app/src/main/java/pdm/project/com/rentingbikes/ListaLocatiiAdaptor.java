package pdm.project.com.rentingbikes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pdm.project.com.rentingbikes.Clase.Locatie;

public class ListaLocatiiAdaptor extends RecyclerView.Adapter<ListaLocatiiAdaptor.ViewHolder> {

    private ArrayList<Locatie> locatii;

    public ListaLocatiiAdaptor(ArrayList<Locatie> locatii) {
        this.locatii = locatii;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.tvDenumireLocatie.setText(locatii.get(position).getDenumire());

        ///TODO Aici trebuie sa luam distanta si sa o punem
        holder.tvDistantaLocatie.setText("1");
    }

    @Override
    public int getItemCount() {
        return locatii.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvDistantaLocatie;
        TextView tvDenumireLocatie;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewRand);
            tvDistantaLocatie = itemView.findViewById(R.id.tvDistantaLocatie);
            tvDenumireLocatie = itemView.findViewById(R.id.tvLocatie);
        }
    }
}
