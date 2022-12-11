package adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private int personaje;
    private int tamano;

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

    public void setPersonaje(int personaje){
        this.personaje = personaje;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    @Override
    public void onBindViewHolder(@NonNull GridCeldasRecyclerAdapter.GridCeldasViewHolder holder, int position) {

        //llama a la funcion bind del viewHolder
        holder.bind(celdas.get(position));

        //establece como no reciclable
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return celdas.size();
    }


    class GridCeldasViewHolder extends RecyclerView.ViewHolder {
        final ImageButton botonCelda;
        final LinearLayout layout;

        //Muestra el texto
        public GridCeldasViewHolder(@NonNull View itemView) {
            super(itemView);
            botonCelda = itemView.findViewById(R.id.buttonCelda);
            layout = itemView.findViewById(R.id.layoutCelda);
        }

        public void bind(final Celda celda) {

            //ajusta el tamaño del tablero
            //amateur
            if(tamano == 12){
                layout.setLayoutParams(new ViewGroup.LayoutParams(80, 80));
            }

            //avanzado
            if(tamano == 16){
                layout.setLayoutParams(new ViewGroup.LayoutParams(60, 60));
            }

            //si esta marcado
            if(celda.getMarcado()){

                //color gris oscuro
                botonCelda.setBackgroundColor(Color.DKGRAY);

                //imagen del animal
                botonCelda.setImageResource(personaje);
                return;
            }

            //si esta revelado
            if( celda.getRevelado() ){

                //si hay animal
                if(celda.getAnimal()) {

                    //color negro
                    botonCelda.setBackgroundColor(Color.BLACK);

                }else{

                    //obtener el numero de animales
                    int proximos = celda.getNumeroAnimales();

                    switch(proximos){

                        case 0:
                            botonCelda.setBackgroundColor(Color.parseColor("#9ACD84"));
                            break;

                        case 1:
                            botonCelda.setBackgroundColor(Color.parseColor("#3D7F34"));
                            botonCelda.setImageResource(R.drawable.uno);
                            botonCelda.setColorFilter(Color.WHITE);
                            break;

                        case 2:
                            botonCelda.setBackgroundColor(Color.parseColor("#9A6A19"));
                            botonCelda.setImageResource(R.drawable.dos);
                            botonCelda.setColorFilter(Color.WHITE);
                            break;

                        case 3:
                            botonCelda.setBackgroundColor(Color.parseColor("#DB371A"));
                            botonCelda.setImageResource(R.drawable.tres);
                            botonCelda.setColorFilter(Color.WHITE);
                            break;

                        case 4:
                            botonCelda.setBackgroundColor(Color.parseColor("#C03016"));
                            botonCelda.setImageResource(R.drawable.cuatro);
                            botonCelda.setColorFilter(Color.WHITE);
                            break;

                        case 5:
                            botonCelda.setBackgroundColor(Color.parseColor("#B72E15"));
                            botonCelda.setImageResource(R.drawable.cinco);
                            botonCelda.setColorFilter(Color.WHITE);
                            break;

                        case 6:
                            botonCelda.setBackgroundColor(Color.parseColor("#AD2B14"));
                            botonCelda.setImageResource(R.drawable.seis);
                            botonCelda.setColorFilter(Color.WHITE);
                            break;

                        case 7:
                            botonCelda.setBackgroundColor(Color.parseColor("#A42913"));
                            botonCelda.setImageResource(R.drawable.siete);
                            botonCelda.setColorFilter(Color.WHITE);
                            break;

                        case 8:
                            botonCelda.setBackgroundColor(Color.parseColor("#9B2712"));
                            botonCelda.setImageResource(R.drawable.ocho);
                            botonCelda.setColorFilter(Color.WHITE);
                            break;

                    }


                }

                //en caso contrario, pone la celda en el color por defecto
            }else{

                //color gris claro
                botonCelda.setBackgroundColor(Color.GRAY);
            }

            //funcion de clic corto
            botonCelda.setOnClickListener(view -> {
                //si el listener no es nulo
                if(listener != null){
                    //obtiene la posicion del adapter
                    int posicion = getAdapterPosition();
                    //si la posicion no corresponde con la del recyclerview
                    if(posicion != RecyclerView.NO_POSITION){
                        //llama a la funcion clic corto
                        listener.onCeldaClick(posicion);
                    }
                }
            });

            //funcion de clic largo
            botonCelda.setOnLongClickListener(view -> {
                //si el listener no es nulo
                if(listener != null){
                    //obtiene la posicion del adapter
                    int posicion = getAdapterPosition();
                    //si la posicion no corresponde con la del recyclerview
                    if(posicion != RecyclerView.NO_POSITION){
                        //llama a la funcion clic largo
                        listener.onCeldaLongClick(posicion);
                    }
                }
                return true;
            });

        }

    }

}