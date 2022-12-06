package celda;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridCeldas {

    private final List<Celda> celdas = new ArrayList<>();

    private Integer hipotenochas, tamano, modoJuego = 0;

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

        for(int x = 0; x < tamano; x++){

            for(int y = 0; y < tamano; y++){

                Celda celda = new Celda(celdaId,x,y);

                nuevaLista.add(celda);

                celdaId++;

            }

        }

        //Limpiar el ArrayList
        this.celdas.clear();
        this.celdas.addAll(nuevaLista);

        int bombas = 0;

        while (bombas < hipotenochas) {

            int x = new Random().nextInt(tamano);
            int y = new Random().nextInt(tamano);

            Celda celda = getCelda(x,y);

            if (!celda.getHipotenocha()) {
                celda.setHipotenocha(true);
                bombas++;
            }
        }

    }

    public Celda getCelda(int x, int y) {
        if (x < 0 || x >= tamano || y < 0 || y >= tamano) {
            return null;
        }
        return celdas.get(x + (y*tamano));
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

    public int proximidadHipotenochas(int fila, int columna) {

        List<Celda> celdasProximas = getCeldasProximas(fila, columna);

        int hipotenochasProximas = 0;

        for (int i = 0; i < celdasProximas.size(); i++){
            if(celdasProximas.get(i).getHipotenocha()){
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

    public void setModoJuego(Integer modoJuego) {
        this.modoJuego = modoJuego;
    }


}
