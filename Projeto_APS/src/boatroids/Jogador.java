package boatroids;

import java.awt.*;


public class Jogador {

	//As senhoritas variáveis, a alma de nossos amados programas.
    public Point posicao, nascer;
    public int mAmt = 10;
    public Rectangle caixaDeColisao;
    public int larg, altu, xPos, yPos;
    public int graus;
    public Image img;
    private static int velMove = 5;
    private int xMover, yMover;

    /*
     * O construtor "contrói" (piada muito boa) o jogador, a img pra armazenar a imagem e todas as coisas.
     * É usado três vezes na classe "Fase", co	m a variável correspondente "criar" lá.
     * O xMover =-1 é pro primeiro movimento não ser pra trás.
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
     * Boolean  pra ver se a caixa de colisão colidiu com alguma outra coisa ai, pra que na classe Fase
     * ele não colida com todos os asteroides coisas ao mesmo tempo.
     */
    public boolean colideCom(Rectangle outra_coisa_ai) {
        if (outra_coisa_ai.intersects(this.caixaDeColisao)) {
            return true;
        }
        return false;
    }
    
    /*
     * É a parte que vai fazer o jogador poder se mover pra frente. É usado em conjunto com
     * a tclCima da classe Fase, pra ter uma tecla correspondente. As nascer.x e nascer.y são responsáveis
     * por fazer a movimentação ser contínua, e não parar a cada clique.
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
     * Serve para criar o "Drift", isto é, não deixar o jogador estático em sua movimentação,
     * pra que precise apertar o botão toda vez. o caixaDeColisao faz com que a movimentação da caixa
     * seja contínua também, e não que ela mude de lugar somente a cada clique.
     */
    public void drift() {
        xPos += nascer.x;
        yPos += nascer.y;
        posicao = new Point(xPos, yPos);
        caixaDeColisao = new Rectangle(xPos, yPos, larg, altu);
    }
    
    /*
     * Método responsável por fazer o jogador e sua caixa de colisão rotacionarem em 360 graus.
     * O xMover e yMover servem pra que a movimentação do jogador seja pra todas as direções,
     * assim como o ponto de onde os tiros vão sair.
     */
    public void rotacionar(int gra) {
        graus = (gra + graus) % 360;
        graus = (graus < 0) ? 360 - -graus : graus;
        yMover = (((int) (3 * Math.cos(Math.toRadians(graus)))));
        xMover = (((int) (3 * Math.sin(Math.toRadians(graus)))));
    }
    
    //Aqui tem determinador de xPos e yPos, que são usados algumas vezes na classe "Fase".
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
     * Método que realiza o ato de atirar. xNovo e yNovo avaliam a "posição" de onde vai sair o tiro,
     * e xDir e yDir a direção dele.
     */
    public Tiro atirar() {
        int xNovo = (int) (xPos + ((this.caixaDeColisao.width) / 2) + 2 * Math.cos(graus));
        int yNovo = (int) (yPos + ((this.caixaDeColisao.height / 2) + 2 * Math.sin(graus)));
        int xDir = velMove * xMover;
        int yDir = velMove * -yMover;
        return new Tiro(xNovo, yNovo, xDir, yDir);

    }
    
    //Um getter de posição, mas não é realmente usado.
    public Point getPosicao() {
        return posicao;
    }
}