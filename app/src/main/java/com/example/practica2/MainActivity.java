package com.example.practica2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.GridCeldasRecyclerAdapter;
import adapter.PersonajeAdaptador;
import celda.Celda;
import celda.GridCeldas;
import celda.OnCeldaClickListener;

public class MainActivity extends AppCompatActivity implements OnCeldaClickListener {

    //Declaracion de las variables globales
    private GridCeldasRecyclerAdapter adapter;
    private RecyclerView gridRecyclerView;
    private final GridCeldas gridCeldas = new GridCeldas();
    private final int[] iconos = {R.drawable.conejo, R.drawable.perro, R.drawable.gato};
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //desactiva el modo oscuro de la aplicacion
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //busca el recyclerview con el determinado id
        gridRecyclerView = findViewById(R.id.gridRecyclerView);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        //inicializa la variable menu
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem m) {

        //obtiene el valor entero del item seleccionado
        int valor = m.getItemId();

        //Iniciar juego
        if (valor == R.id.item1) {
            iniciarJuego();
            return true;
        }

        //Configurar juego
        if (valor == R.id.item2) {
            configurarJuego();
            return true;
        }

        //seleccionar personaje
        if (valor == R.id.item3) {
            seleccionarPersonaje();
            return true;
        }

        //mostrar instrucciones
        if (valor == R.id.item4) {
            mostrarInstrucciones();
            return true;
        }

        //valor por defecto
        return super.onOptionsItemSelected(m);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    //función que inicializa el juego, con el tablero correspondiente y el adaptador
    public void iniciarJuego() {
        try {

            //nueva partida, por lo que partida finalizada se establece a false
            gridCeldas.setPartidaFinalizada(false);

            //genera una lista de celdas
            gridCeldas.generarGrid();

            //obtiene la lista generada de la clase gridCeldas
            List<Celda> celdas = gridCeldas.getCeldas();


            //le pasa el diseño estilo grid layout al recycler view, con un determinado tamaño
            gridRecyclerView.setLayoutManager(new GridLayoutManager(this, gridCeldas.getTamano()));

            //inicializa el adaptador pasandole la lista de celdas y el listener
            adapter = new GridCeldasRecyclerAdapter(celdas, this);

            //le pasa el adapter al gridRecyclerView
            gridRecyclerView.setAdapter(adapter);

            //establece el icono de animal por defecto en la action bar
            adapter.setPersonaje(iconos[0]);

            //escribe el número restante de animales
            setTextAnimales();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //funcion que muestra el dialogo al perder la partida
    public void mostrarPerderPartida() {

        //partida finalizada a true
        gridCeldas.setPartidaFinalizada(true);

        //establece todas las celdas como reveladas
        gridCeldas.revelarAnimales();

        //actualiza todas las celdas
        adapter.notifyDataSetChanged();

        //inicializa un AlertDialog
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        //titulo
        dialogo.setTitle(getString(R.string.perdido));

        //escribe un mensaje al dialogo
        dialogo.setMessage(getString(R.string.reiniciar));

        //no es cancelable fuera de la ventana de dialogo
        dialogo.setCancelable(false);

        //funcion al pulsar el boton de aceptar
        dialogo.setPositiveButton(getString(R.string.aceptar), (dialogo1, id) -> {

            //llama a la funcion reiniciar partida del objeto gridCeldas
            gridCeldas.reiniciarPartida();

            //actualiza todas las celdas
            adapter.notifyDataSetChanged();

            //escribe el número restante de animales
            setTextAnimales();

        });

        //funcion al pulsar el boton de cancelar
        dialogo.setNegativeButton(getString(R.string.cancelar), (dialogo1, id) -> {
            // no hace nada
        });

        //muestra el dialogo
        dialogo.show();
    }


    public void mostrarGanarPartida() {

        //partida finalizada a true
        gridCeldas.setPartidaFinalizada(true);

        //llama a la funcion revelar animales del objeto gridCeldas
        gridCeldas.revelarAnimales();

        //actualiza todas las celdas
        adapter.notifyDataSetChanged();

        //inicializa un AlertDialog
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        //titulo
        dialogo.setTitle(getString(R.string.ganado));

        //escribe un mensaje al dialogo
        dialogo.setMessage(getString(R.string.reiniciar));

        //no es cancelable fuera de la ventana de dialogo
        dialogo.setCancelable(false);

        //funcion al pulsar el boton de aceptar
        dialogo.setPositiveButton(getString(R.string.aceptar), (dialogo1, id) -> {

            //llama a la funcion reiniciar partida del objeto gridCeldas
            gridCeldas.reiniciarPartida();

            //escribe el número restante de animales
            setTextAnimales();

            //actualiza todas las celdas
            adapter.notifyDataSetChanged();

        });
        //funcion al pulsar el boton de cancelar
        dialogo.setNegativeButton(getString(R.string.cancelar), (dialogo1, id) -> {
            //no hace nada y sale y dialogo;
        });

        //muestra el dialogo
        dialogo.show();
    }


    public void mostrarInstrucciones() {

        //inicializa un AlertDialog
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        //titulo
        dialogo.setTitle(getString(R.string.instrucciones));

        //escribe un mensaje al dialogo
        dialogo.setMessage(getString(R.string.instruccionesDetallado));

        //es cancelable fuera de la ventana de dialogo
        dialogo.setCancelable(true);

        //funcion al pulsar el boton de aceptar
        dialogo.setPositiveButton(getString(R.string.aceptar), (dialogo1, id) -> {
            //no hace nada y sale y dialogo;
        });

        //muestra el dialogo
        dialogo.show();
    }

    public void configurarJuego() {

        //inicializa un AlertDialog
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        //titulo
        dialogo.setTitle(R.string.seleccionaModoJuego)
                .setItems(R.array.configurarJuego2, (dialog, which) -> gridCeldas.setModoJuego(which));

        //valor por defecto
        int checkedItem = 0;

        //establece los items, desde el array string de strings.xml
        //a continuacion se hace una llamada a una funcion lambda
        dialogo.setSingleChoiceItems(R.array.configurarJuego2, checkedItem, (dialog, which) -> {

            //establece un modo de juego obteniendo el valor (which) escogido
            gridCeldas.setModoJuego(which);

            //llama a la funcion juego
            iniciarJuego();
        });

        //no es cancelable fuera de la ventana de dialogo
        dialogo.setCancelable(false);

        //funcion al pulsar el boton de aceptar
        dialogo.setPositiveButton(getString(R.string.aceptar), (dialogo1, id) -> {
            //no hace nada;
        });

        //muestra el dialogo
        dialogo.show();
    }


    //clic corto
    @Override
    public void onCeldaClick(int posicion) {

        //obtiene el objeto en la determinada posicion
        Celda celda1 = gridCeldas.getCelda(posicion);

        //aborta la ejecucion si es nulo
        if (celda1 == null) {
            return;
        }

        //aborta si ya ha sido revelado anteriormente
        if (celda1.getRevelado()) {
            return;
        }

        //aborta si la partida ha sido finalizada
        if (gridCeldas.getPartidaFinalizada()) {
            return;
        }

        //obtiene el valor de la fila
        int fila = celda1.getFila();

        //obtiene el valor de la columna
        int columna = celda1.getColumna();

        //obtiene el número de animales proximos a la celda
        int proximos = gridCeldas.proximidadAnimales(fila, columna);

        //establece como revelado
        celda1.setRevelado(true);

        //si hay un animal
        if (celda1.getAnimal()) {

            //perder partida
            mostrarPerderPartida();

            // si no hay animal y el número de proximos es 0
        } else if (proximos == 0) {

            //establece dos listas vacias
            List<Celda> listaVaciar = new ArrayList<>();
            List<Celda> listaProximos = new ArrayList<>();

            //le añade la celda actual que ha hecho clic
            listaProximos.add(celda1);

            //mientras la lista contenga celdas
            while (listaProximos.size() > 0) {

                //obtiene el primer objeto celda de la lista
                Celda celda2 = listaProximos.get(0);

                //bucle for que va recorriendo todas las celdas proximas a la celda actual
                for (Celda celdaProxima : gridCeldas.getCeldasProximas(celda2.getColumna(), celda2.getFila())) {

                    //número de celdas que contienen animales
                    proximos = gridCeldas.proximidadAnimales(celdaProxima.getFila(), celdaProxima.getColumna());

                    //si no hay animales proximos
                    if (proximos == 0) {

                        //si en la lista vaciar no contiene la celda
                        if (!listaVaciar.contains(celdaProxima)) {

                            //si en la lista proximos no contiene la celda
                            if (!listaProximos.contains(celdaProxima)) {

                                //se añade a la lista proximos
                                listaProximos.add(celdaProxima);
                            }
                        }

                        //si hay animales proximos
                    } else {

                        //si en la lista vaciar no contiene la celda
                        if (!listaVaciar.contains(celdaProxima)) {

                            //se añade a la lista vaciar
                            listaVaciar.add(celdaProxima);
                        }
                    }
                }

                //se elimina de la lista proximos
                listaProximos.remove(celda2);

                //se añade a la lista vaciar
                listaVaciar.add(celda2);
            }

            //otro bucle for que recorre las lista de celdas que no contienen animales y son contiguas a la celda clicada
            for (Celda celda3 : listaVaciar) {


                if (!celda3.getAnimal()) {
                    celda3.setRevelado(true);
                }

            }
        }

        //actualiza todas las celdas
        adapter.notifyDataSetChanged();

    }

    //clic largo
    @Override
    public void onCeldaLongClick(int posicion) {

        //obtiene el objeto en la determinada posicion
        Celda celda = gridCeldas.getCelda(posicion);

        //aborta la ejecucion si es nulo
        if (celda == null) {
            return;
        }

        //aborta si ya ha sido revelado anteriormente
        if (celda.getRevelado()) {
            return;
        }

        //aborta si la partida ha sido finalizada
        if (gridCeldas.getPartidaFinalizada()) {
            return;
        }

        //si la celda contiene un animal
        if (celda.getAnimal()) {

            //revelado
            celda.setRevelado(true);

            //marcado
            celda.setMarcado(true);

            //disminuir el numero de animales revelados
            gridCeldas.disminuirAnimales();

            //escribe el numero restantes de animales
            setTextAnimales();

            //actualiza todas las celdas
            adapter.notifyDataSetChanged();

            //si el numero de animales es 0
            if (gridCeldas.getAnimales() == 0) {
                //ganar la partida
                mostrarGanarPartida();
            }

            //si no es un animal
        } else {
            //perder la partida
            mostrarPerderPartida();
        }

    }


    //funcion que actualiza el texto de animales restantes
    public void setTextAnimales() {

        try {
            //obtiene el numero de animales
            int numeroAnimales = gridCeldas.getAnimales();

            //obtiene el textview
            TextView texto = findViewById(R.id.textViewNHipotenochas);

            //casteo de int a string
            texto.setText(String.valueOf(numeroAnimales));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    public void seleccionarPersonaje() {

        //crea una instancia de la clase AlertDialog
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        //crea una lista de opciones para el spinner
        List<String> opciones = Arrays.asList(getResources().getStringArray(R.array.configurarPersonaje));

        //crea un adaptador para el spinner usando la lista de opciones
        PersonajeAdaptador personajeAdaptador = new PersonajeAdaptador(opciones, iconos);

        //establece el adaptador en el spinner
        Spinner spinner = new Spinner(this);
        spinner.setAdapter(personajeAdaptador);

        //agrega el spinner al diálogo
        dialogo.setView(spinner);

        //establece un título para el diálogo
        dialogo.setTitle(R.string.seleccionarPersonaje);

        //no es cancelable fuera de la ventana de dialogo
        dialogo.setCancelable(false);

        //listener del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                try {
                    //obtiene el item del menu que contiene el icono de los animales
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), iconos[pos]));

                    //establece la imagen al icono
                    adapter.setPersonaje(iconos[pos]);

                    //actualiza todas las celdas del tablero
                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
                //no hace nada
            }
        });

        //funcion al pulsar el boton de aceptar
        dialogo.setPositiveButton(R.string.aceptar, (dialog, id) -> {
            //no hace nada
        });


        //muestra el dialogo
        dialogo.show();

    }

}