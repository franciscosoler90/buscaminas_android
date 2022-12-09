package celda;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridCeldas {

    private final List<Celda> celdas = new ArrayList<>();

    private Integer hipotenochas;
    private Integer tamano;
    private Integer modoJuego = 0;

    private boolean partidaFinalizada;

    public GridCeldas(){
        this.tamano = 8;
        this.hipotenochas = 10;
    }

    public void generarGrid(){

        ArrayList<Celda> nuevaLista = new ArrayList<>();

        switch (this.modoJuego){

            case 1:
                this.tamano = 12;
                this.hipotenochas = 30;
                break;
            case 2:
                this.tamano = 16;
                this.hipotenochas = 60;
                break;
            default:
                this.tamano = 8;
                this.hipotenochas = 10;
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

        int bombas = 0;

        while (bombas < hipotenochas) {

            int fila = new Random().nextInt(tamano);
            int columna = new Random().nextInt(tamano);

            Celda celda = getCelda(fila,columna);

            if (!celda.getHipotenocha()) {
                celda.setHipotenocha(true);
                bombas++;
            }
        }

        int proximos;

        for(Celda c : this.celdas){
            proximos = proximidadHipotenochas(c.getFila(), c.getColumna());
            System.out.println(proximos);
            c.setNumHipotenochas(proximos);
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

    public int proximidadHipotenochas(int columna, int fila) {

        List<Celda> celdasProximas = getCeldasProximas(fila, columna);

        int hipotenochasProximas = 0;

        for (Celda c: celdasProximas){
            if(c.getHipotenocha()){
                hipotenochasProximas++;
            }
        }

        return hipotenochasProximas;
    }


    public void reiniciarPartida(){
        this.partidaFinalizada = false;
        this.celdas.clear();
        generarGrid();
    }


    public int[] toXY(int index) {
        int y = index / tamano;
        int x = index - (y*tamano);
        return new int[]{x, y};
    }

    public void disminuirHipotenochas(){
        if(hipotenochas > 0){
            hipotenochas--;
        }
    }

    public void revelarHipotenochas(){
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

    public Integer getHipotenochas() {
        return hipotenochas;
    }

    public void setModoJuego(Integer modoJuego) {
        this.modoJuego = modoJuego;
    }


}
