	package boatroids;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;
import java.applet.*;

public class Som {

	//Vari�veis que um dia v�o para a broadway por produzirem sons muito bons.
    String nome;
    AudioClip audio;

    //M�todo construtor, busca no pacote de sons as trilhas que v�o ser usadas.
    public Som(String nomearq) {
        nome = nomearq;
        //Pega no pacote sons baseado no nome.
        try {
            URL in = this.getClass().getResource("sons/" + nome);
            audio = Applet.newAudioClip(in);
        } catch (Exception ex) {
            Logger.getLogger(Som.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*
     * Aqui tem o m�do play, que serva pra tocar a m�sica. Ta em ingl�s porque "tocar" ia ficar
     * meio estranho. Durante um tempo tinha tamb�m o m�todo "stop", mas como n�o era usado ele foi
     * retirado. Abaixo o m�todo loop, que � usado para fazer o som de fundo tocar indefinidamente.
     */
    public void play() {
        audio.play();

    }
    public void loop() {
    	audio.loop();
    }
}
