package boatroids;

import java.awt.*;

public class Tiro {

	//Vari�veis de guerra (porque essa � a classe tiro!)... Tenho que parar com essas piadas.
    public Rectangle caixaDeColisao;
    public int xVel, yVel, xPos, yPos, xOrig, yOrig;
    public final int larg = 10, altu = 10, maxViagem = 750;
    public boolean falecer;
    private int DisXdeOrig, DisYdeOrig;

    /*
     * M�todo construtor da classe. xPos e yPos s�o os pontos atuais do jogador, x Vel e yVel correspondem a
     * velocidade do tiro. O falecer corresponde ao tiro deixar de existir,
     * e s�o dois casos que isso acontece: ele viajou a dist�ncia m�xima ou colidiu com a caixa de colis�o
     * de um asteroide.
     */
    public Tiro(int x, int y, int xs, int ys) {
        this.xPos = x;
        this.yPos = y;
        this.xOrig = x;
        this.yOrig = y;
        this.xVel = xs;
        this.yVel = ys;
        this.DisXdeOrig = 0;
        this.DisYdeOrig = 0;
        this.falecer = false;
        caixaDeColisao = new Rectangle(x, y, larg, altu);
    }

    /*
     * O m�todo respons�vel por fazer o tiro se mover e n�o ficar est�tico. A conta de dist�ncia � feita
     * atrav�s de contas, utilizando a fun��o math.abs par que o valor retrnado seja sempre positivo.
     * Ai tem um for pra ver se a dist�ncia m�xima j� foi alcan�ada, e se sim, o tiro deixa de existir.
     */
    public void mover() {

        this.xPos += this.xVel;
        this.yPos += this.yVel;
        this.DisYdeOrig += Math.abs(this.yVel);
        this.DisXdeOrig += Math.abs(this.xVel);
        if (this.DisXdeOrig + this.DisYdeOrig > maxViagem) {
            this.falecer = true;
        }
        caixaDeColisao = new Rectangle(xPos, yPos, larg, altu);
    }

    //N�o � a primeira vez que voc� v� esse m�todo, serve pra checar se colidiu com outra coisa ai.
    public boolean colideCom(Rectangle outra_coisa_ai) {
        if (this.caixaDeColisao.intersects(outra_coisa_ai)) {
            return true;
        }
        return false;
    }

    //Fun��es que ser�o usadas na classe "Fase" pra auxiliar no funcionamento direitinho.
    public void setPosX(int larg) {
        this.xPos = larg;
        caixaDeColisao = new Rectangle(xPos, yPos, larg, altu);
    }

    public void setPosY(int larg) {
        this.yPos = larg;
        caixaDeColisao = new Rectangle(xPos, yPos, larg, altu);
    }
}