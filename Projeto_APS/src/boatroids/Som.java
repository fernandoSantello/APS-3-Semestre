	package boatroids;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;
import java.applet.*;

public class Som {

	//Variáveis que um dia vão para a broadway por produzirem sons muito bons.
    String nome;
    AudioClip audio;

    //Método construtor, busca no pacote de sons as trilhas que vão ser usadas.
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
     * Aqui tem o médo play, que serva pra tocar a música. Ta em inglês porque "tocar" ia ficar
     * meio estranho. Durante um tempo tinha também o método "stop", mas como não era usado ele foi
     * retirado. Abaixo o método loop, que é usado para fazer o som de fundo tocar indefinidamente.
     */
    public void play() {
        audio.play();

    }
    public void loop() {
    	audio.loop();
    }
}
