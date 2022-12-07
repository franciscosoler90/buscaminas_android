package adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica2.R;

import java.util.List;

import celda.Celda;
import celda.OnCeldaClickListener;

public class GridCeldasRecyclerAdapter extends ListAdapter<Celda, GridCeldasRecyclerAdapter.GridCeldasViewHolder> {

    public static final CeldaDiffUtil diffCallback = new CeldaDiffUtil();

    private final List<Celda> celdas;
    private final OnCeldaClickListener listener;

    public GridCeldasRecyclerAdapter(List<Celda> celdas, OnCeldaClickListener listener) {
        super(diffCallback);
        this.celdas = celdas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GridCeldasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_layout, parent, false);
        return new GridCeldasViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull GridCeldasRecyclerAdapter.GridCeldasViewHolder holder, int position) {
        holder.bind(celdas.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return celdas.size();
    }


    class GridCeldasViewHolder extends RecyclerView.ViewHolder {
        Button botonCelda;
        LinearLayout layout;

        //Muestra el texto
        public GridCeldasViewHolder(@NonNull View itemView) {
            super(itemView);
            botonCelda = itemView.findViewById(R.id.buttonCelda);
            layout = itemView.findViewById(R.id.layoutCelda);
        }

        public void bind(final Celda celda) {

            if(celda.getMarcado()){
                botonCelda.setBackgroundColor(Color.DKGRAY);
                botonCelda.setText("H");
                return;
            }

            if( celda.getRevelado() ){

                if(celda.getHipotenocha()) {

                    botonCelda.setBackgroundColor(Color.BLACK);

                }else{

                    int proximos = celda.getNumHipotenochas();

                    switch(proximos){

                        case 0:
                            botonCelda.setBackgroundColor(Color.parseColor("#9ACD84"));
                            break;

                        case 1:
                            botonCelda.setBackgroundColor(Color.parseColor("#3D7F34"));
                            break;

                        case 2:
                            botonCelda.setBackgroundColor(Color.parseColor("#9A6A19"));
                            break;

                        case 3:
                            botonCelda.setBackgroundColor(Color.parseColor("#DB371A"));
                            break;

                        case 4:
                            botonCelda.setBackgroundColor(Color.parseColor("#C03016"));
                            break;

                        case 5:
                            botonCelda.setBackgroundColor(Color.parseColor("#B72E15"));
                            break;

                        case 6:
                            botonCelda.setBackgroundColor(Color.parseColor("#AD2B14"));
                            break;

                        case 7:
                            botonCelda.setBackgroundColor(Color.parseColor("#A42913"));
                            break;

                        case 8:
                            botonCelda.setBackgroundColor(Color.parseColor("#9B2712"));
                            break;

                    }

                    botonCelda.setText(String.valueOf(proximos));

                }

            }else{
                botonCelda.setBackgroundColor(Color.GRAY);
            }

            botonCelda.setOnClickListener(view -> {
                if(listener != null){
                    int posicion = getAdapterPosition();
                    if(posicion != RecyclerView.NO_POSITION){
                        listener.onCeldaClick(posicion);
                    }
                }
            });

            botonCelda.setOnLongClickListener(view -> {
                if(listener != null){
                    int posicion = getAdapterPosition();
                    if(posicion != RecyclerView.NO_POSITION){
                        listener.onCeldaLongClick(posicion);
                    }
                }
                return true;
            });

        }

    }

}