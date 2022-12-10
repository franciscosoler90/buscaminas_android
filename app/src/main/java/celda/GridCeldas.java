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

            case 1:
                this.tamano = 12;
                this.animales = 30;
                break;
            case 2:
                this.tamano = 16;
                this.animales = 60;
                break;
            default:
                this.tamano = 8;
                this.animales = 10;
                break;
        }

        int celdaId = 0;

        for(int fila = 0; fila < tamano; fila++){

            for(int columna = 0; columna < tamano; columna++){

                Celda celda = new Celda(celdaId,fila,columna);

                nuevaLista.add(celda);

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
            proximos = proximidadAnimales(c.getFila(), c.getColumna());
            c.setNumeroAnimales(proximos);
        }

    }

    public Celda getCelda(int fila, int columna) {
        if (fila < 0 || fila >= tamano || columna < 0 || columna >= tamano) {
            return null;
        }
        return celdas.get(fila + (columna*tamano));
    }


    public Celda getCelda(int posicion) {
        return celdas.get(posicion);
    }

    public List<Celda> getCeldasProximas(int fila, int columna) {
        List<Celda> celdas = new ArrayList<>();

        List<Celda> celdasProximas = new ArrayList<>();
        celdasProximas.add(getCelda(fila-1, columna));
        celdasProximas.add(getCelda(fila+1, columna));
        celdasProximas.add(getCelda(fila-1, columna-1));
        celdasProximas.add(getCelda(fila, columna-1));
        celdasProximas.add(getCelda(fila+1, columna-1));
        celdasProximas.add(getCelda(fila-1, columna+1));
        celdasProximas.add(getCelda(fila, columna+1));
        celdasProximas.add(getCelda(fila+1, columna+1));

        for (Celda celdaProxima: celdasProximas) {
            if (celdaProxima != null) {
                celdas.add(celdaProxima);
            }
        }

        return celdas;
    }

    public int proximidadAnimales(int columna, int fila) {

        List<Celda> celdasProximas = getCeldasProximas(fila, columna);

        int animalesProximos = 0;

        for (Celda c: celdasProximas){
            if(c.getAnimal()){
                animalesProximos++;
            }
        }

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
