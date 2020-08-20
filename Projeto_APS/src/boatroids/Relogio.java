
package boatroids;

public class Relogio {
	
	//Um pássaro? Um avião? Não! São as variáveis!
    private long milisec;
    private long segundos;

    //Método contrutor pra criar o relógio.
    public Relogio() {
        this.milisec = System.currentTimeMillis();
        this.segundos = 0;
    }
    
    //Serve pra que o reógio apareça escrito todo bonitinho em formato escrito.
    public String toString() {
        this.segundos = (System.currentTimeMillis() - this.milisec) / 1000;
        return (this.segundos / 60) + ":" + String.format("%02d", (this.segundos % 60));

    }
}