package boatroids;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;


public class Runner extends JFrame {
	
	//S�o s� duas vari�veis, mas se tirar essas da erro na classe toda.
    public Fase fase;
    public boolean Debuger = true;
    
    /*
     * Toda a classe Runner est� ligada com KeyListener e KeyEvent, ela � respons�vel por fazer
     * a leitura das teclas n�. E em conjunto com outros m�todos do programa, cada vez que a tecla aqui
     * especificada � apertada, vai fazer a a��o correspondente que for dita aqui.
     */

    private class Tecla implements KeyListener {
    	
    	//Bot�o de pause com p min�sculo.
        public void keyPressed(KeyEvent f) {
            if (f.getKeyChar() == 'p') {
                if (fase.rodarJogo()) {
                    fase.setJogoOff();
                } else {
                    fase.setJogoOn();
                }
            }
            
            /*
             * Todos esses VK_ s�o pra teclas do teclado, � assim que � pra ser especificado de acordo
             * com KeyEvent. Ent�o abaixo tem consecutivamente seta pra cima, seta pra baixo, seta pra
             * direira e seta pra esquerda. Ai se pegar que apertou alguma dessas teclas,
             * ele coloca como true usando os setters da classe "Fase".
             */
            if (f.getKeyCode() == KeyEvent.VK_UP) {
                fase.setTclCima(true);
            }
            if (f.getKeyCode() == KeyEvent.VK_DOWN) {
                fase.setTclBaixo(true);
            }
            if (f.getKeyCode() == KeyEvent.VK_RIGHT) {
                fase.setTclDir(true);
            }
            if (f.getKeyCode() == KeyEvent.VK_LEFT) {
                fase.setTclEsq(true);
            }
            
            //Aqui � o correspondente a tecla de espa�o.
            if (f.getKeyChar() == ' ') {
                fase.setTclEsp(true);
            }
            
            /*
             * O da tecla T, que � usado pra se teletransportar.
             * Sim, nosso barco se teletransporta. Ele � bem legal.
             */
            if (f.getKeyCode() == KeyEvent.VK_T) {
                fase.setTclT(true);
            }
            
            //Tecla r pra quando voc� perder o jogo e quiser recome�ar.
            if (f.getKeyChar() == 'r') {
                fase.setTclComeco(true);
            }
            
            /*
             * Pra tecla esc, caso voc� queira sair do jogo e deixar 
             * o ambiente h�drico a merc� do mundo.
             */
            if (f.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);               
            }
            
            //As consideradas fun��es de debug, apesar que a quantidade de tiros � de dificuldade.
            if (Debuger) {
            	/*
            	 * "F" de Fernando pra fun��o debugger, que faz com que as caixas de colis�o fiquem vis�veis.
            	 * Pra funcionar faz o uso dos getters e setters correspondentes da classe "Fase".
            	 */
                if (f.getKeyChar() == 'f') {
                    if (fase.getDebuger()) {
                        fase.setDebugerOff();
                    } else {
                        fase.setDebugerOn();
                    }
                }
                
                /*
                 * O sinal de "=" pra quando for pra aumentar a quantidade m�xima de
                 *  tiros e aumentar a maxMunicao :).
                 */
                if (f.getKeyChar() == '=') {
                    fase.maxMunicao++;
                }
                
                /*
                 * O sinal de "-" pra quando for diminuir a quantidade m�xima de
                 * tiros e diminuir a maxMunicao :(.
                 */
                if (f.getKeyChar() == '-') {
                    fase.maxMunicao--;
                }
                
                /*
                 * A tecla "i" para ativar a invencibilidade, para que a colis�o seja ignorada
                 * e voc� seja o barco mais resistente do mundo. Assim como o debugger, faz o uso de getters 
                 * e setters da classe "Fase".
                 */
                if (f.getKeyChar() == 'i') {
                    if (fase.getIncencibilidade()) {
                        fase.setInvencibilidadeOff();
                    } else {
                        fase.setInvencibilidadeOn();
                    //Muitas chaves pra fechar tudo
                    }
                }
            }
        }

        /*
         * KeyReleased serve pra notar quando uma tecla foi "solta". Ai aqui funciona igual a parte de cima,
         *mas os retornos s�o false porque as teclas foram soltas.
         */
        public void keyReleased(KeyEvent f) {
            if (f.getKeyCode() == KeyEvent.VK_UP) {
                fase.setTclCima(false);
            }
            if (f.getKeyCode() == KeyEvent.VK_DOWN) {
                fase.setTclBaixo(false);
            }
            if (f.getKeyCode() == KeyEvent.VK_RIGHT) {
                fase.setTclDir(false);
            }
            if (f.getKeyCode() == KeyEvent.VK_LEFT) {
                fase.setTclEsq(false);
            }
            if (f.getKeyChar() == ' ') {
                fase.setTclEsp(false);
            }
            if (f.getKeyCode() == KeyEvent.VK_T) {
                fase.setTclT(false);
            }
        }

        public void keyTyped(KeyEvent f) {
        }
    }

    //A implementa��o do Focus Listener
    private class Foco implements FocusListener {

        public void focusGained(FocusEvent f) {
        }
        //Pra quando o jogo n�o for a coisa sendo focado pelo computador, ele pausar
        public void focusLost(FocusEvent f) {
            fase.setJogoOff();
        }
    }

    /*
     * A determina��o do nome que vai em cima do Jframe, do tamanho, da caracter�stica dele
     * n�o ser ajust�vel e essas coisas. Aqui determina a imagem que � usada de fundo, e a fase � criada
     * proveniente da classe "Fase". S�o adicionados o FocusListener e o KeyListener. A posi��o tamb�m
     *  � colocada como relative to null, para que apare�a sempre no meio da tela do computador.
     */
    public Runner() throws Exception {
        super("Boatroids!");
        this.setSize(960, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        fase = new Fase(this.getWidth(), this.getHeight(), "imgfundo.jpg");
        this.add(fase);
        this.addFocusListener(new Foco());
        this.addKeyListener(new Tecla());
	        this.setLocationRelativeTo(null);
    }
}