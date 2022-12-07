package celda;

public class Celda {

    private final int posicion;
    private final int fila;
    private final int columna;
    private int numHipotenochas;
    private boolean revelado;
    private boolean hipotenocha;

    private boolean marcado;

    public Celda(int posicion, int fila, int columna){
        this.posicion = posicion;
        this.fila = fila;
        this.columna = columna;
        this.revelado = false;
        this.hipotenocha = false;
        this.marcado = false;
    }

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

    public boolean getHipotenocha() {
        return hipotenocha;
    }

    public void setHipotenocha(boolean hipotenocha) {
        this.hipotenocha = hipotenocha;
    }

    public boolean getMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public int getNumHipotenochas() {
        return numHipotenochas;
    }

    public void setNumHipotenochas(int numHipotenochas) {
        this.numHipotenochas = numHipotenochas;
    }

}
