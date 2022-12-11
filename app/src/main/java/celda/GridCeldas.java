package celda;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridCeldas {

    private final List<Celda> celdas = new ArrayList<>();

    private Integer animales;
    private Integer tamano;
    private Integer modoJuego = 0;

    private boolean partidaFinalizada;

    //constructor
    public GridCeldas(){
        this.tamano = 8;
        this.animales = 10;
    }

    //funcion que genera una lista de celdas
    public void generarGrid(){

        //inicializa lista
        ArrayList<Celda> nuevaLista = new ArrayList<>();

        //dependiendo del modo de juego, genera un grid con un tamaño determinado y el numero de animales
        switch (this.modoJuego){

                //principiante
            case 1:
                this.tamano = 12;
                this.animales = 30;
                break;
                //amateur
            case 2:
                this.tamano = 16;
                this.animales = 60;
                break;
                //avanzado
            default:
                this.tamano = 8;
                this.animales = 10;
                break;
        }

        int celdaId = 0;

        // recorre filas
        for(int fila = 0; fila < tamano; fila++){

            // recorre columnas
            for(int columna = 0; columna < tamano; columna++){

                //crea un objeto celda
                Celda celda = new Celda(celdaId,fila,columna);

                //añade a la lista
                nuevaLista.add(celda);

                //aumenta el valor de celdaId
                celdaId++;

            }

        }

        //Limpiar el ArrayList
        this.celdas.clear();
        this.celdas.addAll(nuevaLista);

        int animales = 0;

        //bucle while que genera los animales
        while (animales < this.animales) {

            //filas y columnas aleatorias dentro del rango del tamaño del tablero
            int fila = new Random().nextInt(tamano);
            int columna = new Random().nextInt(tamano);

            //crea un objeto celda
            Celda celda = getCelda(fila,columna);

            //si no hay un animal en la celda, lo pone a true
            if (!celda.getAnimal()) {
                celda.setAnimal(true);
                animales++;
            }
        }

        int proximos;

        //vuelve a recorrar la lista e introduce en cada celda el numero de celdas proximas
        for(Celda c : this.celdas){
            //obtiene el número de animales proximos a la celda
            proximos = proximidadAnimales(c.getFila(), c.getColumna());

            //le pasa el valor a la celda
            c.setNumeroAnimales(proximos);
        }

    }

    public Celda getCelda(int fila, int columna) {

        //si el valor de la fila o la columna es menor a 0 o mayor o igual al tamaño del tablero, devuelve nulo
        if (fila < 0 || fila >= tamano || columna < 0 || columna >= tamano) {
            return null;
        }
        //retorna el objeto celda de la lista
        return celdas.get(fila + (columna*tamano));
    }


    public Celda getCelda(int posicion) {
        return celdas.get(posicion);
    }

    public List<Celda> getCeldasProximas(int fila, int columna) {

        //inicializa listas
        List<Celda> celdas = new ArrayList<>();
        List<Celda> celdasProximas = new ArrayList<>();

        //añade las celdas proximas
        celdasProximas.add(getCelda(fila-1, columna));
        celdasProximas.add(getCelda(fila+1, columna));
        celdasProximas.add(getCelda(fila-1, columna-1));
        celdasProximas.add(getCelda(fila, columna-1));
        celdasProximas.add(getCelda(fila+1, columna-1));
        celdasProximas.add(getCelda(fila-1, columna+1));
        celdasProximas.add(getCelda(fila, columna+1));
        celdasProximas.add(getCelda(fila+1, columna+1));

        //recorre la lista y añade las celdas que sean validas a la otra lista
        for (Celda celdaProxima: celdasProximas) {
            if (celdaProxima != null) {
                celdas.add(celdaProxima);
            }
        }

        return celdas;
    }

    public int proximidadAnimales(int columna, int fila) {

        //inicializa lista con las celdas proximas a la celda correspondiente
        //llamando a la funcion anterior getCeldasProximas
        List<Celda> celdasProximas = getCeldasProximas(fila, columna);

        int animalesProximos = 0;

        //recorre la lista en busca de animales
        for (Celda c: celdasProximas){
            //si es animal
            if(c.getAnimal()){
                //aumenta el contador
                animalesProximos++;
            }
        }

        //devuelve el valor
        return animalesProximos;
    }


    public void reiniciarPartida(){
        this.partidaFinalizada = false;
        this.celdas.clear();
        generarGrid();
    }

    public void disminuirAnimales(){
        if(animales > 0){
            animales--;
        }
    }

    public void revelarAnimales(){
        for(Celda c : celdas){
            c.setRevelado(true);
        }
    }

    public boolean getPartidaFinalizada() {
        return partidaFinalizada;
    }

    public void setPartidaFinalizada(boolean partidaFinalizada) {
        this.partidaFinalizada = partidaFinalizada;
    }

    public List<Celda> getCeldas() {
        return celdas;
    }

    public Integer getTamano() {
        return tamano;
    }

    public Integer getAnimales() {
        return animales;
    }

    public void setModoJuego(Integer modoJuego) {
        this.modoJuego = modoJuego;
    }


}
