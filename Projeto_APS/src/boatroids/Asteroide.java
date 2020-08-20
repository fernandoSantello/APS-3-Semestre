package boatroids;

import java.awt.*;
import java.util.Random;

public class Asteroide {

	//Vari�veis lindas a serem usadas
    public int escalaT;
    public int xDrift, yDrift, xPos, yPos;
    public Image img;
    public Rectangle caixaDeColisao;
    public static int veldrift = 10;
    public int graus = 0;
    private Random rand;
    
    /*Vari�veis que v�o ser usadas no Switch, que correspondem ao tamanho de imagem que vai ser selecionado.
     *As imagens em si s�o puxadas na classe "Fase".
     */
    public static Image grande;
    public static Image medio;
    public static Image pequeno;
    public static final int GRANDE = 3;
    public static final int MEDIO = 2;
    public static final int PEQUENO = 1;

    /* Cria��o dos m�todos construtores. S�o dois, com duas assinaturas diferentes, ent�o isso
     * se trata de uma sobrecarga de m�todo. S�o pra situa��es diferentes. 
     */
   
    //Esse daqui s� usa a escala, � pro come�o de cada onda de asteroides.
    public Asteroide(int escalaT) throws Exception {
        this.escalaT = escalaT;
        rand = new Random();
        switch (escalaT) {
            case GRANDE:
                this.img = grande;
                break;
            case MEDIO:
                this.img = medio;
                break;
            case PEQUENO:
                this.img = pequeno;
                break;
            //Default ta aqui s� porque pode ter no Switch, n�o � usado de verdade.  
            default:
                throw new Exception("Tamanho incorreto.");
        }
        /*
         * A parte pra movimenta��o aleat�ria. O while � pra caso xDrift ou yDrift de 0 no aleat�rio, 
         * ele sorteie de novo. E ele precisa estar depois porque n�o da pra saber o valor de
         * xDrift ou yDrift antes do sorteio.
         */
        xDrift = rand.nextInt(veldrift) - veldrift / 2;
        yDrift = rand.nextInt(veldrift) - veldrift / 2;
        while (xDrift == 0 || yDrift == 0) {
            xDrift = rand.nextInt(veldrift) - veldrift / 2;
            yDrift = rand.nextInt(veldrift) - veldrift / 2;
        }
        
        //Cria a caixa de colis�o.
        this.caixaDeColisao = new Rectangle(xPos, yPos, this.img.getWidth(null), this.img.getHeight(null));
    }

    /*
     * Esse outro � para os que s�o gerados a partir de um destru�do. Ai precisa da xPos e yPos 
     * pra ele "nascer" no mesmo lugar do anterior.
     */
    public Asteroide(int escalaT, int x, int y) throws Exception {
        this.escalaT = escalaT;
        this.xPos = x;
        this.yPos = y;
        rand = new Random();
        switch (escalaT) {
            case GRANDE:
                this.img = grande;
                break;
            case MEDIO:
                this.img = medio;
                break;
            case PEQUENO:
                this.img = pequeno;
                break;
            //Default ta aqui s� porque pode ter no Switch, n�o � usado de verdade.
            default:
                throw new Exception("Tamanho incorreto");
        }
        /*
        * A parte pra movimenta��o aleat�ria. O while � pra caso xDrift ou yDrift de 0 no aleat�rio, 
        * ele sorteie de novo. E ele precisa estar depois porque n�o da pra saber o valor de
        * xDrift ou yDrift antes do sorteio.
        */
        xDrift = rand.nextInt(veldrift) - veldrift / 2;
        yDrift = rand.nextInt(veldrift) - veldrift / 2;
        while (xDrift == 0 || yDrift == 0) {
            xDrift = rand.nextInt(veldrift) - veldrift / 2;
            yDrift = rand.nextInt(veldrift) - veldrift / 2;
        }
        //Cria a caixa de colis�o que ser� usada nos colideCom.
        this.caixaDeColisao = new Rectangle(xPos, yPos, this.img.getWidth(null), this.img.getHeight(null));

    }

    /*
     * Boolean pra checar a caixa de colis�o, � usado no usado na instancia��o dos
     *asteroides na classe Fase.
     */
    public boolean colideCom(Rectangle outra_coisa_ai) {
        if (this.caixaDeColisao.intersects(outra_coisa_ai)) {
            return true;
        }
        return false;
    }

    // M�todo drift, usado na classe Fase. Serve pra fazer o drift de movimenta��o.
    public void drift() {
        xPos += xDrift;
        yPos += yDrift;
        this.caixaDeColisao = new Rectangle(xPos, yPos, this.img.getWidth(null),
                this.img.getHeight(null));
    }
    
    
    //Ai aqui tem os setters de posi��o, das duas.
    public void setPosX(int posicao) {
        this.xPos = posicao;
    }

    public void setPosY(int posicao) {
        this.yPos = posicao;
    }
}