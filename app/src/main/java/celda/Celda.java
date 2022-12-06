package celda;

public class Celda {

    private final int id;
    private final int fila;
    private final int columna;
    private int numHipotenochas;
    private boolean revelado, hipotenocha;

    public Celda(int id, int fila, int columna){
        this.id = id;
        this.fila = fila;
        this.columna = columna;
        this.revelado = false;
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

    public int getId() {
        return id;
    }

    public boolean getHipotenocha() {
        return hipotenocha;
    }

    public void setHipotenocha(boolean hipotenocha) {
        this.hipotenocha = hipotenocha;
    }

    public int getNumHipotenochas() {
        return numHipotenochas;
    }

    public void setNumHipotenochas(int numHipotenochas) {
        this.numHipotenochas = numHipotenochas;
    }

}
