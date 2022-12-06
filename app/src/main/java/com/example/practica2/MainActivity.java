package com.example.practica2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import adapter.GridCeldasRecyclerAdapter;
import celda.Celda;
import celda.GridCeldas;
import celda.OnCeldaClickListener;

public class MainActivity extends AppCompatActivity implements OnCeldaClickListener {

    private GridCeldasRecyclerAdapter adapter;
    private RecyclerView gridRecyclerView;
    private final GridCeldas gridCeldas = new GridCeldas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridRecyclerView = findViewById(R.id.gridRecyclerView);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem m){

        int valor = m.getItemId();

        if(valor == R.id.item1){

            try{

                gridCeldas.generarGrid();

                gridRecyclerView.setLayoutManager(new GridLayoutManager(this, gridCeldas.getTamano()));

                List<Celda> celdas = gridCeldas.getCeldas();

                adapter = new GridCeldasRecyclerAdapter(celdas, this);

                gridRecyclerView.setAdapter(adapter);


            }catch(Exception e){
                 System.out.println(e.getMessage());
            }

            return true;
        }

        if(valor == R.id.item2){
            configurarJuego();
            return true;
        }

        if(valor == R.id.item3){
            System.out.println(getString(R.string.seleccionarPersonaje));
            return true;
        }

        if(valor == R.id.item4){
            mostrarInstrucciones();
            return true;
        }

        return super.onOptionsItemSelected(m);

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    public void mostrarPerderPartida() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getString(R.string.perdido));
        dialogo.setMessage(getString(R.string.reiniciar));
        dialogo.setCancelable(false);
        dialogo.setPositiveButton(getString(R.string.aceptar), (dialogo1, id) -> {
            gridCeldas.reiniciarPartida();
            adapter.notifyDataSetChanged();

        });
        dialogo.setNegativeButton(getString(R.string.cancelar), (dialogo1, id) -> {
            // cancelar();
        });
        dialogo.show();
    }


    public void mostrarInstrucciones() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getString(R.string.instrucciones));
        dialogo.setMessage(getString(R.string.instruccionesDetallado));
        dialogo.setCancelable(false);
        dialogo.setPositiveButton(getString(R.string.aceptar), (dialogo1, id) -> {
            //aceptar();
        });
        dialogo.show();
    }

    public void configurarJuego(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(R.string.seleccionaModoJuego)
                .setItems(R.array.configurarJuego2, (dialog, which) -> gridCeldas.setModoJuego(which));

        dialogo.show();
    }


    @Override
    public void onCeldaClick(int posicion) {

        Celda celda = gridCeldas.getCelda(posicion);

        if(celda == null){
            return;
        }

        if(gridCeldas.getPartidaFinalizada()) {
            mostrarPerderPartida();
            return;
        }

        if (celda.getRevelado()) {
            return;
        }

        int fila = celda.getFila();
        int columna = celda.getColumna();

        int proximos = gridCeldas.proximidadHipotenochas(fila,columna);



        celda.setRevelado(true);
        celda.setNumHipotenochas(proximos);

        adapter.notifyDataSetChanged();

        if (celda.getHipotenocha()) {
            gridCeldas.setPartidaFinalizada(true);
            mostrarPerderPartida();
        }

    }

    @Override
    public void onCeldaLongClick(int posicion) {

        System.out.println(posicion);

    }
}