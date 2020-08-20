package boatroids;

import java.awt.*;


public class Jogador {

	//As senhoritas vari�veis, a alma de nossos amados programas.
    public Point posicao, nascer;
    public int mAmt = 10;
    public Rectangle caixaDeColisao;
    public int larg, altu, xPos, yPos;
    public int graus;
    public Image img;
    private static int velMove = 5;
    private int xMover, yMover;

    /*
     * O construtor "contr�i" (piada muito boa) o jogador, a img pra armazenar a imagem e todas as coisas.
     * � usado tr�s vezes na classe "Fase", co	m a vari�vel correspondente "criar" l�.
     * O xMover =-1 � pro primeiro movimento n�o ser pra tr�s.
     */
    public Jogador(int x, int y, Image imagem) {
        xPos = x;
        yPos = y;
        img = imagem;
        nascer = new Point(0, 0);
        posicao = new Point(xPos, yPos);
        graus = 270;
        larg = img.getWidth(null);
        altu = img.getHeight(null);
        caixaDeColisao = new Rectangle(xPos, yPos, larg, altu);
        xMover = -1;
        yMover = 0;
    }
    
    /*
     * Boolean  pra ver se a caixa de colis�o colidiu com alguma outra coisa ai, pra que na classe Fase
     * ele n�o colida com todos os asteroides coisas ao mesmo tempo.
     */
    public boolean colideCom(Rectangle outra_coisa_ai) {
        if (outra_coisa_ai.intersects(this.caixaDeColisao)) {
            return true;
        }
        return false;
    }
    
    /*
     * � a parte que vai fazer o jogador poder se mover pra frente. � usado em conjunto com
     * a tclCima da classe Fase, pra ter uma tecla correspondente. As nascer.x e nascer.y s�o respons�veis
     * por fazer a movimenta��o ser cont�nua, e n�o parar a cada clique.
     */
    public void mover(Point onde) {
        xPos += onde.x * xMover;
        yPos += onde.y * yMover;
        posicao = new Point(xPos, yPos);
        caixaDeColisao = new Rectangle(xPos, yPos, larg, altu);
        nascer.x += xMover;
        nascer.y += -(yMover);
    }

    /*
     * Serve para criar o "Drift", isto �, n�o deixar o jogador est�tico em sua movimenta��o,
     * pra que precise apertar o bot�o toda vez. o caixaDeColisao faz com que a movimenta��o da caixa
     * seja cont�nua tamb�m, e n�o que ela mude de lugar somente a cada clique.
     */
    public void drift() {
        xPos += nascer.x;
        yPos += nascer.y;
        posicao = new Point(xPos, yPos);
        caixaDeColisao = new Rectangle(xPos, yPos, larg, altu);
    }
    
    /*
     * M�todo respons�vel por fazer o jogador e sua caixa de colis�o rotacionarem em 360 graus.
     * O xMover e yMover servem pra que a movimenta��o do jogador seja pra todas as dire��es,
     * assim como o ponto de onde os tiros v�o sair.
     */
    public void rotacionar(int gra) {
        graus = (gra + graus) % 360;
        graus = (graus < 0) ? 360 - -graus : graus;
        yMover = (((int) (3 * Math.cos(Math.toRadians(graus)))));
        xMover = (((int) (3 * Math.sin(Math.toRadians(graus)))));
    }
    
    //Aqui tem determinador de xPos e yPos, que s�o usados algumas vezes na classe "Fase".
    public void setPosX(int larg) {
        xPos = larg;
        posicao = new Point(xPos, yPos);
        caixaDeColisao = new Rectangle(xPos, yPos, this.larg, altu);
    }

    public void setPosY(int larg) {
        yPos = larg;
        posicao = new Point(xPos, yPos);
        caixaDeColisao = new Rectangle(xPos, yPos, this.larg, altu);
    }
    
    /*
     * M�todo que realiza o ato de atirar. xNovo e yNovo avaliam a "posi��o" de onde vai sair o tiro,
     * e xDir e yDir a dire��o dele.
     */
    public Tiro atirar() {
        int xNovo = (int) (xPos + ((this.caixaDeColisao.width) / 2) + 2 * Math.cos(graus));
        int yNovo = (int) (yPos + ((this.caixaDeColisao.height / 2) + 2 * Math.sin(graus)));
        int xDir = velMove * xMover;
        int yDir = velMove * -yMover;
        return new Tiro(xNovo, yNovo, xDir, yDir);

    }
    
    //Um getter de posi��o, mas n�o � realmente usado.
    public Point getPosicao() {
        return posicao;
    }
}