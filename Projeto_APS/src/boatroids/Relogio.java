
package boatroids;

public class Relogio {
	
	//Um p�ssaro? Um avi�o? N�o! S�o as vari�veis!
    private long milisec;
    private long segundos;

    //M�todo contrutor pra criar o rel�gio.
    public Relogio() {
        this.milisec = System.currentTimeMillis();
        this.segundos = 0;
    }
    
    //Serve pra que o re�gio apare�a escrito todo bonitinho em formato escrito.
    public String toString() {
        this.segundos = (System.currentTimeMillis() - this.milisec) / 1000;
        return (this.segundos / 60) + ":" + String.format("%02d", (this.segundos % 60));

    }
}