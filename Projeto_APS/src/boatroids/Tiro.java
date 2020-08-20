package boatroids;

import java.awt.*;

public class Tiro {

	//Variáveis de guerra (porque essa é a classe tiro!)... Tenho que parar com essas piadas.
    public Rectangle caixaDeColisao;
    public int xVel, yVel, xPos, yPos, xOrig, yOrig;
    public final int larg = 10, altu = 10, maxViagem = 750;
    public boolean falecer;
    private int DisXdeOrig, DisYdeOrig;

    /*
     * Método construtor da classe. xPos e yPos são os pontos atuais do jogador, x Vel e yVel correspondem a
     * velocidade do tiro. O falecer corresponde ao tiro deixar de existir,
     * e são dois casos que isso acontece: ele viajou a distância máxima ou colidiu com a caixa de colisão
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
     * O método responsável por fazer o tiro se mover e não ficar estático. A conta de distância é feita
     * através de contas, utilizando a função math.abs par que o valor retrnado seja sempre positivo.
     * Ai tem um for pra ver se a distância máxima já foi alcançada, e se sim, o tiro deixa de existir.
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

    //Não é a primeira vez que você vê esse método, serve pra checar se colidiu com outra coisa ai.
    public boolean colideCom(Rectangle outra_coisa_ai) {
        if (this.caixaDeColisao.intersects(outra_coisa_ai)) {
            return true;
        }
        return false;
    }

    //Funções que serão usadas na classe "Fase" pra auxiliar no funcionamento direitinho.
    public void setPosX(int larg) {
        this.xPos = larg;
        caixaDeColisao = new Rectangle(xPos, yPos, larg, altu);
    }

    public void setPosY(int larg) {
        this.yPos = larg;
        caixaDeColisao = new Rectangle(xPos, yPos, larg, altu);
    }
}