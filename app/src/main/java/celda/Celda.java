package celda;

public class Celda {

    private final int posicion;
    private final int fila;
    private final int columna;
    private int numeroAnimales;
    private boolean revelado;
    private boolean esAnimal;
    private boolean marcado;

    //constructor
    public Celda(int posicion, int fila, int columna){
        this.posicion = posicion;
        this.fila = fila;
        this.columna = columna;
        this.revelado = false;
        this.esAnimal = false;
        this.marcado = false;
    }

    //getters and setters
    public int getColumna() {
        return columna;
    }

    public int getFila() {
        return fila;
    }

    public boolean getRevelado() {
        return revelado;
    }

    public void setRevelado(boolean revelado) {
        this.revelado = revelado;
    }

    public int getPosicion() {
        return posicion;
    }

    public boolean getAnimal() {
        return esAnimal;
    }

    public void setAnimal(boolean esAnimal) {
        this.esAnimal = esAnimal;
    }

    public boolean getMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public int getNumeroAnimales() {
        return numeroAnimales;
    }

    public void setNumeroAnimales(int numeroAnimales) {
        this.numeroAnimales = numeroAnimales;
    }

}
