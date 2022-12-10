package adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practica2.R;

import java.util.List;

public class PersonajeAdaptador extends BaseAdapter {
    final List<String> cantidad;
    final int[] icono;

    public PersonajeAdaptador(List<String> cantidad, int[] icono) {
        this.cantidad = cantidad;
        this.icono = icono;
    }

    //Metodo que devuelve el numero de elementos de la lista
    @Override
    public int getCount() {
        return cantidad.size();
    }

    //metodo que devuelve el objeto seleccionado cuando seleccionamos la posisión i
    @Override
    public Object getItem(int i) {
        return cantidad.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //Devuelve el view de cada item de la lista
    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //Creo un objeto de la clase Layout inflater
        LayoutInflater l=LayoutInflater.from(viewGroup.getContext());

        //genero el view inflando el LayoutInflater con el layaout itemlista
        view=l.inflate(R.layout.itemlista,null);

        //Leno el view con los valores
        TextView t=view.findViewById(R.id.texto);

        //escribe en el texto del elemento
        t.setText(cantidad.get(i));

        //iniciar ImageView
        ImageView imagen = view.findViewById(R.id.icono);

        //icono en las imagenes
        imagen.setImageResource(icono[i]);

        return view;
    }


}
