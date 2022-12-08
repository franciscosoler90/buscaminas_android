package com.example.practica2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

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
                gridCeldas.setPartidaFinalizada(false);

                gridRecyclerView.setLayoutManager(new GridLayoutManager(this, gridCeldas.getTamano()));

                List<Celda> celdas = gridCeldas.getCeldas();

                adapter = new GridCeldasRecyclerAdapter(celdas, this);

                gridRecyclerView.setAdapter(adapter);

                setTextHipotenochas();

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
            seleccionarPersonaje();
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


    public void seleccionarPersonaje(){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getString(R.string.seleccionarPersonaje));
        dialogo.setCancelable(false);
        dialogo.setPositiveButton(getString(R.string.aceptar), (dialogo1, id) -> {


        });
        dialogo.setNegativeButton(getString(R.string.cancelar), (dialogo1, id) -> {
            // cancelar();
        });
        dialogo.show();

    }


    public void mostrarPerderPartida() {

        gridCeldas.setPartidaFinalizada(true);
        gridCeldas.revelarHipotenochas();
        adapter.notifyDataSetChanged();

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getString(R.string.perdido));
        dialogo.setMessage(getString(R.string.reiniciar));
        dialogo.setCancelable(false);
        dialogo.setPositiveButton(getString(R.string.aceptar), (dialogo1, id) -> {
            gridCeldas.reiniciarPartida();
            setTextHipotenochas();
            adapter.notifyDataSetChanged();

        });
        dialogo.setNegativeButton(getString(R.string.cancelar), (dialogo1, id) -> {
            // cancelar();
        });
        dialogo.show();
    }


    public void mostrarGanarPartida() {

        gridCeldas.setPartidaFinalizada(true);
        gridCeldas.revelarHipotenochas();
        adapter.notifyDataSetChanged();

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getString(R.string.ganado));
        dialogo.setMessage(getString(R.string.reiniciar));
        dialogo.setCancelable(false);
        dialogo.setPositiveButton(getString(R.string.aceptar), (dialogo1, id) -> {
            gridCeldas.reiniciarPartida();
            setTextHipotenochas();
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

        Celda celda1 = gridCeldas.getCelda(posicion);

        if(celda1 == null){
            return;
        }

        if (celda1.getRevelado()) {
            return;
        }

        if(gridCeldas.getPartidaFinalizada()) {
            return;
        }

        int fila = celda1.getFila();
        int columna = celda1.getColumna();
        int proximos = gridCeldas.proximidadHipotenochas(fila,columna);
        celda1.setRevelado(true);

        if (celda1.getHipotenocha()) {

            mostrarPerderPartida();

        }else if(proximos == 0) {

                List<Celda> toClear = new ArrayList<>();
                List<Celda> toCheckAdjacents = new ArrayList<>();
                toCheckAdjacents.add(celda1);

                while (toCheckAdjacents.size() > 0) {

                    Celda celda2 = toCheckAdjacents.get(0);

                    int[] celdaEnPosicion = gridCeldas.toXY(celda2.getPosicion());

                    for (Celda adjacent : gridCeldas.getCeldasProximas(celdaEnPosicion[0], celdaEnPosicion[1])) {

                        fila = adjacent.getFila();
                        columna = adjacent.getColumna();
                        proximos = gridCeldas.proximidadHipotenochas(fila,columna);

                        if (proximos == 0) {
                            if (!toClear.contains(adjacent)) {
                                if (!toCheckAdjacents.contains(adjacent)) {
                                    toCheckAdjacents.add(adjacent);
                                }
                            }
                        } else {
                            if (!toClear.contains(adjacent)) {
                                toClear.add(adjacent);
                            }
                        }
                    }
                    toCheckAdjacents.remove(celda2);
                    toClear.add(celda2);
                }

                for (Celda celda3 : toClear) {

                    if(!celda3.getHipotenocha()) {
                        celda3.setRevelado(true);
                    }

                }
        }

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onCeldaLongClick(int posicion) {

        Celda celda = gridCeldas.getCelda(posicion);

        if(celda == null){
            return;
        }

        if(celda.getMarcado()){
            return;
        }

        if(gridCeldas.getPartidaFinalizada()) {
            return;
        }

        if(celda.getHipotenocha()){

            celda.setRevelado(true);
            celda.setMarcado(true);

            gridCeldas.disminuirHipotenochas();
            setTextHipotenochas();

            adapter.notifyDataSetChanged();

            if(gridCeldas.getHipotenochas() == 0) {
                mostrarGanarPartida();
            }

        }else{
            mostrarPerderPartida();
        }

    }


    public void setTextHipotenochas(){

        try {
            int NHipotenochas = gridCeldas.getHipotenochas();
            TextView texto = findViewById(R.id.textViewNHipotenochas);
            texto.setText(String.valueOf(NHipotenochas));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}