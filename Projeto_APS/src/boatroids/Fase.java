package boatroids;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.net.URL;

//ActionListener pra receber eventos de ação.
public class Fase extends JPanel implements ActionListener {

	//Private
    private double framesPorSegundo = System.currentTimeMillis(), novoFPS = System.currentTimeMillis();
    private Asteroide[] objast;
    private boolean debug;
    private Image fundo, boom[];
    private Point pInicio;
    private boolean rodarJogo;
    private boolean tclCima = false, tclBaixo = false, tclEsq = false,
            tclDir = false, tclEsp = false, tclT = false, tclComeco = false, replay = false;
    private Jogador criar;
    private boolean FimJogo, explodindo;
    private boolean invin;
    private final int mAmt = 1, radianos = 3;
    private int larg, altu, potuacao, asteCont;
    private double parteExp;
    private int vidas;
    private Relogio tempo;
    
    //Public
    public ArrayList<Tiro> tiros = new ArrayList<Tiro>();
    public Point[] expLoc = new Point[1];
    public int[] expNum = new int[1];
    public Point expLocal;
    public Som nascer, tiro, jogaDestu, astDestru, teleportar,somFundo;
    public int maxViagem = 200;
    public int maxMunicao = 5;
    
    //Aqui é o método que cria novos objetos som, que serão chamados de acordo com seus respectivos nomes.
    public void adcSom() {
        nascer = new Som("nascer.wav");
        tiro = new Som("tiro.wav");
        astDestru = new Som("astexp.wav");
        jogaDestu = new Som("jogexp.wav");
        teleportar = new Som("teleportar.wav");
        somFundo = new Som("somfundo.wav");
    }

    /*
     * Método construtor da classe Fase. É o que engloba grande parte da magia que acontece no código.
     * Fundo é colocado como azul. Cria o jogador, carregando a imagem certinho conforme o nome no pacote
     * imagens. rodarJogo como true para ser executado, FimJogo falso porque ele não acabou. Altura e largura
     * usando o this pra que sejam pegas certinho, e logo abaixo finalmente as imagens que serão selecionadas
     * de acordo com os Switch da classe "Asteroides" são especificadas. O contador de asteroides começa com
     * 0 e será incrementado futuramente. Vidas como 3, porque é a quantidade inicial definida pelo grupo,
     * e pontuação como 0 porque o jogo acabou de começar. "Boom", que é o array de imagens da explosão
     * tem seu tamanho especificado, e o método para criar sons é executado. Timer executado e um novo
     * relógio é criado.
     */
    public Fase(int f, int e, String fundo) throws Exception {
        this.setBackground(Color.BLUE);
        this.setBackground(this.carregarIMG(fundo));
        super.setSize(f, e);
        pInicio = new Point(this.getWidth() / 2, this.getHeight() / 2);
        criar = new Jogador(pInicio.x, pInicio.y, this.carregarIMG("jogador.png"));
        rodarJogo = true;
        FimJogo = false;
        debug = false;
        larg = this.getWidth();
        altu = this.getHeight();

        Asteroide.grande = this.carregarIMG("astG.png");
        Asteroide.medio = this.carregarIMG("astM.png");
        Asteroide.pequeno = this.carregarIMG("astP.png");
        Asteroide.veldrift = 5;

        asteCont = 0;
        vidas = 3;
        potuacao = 0;
        boom = new Image[12];
        this.carregarEXP();
        adcSom();
        Timer relogio = new Timer(25, this);
        relogio.start();
        tempo = new Relogio();
    }

    //Sempre é usado "f" ou "e", por serem iniciais de "Fernando".
    public void actionPerformed(ActionEvent f) {
    	//Contagem de Frames por segundo.
        framesPorSegundo = 1000 / (System.currentTimeMillis() - novoFPS);
        /*
         *  Se o array classe Asteroides for nulo ou tiver 0 como valor, primeiro a música de nascer é tocada,
         *  e o delay de 1 segundo feito através do sleep serve para: 1- Criar um efeito de fliperama;
         *  2-Fazer com que o som de ínicio de onda e o som de fundo da fase não toquem ao mesmo tempo.
         *  Então objast começa sua interação com o contador de asteroides asteCont. A lógica é a seguinte:
         *  toda vez que o array de asteroides zerar, o contador adicionará ele mesmo + 3 como novos
         *  asteroides na tela. Aqui está a dificuldade que cresce a cada onda. Na primeira serão 3 asteroides
         *  (de tamanhos aleatórios), na segunda serão 6, na terceira 9, e assim por diante. A dificuldade
         *  aumenta progressivamente.
         */
        if (objast == null || objast.length == 0) {
            try {
                nascer.play();
                TimeUnit.SECONDS.sleep(1);
                somFundo.loop();
                asteCont += 3;
                objast = new Asteroide[asteCont];
                instAste();
            } catch (Exception execao) {
            }
        }
        repaint();
        novoFPS = System.currentTimeMillis();

    }
    
    /*
     * Responsável por carregar as imagens de modo geral, e é usado muuuitas vezes no decorrer do programa.
     * Através degetResouce, é possível pegar a imagem com o nome que será inserido em determinado lugar.
     * Por exemplo para pegar as imagens dos asteroides, o nome será asteroides, e na do jogador esse nome
     * será jogador. No final, a imagem é retornada.
     */
    private Image carregarIMG(String nomeimagem) {
        Image imagem = null;
        URL lugar = getClass().getResource("imagens/" + nomeimagem);
        imagem = Toolkit.getDefaultToolkit().getImage(lugar);
        this.prepareImage(imagem, null);
        while (imagem.getWidth(null) == -1) {
        }
        return imagem;
    }

    /*
     * Esse aqui realiza a ação de muuuitos métodos que foram criados no decorrer no programa, 
     * e serve como uma "central" de criação de estágios gráficos,
     * onde todas as funções de cada um desses métodos é executada. Todas elas serão explicadas no decorrer
     * do programa, conforme forem sendo apresentadas.
     */
    @Override
    public void paintComponent(Graphics f) {
        if (rodarJogo) {
            super.paintComponent(f);
            fzrFundo(f);
            tclsInput();
            criar.drift();
            moverAste();
            moverTiro();
            checarLim();
            if (!FimJogo) {
                checarColisao();
                fzrTiro(f);
                fzrRel(f);
                if (explodindo) {
                    criar = new Jogador(pInicio.x, pInicio.y, this.carregarIMG("jogador.png"));
                    fzrExp(f);
                } else {
                    fzrCriar(f);
                } 
            	} else {
                if (explodindo) {
                    fzrExp(f);
                }
            }
            fzrAste(f);
            fzrIntel(f);
            fzrPont(f);
            fazFPS(f, this.getHeight());
            fzrMuni(f);
            if (FimJogo) {
                fzrFimJogo(f);
            }
        	} else {
        		if (!FimJogo) {
        			fzrPause(f);
            }
        }
    }

    /*
     * Como as teclas funcionam. Direita rotaciona em radianos, esquerda em radianos negativo, cima se 
     * baseia na movimentação e assim vai.
     */
    public void tclsInput() {
        if (tclDir) {
            criar.rotacionar(radianos);
        }
        if (tclEsq) {
            criar.rotacionar(-radianos);
        }
        if (tclCima) {
            criar.mover(new Point(mAmt, -mAmt));
            tclCima = false;
        }
        //Essa programação é o que faz a tecla de parar realmente parar a movimentação do barco.
        if (tclBaixo) {
            int tepmX = criar.nascer.x;
            int tepmY = criar.nascer.y;

            tepmX = tepmX == 0 ? 0 : tepmX > 0 ? tepmX - 1 : tepmX + 1;
            tepmY = tepmY == 0 ? 0 : tepmY > 0 ? tepmY - 1 : tepmY + 1;
            criar.nascer = new Point(tepmX, tepmY);
        }
        //Criar tiro com espaço.
        if (tclEsp) {
            if (!FimJogo) {
                adcTiro(criar.atirar());
                tclEsp = false;
            }
        }
        //Posição gerada a partir da tecla T é aleatória baseando-se on width e height, fazendo o teletransporte.
        if (tclT) {
            criar.setPosX((int) (Math.random() * this.getWidth()));
            criar.setPosY((int) (Math.random() * this.getHeight()));
            teleportar.play();
            tclT = false;
        }
        /*
         * Isso que torna o restart possível uma vez que o jogador perdeu o jogo. 
         * Todas as especificações são refeitas, as trilhas a serem tocadas, o delay, a pontuação,
         * o jogador é criado novamente, array resetado... Essencialmente é tudo de novo, e alem disso
         *  o replay é declarado como true
         */
        if (tclComeco) {
            tclComeco = false;
            if (FimJogo || !rodarJogo) {
                nascer.play();
                try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException execao) {
					execao.printStackTrace();
				}
                
                somFundo.loop();
                replay = true;
                potuacao = 0;
                criar = new Jogador(pInicio.x, pInicio.y, this.carregarIMG("jogador.png"));
                asteCont = 3;
                objast = new Asteroide[asteCont];
                try {
                    instAste();
                } catch (Exception f) 
                {
                Logger.getLogger(getClass().getName())
                .log(Level.SEVERE, null, f);
                }
                vidas = 3;
                potuacao = 0;
                FimJogo = false;
                rodarJogo = true;
            }
        }
    }

    //Também é necessário para colocar a imagem no fundo
    public void setBackground(Image imagemFundo) {
        fundo = imagemFundo;
    }

    /*
     * O array de imagens responsável por carregar as imagens de explosão em sequência. Com um for, o 
     * tamanho do array em si e um aditivo de um, todas as imagens são pegas porque possuem o mesmo nome em
     * String (aqui é feita a conversão do númerono no for pra string) no pacote
     * asteroids.imagens, tudo que muda é o número no final. Com o x-1, todas as imagens são pegas com auxílio
     * de "carregarIMG".
     */
    public void carregarEXP() {
        for (int x = 1; x < boom.length + 1; x++) {
            String pegarmtsboom = String.format("Explosao%d.png", x);
            boom[x - 1] = this.carregarIMG(pegarmtsboom);
        }
    }
    
    /*
     * Esse programa tem várias "checar limites", que servem para criar o efeito de poder atravessar de um
     * lado da tela para o outro. O primeiro agora é o astChecarLim, que serve para checar o limite de espaço
     * que os asteroides podem viajar. Para todo componente do array, se as condições requiridas forem 
     * cumpridas a checagem é feita. Dentro do if maior, tem dois ifs menores. O primeiro dos menores serve
     * para reposicionar a posição do asteroide através de setPosX, e corresponde a vertical, cima e baixo.
     * Já o segundo if menor corresponde a horizontal, esquerde e direita. Através daqui que as imagens são 
     * reposicionadas, e não somem uma vez que atravessam os limites.
     * 
     */
    public void astChecarLim() {
        for (int x = 0; x < objast.length; x++) {
            if (!this.getBounds().intersects(objast[x].caixaDeColisao)) {
                if (objast[x].caixaDeColisao.x < 0) {
                    objast[x].setPosX(this.getWidth());
                } else if (objast[x].caixaDeColisao.x > this.getWidth()) {
                    objast[x].setPosX(0 - objast[x].caixaDeColisao.width);
                }
                if (objast[x].caixaDeColisao.y < 0) {
                    objast[x].setPosY(this.getHeight());
                } else if (objast[x].caixaDeColisao.y > this.getHeight()) {
                    objast[x].setPosY(0 - objast[x].caixaDeColisao.height);
                }
        }
        }
    }

    /*
     * Esse corresponde ao checar limite do jogador, e é um pouco difícil de por em palavras, fica melhor
     * visualmente. Por isso vou fazer um gráficozinho que vale como explicação pra todos os "checarLimite."
     * Tá, imagine um barco se movimentando na diagonal para cima em sentido esquerdo 
     * (apontando para onde a coordenada [1,2] está). uma vez que elechegar no limite, 
     * ele será colocado no ponto F para o ponto E, conforme a figura:
     *   1  2  3  4  5  6  7
     * 1    F
     * 2
     * 3
     * 4
     * 5    
     * 6
     * 7    E
     * Aqui o reposicionamento também é feito com setPosX e setPosY.
     * 
     * 
     * O gráfico é so pra ficar mais visualmente "entendível" como a movimentação funciona no geral,
     * mas executando o jogo fica bem mais fácil de visualizar.
     */
    public void checarLim() {
        if (!this.getBounds().intersects(criar.caixaDeColisao)) {
            if (criar.caixaDeColisao.x < 0) {
                criar.setPosX(this.getWidth());
            } else if (criar.caixaDeColisao.x > this.getWidth()) {
                criar.setPosX(0);
            }
            if (criar.caixaDeColisao.y < 0) {
                criar.setPosY(this.getHeight());
            } else if (criar.caixaDeColisao.y > this.getHeight()) {
                criar.setPosY(0);
            }
        }
        astChecarLim();
        tiroChecarLim();
    }

    /*
     * Checar limite dos tiros, e a funcionalidade igual a movimentação do jogador que foi descrita acima.
     */
    public void tiroChecarLim() {
        ArrayList<Tiro> epmLista = new ArrayList<Tiro>();
        for (int x = 0; x < tiros.size(); x++) {
            Tiro epm = tiros.get(x);
            if (!this.getBounds().intersects(epm.caixaDeColisao)) {
                if (epm.caixaDeColisao.x < 0) {
                    epm.setPosX(this.getWidth());
                } else if (epm.caixaDeColisao.x > this.getWidth()) {
                    epm.setPosX(0);
                }
                if (epm.caixaDeColisao.y < 0) {
                    epm.setPosY(this.getHeight());
                } else if (epm.caixaDeColisao.y > this.getHeight()) {
                    epm.setPosY(0);
                }
                tiros.remove(x);
                epmLista.add(epm);
            }
        }
        tiros.addAll(epmLista);
    }
	
    // Outro componente que realiza o funcionamento da movimentação dos asteroides, e é o chamado lá em cima.
    public void moverAste() {
        for (int x = 0; x < objast.length; x++) {
            objast[x].drift();
        }
    }

    
     // A instanciação dos asteroides, criação de objeto.
     
    public void instAste() throws Exception {
        for (int x = 0; x < objast.length; x++) {
            objast[x] = new Asteroide((int) (Math.random() * 3) + 1);
            objast[x].setPosX((int) (Math.random() * larg));
            objast[x].setPosY((int) (Math.random() * altu));
            while (objast[x].colideCom(criar.caixaDeColisao) || Math.abs(objast[x].xPos - criar.xPos) < maxViagem) {
                objast[x] = new Asteroide((int) (Math.random() * 3) + 1);
                objast[x].setPosX((int) (Math.random() * larg));
                objast[x].setPosY((int) (Math.random() * altu));

            }
        }
    }

    /*
     * A checagem de colisão específica do jogador. O If verifica se o jogador não está invencível e se
     * o jogo ainda está rodando. Se acabar coldindo com um asteroide, o som de destruir é tocado e o estado
     * explodindo passa a ser true. O local da explosão é relativo a posição do asteroide específico do array
     * que a colisão ocorreu. Logo depois a quantidade de vidas máxima é diminuída, e se as mesmas chegarem
     * a zero, o fim de jogo é passa a ser true.
     */
    public void checarColisao() {
        checarColisaoTiro();
        if (!invin && !FimJogo) {
            for (int x = 0; x < objast.length; x++) {
                if (criar.colideCom(objast[x].caixaDeColisao)) {
                    jogaDestu.play();
                    explodindo = true;
                    parteExp = 0;
                    expLocal = new Point(objast[x].xPos, objast[x].yPos);
                    criar.setPosX(pInicio.x);
                    criar.setPosY(pInicio.y);
                    criar.nascer = new Point(0, 0);
                    vidas--;
                    removerAste(x);
                    if (vidas == 0) {
                        FimJogo = true;
                    }
                }
            }
        }
    }
    

    /*
     * Responsável pela remoção dos asteroides pequenos. Aqui também é feito o cálculo de pontuação deles,
     * uma vez que forem atingidos e removidos em "checarColisaoTiro" (está mais abaixo). 
     */
    public void removerAste(int posicao) {
        Asteroide[] epm = new Asteroide[objast.length - 1];
        for (int x = 0, s = 0; x < objast.length; x++) {
            if (!(x == posicao)) {
                epm[s] = objast[x];
                s++;
            } else {
            	potuacao += 20 * objast[x].escalaT; 
            	
            }
        }
        objast = epm;
    }
    
    
    /*
     * A adição de um novo asteroide. Objast = epm, é o que faz com que ao se dividir, sejam dois asteroides
     * da categoria menor, e não apenas um.
     */
    public void adcAste(Asteroide f) {
        Asteroide epm[] = new Asteroide[objast.length + 1];
        epm[0] = f;
        System.arraycopy(objast, 0, epm, 1, objast.length);
        objast = epm;
    }

    /*
     * Esse é responsável por adicionar os tiros na tela ao mesmo tempo que toca seu respectivo som
     * se o tamando da array list tiro for menor que a munição máxima determinada. Nota-se que o início 
     * padrão da maxMunicao é 5, mas isso pode ser alterado.
     */
    public void adcTiro(Tiro atirar) {
        if (tiros.size() < maxMunicao) {
            tiro.play();
            tiros.add(atirar);
        }
    }

    /*
     * Responsável controlar as ações ao tiro colidir com um asteróide. Ações como tocar o som de destruição,
     * fazer calculos de pontuação aos asteroides serem destruidos, eliminar o asteroide da tela usando
     * removerAst, e também do ato dos tiros serem removidos ao atingirem algo. Aqui muitos métodos já 
     * descritos são executados para realizar a operação.
     */
    private void checarColisaoTiro() {
        if (!(tiros.size() == 0)) {
            for (int x = 0; x < objast.length && objast.length != 0; x++) {
                for (int s = 0; s < tiros.size(); s++) {
                    if (tiros.size() > 0) {
                        Tiro epm = tiros.get(s);
                        if (epm.colideCom(objast[x].caixaDeColisao)) {
                            astDestru.play();
                            potuacao += 40 * objast[x].escalaT;
                            adcExp(objast[x].xPos, objast[x].yPos);
                            if (objast[x].escalaT == 1) {
                                removerAste(x);
                                x = 0;
                            } else {
                                try {
                                    objast[x] = new Asteroide(objast[x].escalaT - 1, objast[x].xPos, objast[x].yPos);
                                    adcAste(new Asteroide(objast[x].escalaT, objast[x].xPos, objast[x].yPos));
                                } catch (Exception execao) {
                                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, execao);
                                }
                            }
                            tiros.remove(s);
                        }
                    }
                }
            }
        }
    }

    
    //A movimentação do tiro ocorre por aqui, assim como seu "falecimento".
    public void moverTiro() {
        for (int x = 0; x < tiros.size(); x++) {
            Tiro epm = tiros.get(x);
            epm.mover();
            tiros.remove(x);
            if (!epm.falecer) {
                tiros.add(epm);
            }
        }
    }

    //Auxília na adição das explosões através do novo array criado.
    public void adcExp(int f, int e) {
        if (expLoc != null) {
            int[] epmNum = expNum;
            Point[] epmLoc = expLoc;
            expLoc = new Point[epmLoc.length + 1];
            expNum = new int[epmNum.length + 1];
            expNum[epmNum.length] = 1;
            expLoc[epmLoc.length] = new Point(f, e);
            for (int x = 0; x < epmNum.length - 1; x++) {
                expLoc[x] = epmLoc[x];
                expNum[x] = epmNum[x];
            }
        } else {
            expNum = new int[1];
            expNum[0] = 1;
            expLoc = new Point[1];
            expLoc[0] = new Point(f, e);
        }
    }


    /*
     * Esse aqui realiza o desenho do fundo, faz com que a imagem ocupe o espaço necessário.
     * Ela não está alinhada com nada, mas ela é maior que o Jframe, então ocupa o espaço todo.
     * Nota-se que a partir de agora, Graphics será muito usado para realizar todas as tarefas
     * gráficas necessárias.
     */
    public void fzrFundo(Graphics f) {
        f.drawImage(fundo, 0, 0, null);
    }

    //Com a bela fonte Tahoma, esse escreve os FPS na tela e posiciona eles. FPS coisa séria por isso fonte séria.
    public void fazFPS(Graphics f, int e) {
    	f.setFont(new Font("Tahoma", Font.PLAIN, 20));
        f.setColor(Color.BLACK);
        f.drawString(String.format("\tFPS: %.2f", framesPorSegundo), 0, e - 20);
    }

    
    /*
     * Esse aqui cria o belo jogador em toda sua glória, utilizando AffineTransform para que
     * o efeito de paralelismo das linhas seja preservado, e o movimento da imagem se torne
     * completamente flúido. Também é determinado a cor da função debugger, que no caso é branco.
     */
    public void fzrCriar(Graphics f) {
        Graphics2D grafico = (Graphics2D) f;
        AffineTransform pInicio = grafico.getTransform();
        AffineTransform ta = (AffineTransform) pInicio.clone();
        ta.translate(criar.xPos, criar.yPos);
        ta.translate(criar.img.getWidth(null) / 2, criar.img.getHeight(null) / 2);
        ta.rotate(Math.toRadians(criar.graus));
        ta.translate(-criar.xPos, -criar.yPos);
        ta.translate(-criar.img.getWidth(null) / 2, -criar.img.getHeight(null) / 2);
        grafico.setTransform(ta);
        grafico.drawImage(criar.img, ta.getTranslateInstance(criar.xPos, criar.yPos), null);
        grafico.setTransform(pInicio);
        if (debug) {
            grafico.setColor(Color.WHITE);
            grafico.drawRect(criar.caixaDeColisao.x, criar.caixaDeColisao.y, criar.img.getWidth(null), criar.img.getHeight(null));
        }
    }
   
    
    
    /*
     * Faz a tela de pause com a fonte Comic Sans MS, pra ficar bem fácil de ler. Posiciona onde a palavra
     * "pausado" vai aparecer também. 
     */
    public void fzrPause(Graphics f) {
        f.setColor(Color.BLACK);
        f.setFont(new Font("Comic Sans MS", Font.PLAIN, 36));
        f.drawString("PAUSADO", ((int) (larg
                - f.getFontMetrics().getStringBounds("PAUSADO", f).getWidth())
                / 2), altu / 2);
    }

    /*
     * Desenha os asteroides baseado no objast, e também desenha as linhas da função debugger em volta, mas
     * a cor dessas linhas nos asteroides são pretas.
     */
    public void fzrAste(Graphics f) {
        for (Asteroide e : objast) {
            f.drawImage(e.img, e.xPos, e.yPos, null);
            if (debug) {
            	f.setColor(Color.black);
                f.drawRect(e.caixaDeColisao.x, e.caixaDeColisao.y, e.img.getWidth(null), e.img.getHeight(null));
            }
        }
    }
    
    /*
     * Informações varias posicionadas pela tela, com fontes de tamanho facilmente legível ao mesmo tempo
     * que não são poluentes na tela. Todas tem suas coordenadas ditas no final.
     */
    public void fzrIntel(Graphics f) {
        f.setColor(Color.BLACK);
        f.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        f.drawString("Vidas restantes: " + vidas, 10, 40);
        f.drawString("Tiros:", 820, 650);
    }

    
    
    /*
     * Faz a tela de fim de jogo. As duas frases escritas tem características diferentes, por isso
     * ela é um pou o mais longuinha. "FIM DE JOGO" está escrito em preto e é um pouco maior que "Pressione
     * R para recomeçar", que por sua vez está em preto.
     */
    public void fzrFimJogo(Graphics f) {
    	f.setColor(Color.BLACK);
        f.setFont(new Font("Comic Sans MS", Font.PLAIN, 100));
        f.drawString("FIM DE JOGO", ((int) (larg
                - f.getFontMetrics().getStringBounds("FIM DE JOGO", f).getWidth())
                / 2), altu / 3);
        f.setColor(Color.RED);
        f.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        String texto = "Pressione R para reiniciar!";
        f.drawString(texto, ((int) (larg
                - f.getFontMetrics().getStringBounds(texto, f).getWidth())
                / 2), altu / 2);
    }
    
    //Faz a pontuação, que logo em seguida mostra os dados da variável pontuação (sem criatividade pra nome, eu sei).
    public void fzrPont(Graphics f) {
        f.setColor(Color.BLACK);
        f.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        f.drawString("Pontos: " + potuacao, larg - 170, 40);
    }

    /*
     * Faz os tiros, que adquirem seu formato mais circular através de fillOval, e eles tem sua coloração 
     * vermelha para ser bem vísivel, mais detacados do que o resto. Sua caixa da função degubber é da cor
     * branca, assim como o jogador.
     */
    public void fzrTiro(Graphics f) {
        for (int x = 0; x < tiros.size(); x++) {
            Tiro epm = tiros.get(x);
            f.setColor(Color.RED);
            f.fillOval(epm.xPos, epm.yPos, epm.larg, epm.altu);
            if (debug) {
                f.setColor(Color.WHITE);
                f.drawRect(epm.caixaDeColisao.x, epm.caixaDeColisao.y, epm.larg, epm.altu);
            }
        }
    }

    /*
     * Responsável por desenhar as figuras de explosão que são puxadas com outros métodos na tela através de
     * drawImage. Quando parteEXP e boom.lenght chegam no mesmo número, no caso 12, o estado explodindo
     * vira false.
     */
    public void fzrExp(Graphics f) {
        int e = expLocal.x;
        int s = expLocal.y;
        if (parteExp < boom.length - 1) {
            parteExp += .3;
            f.drawImage(boom[(int) parteExp], e, s, null);
        } else {
            explodindo = false;
        }
    }

    /*
     * Desenha a reta que representa a munição disponível, através de drawRect. Coloca a bora preta com 
     * preenchimento branco, e faz dela bem grossa pra ser visível.
     */
    public void fzrMuni(Graphics f) {
        int comecoX = larg - maxMunicao * 10;
        int comecoY = altu - 50;
        int fimX = (maxMunicao - tiros.size()) * 10;
        int fimY = altu - 20;
        f.setColor(Color.black);
        f.drawRect(comecoX, comecoY, fimX, fimY);
        f.setColor(Color.white);
        f.fillRect(comecoX + 1, comecoY + 1, fimX - 1, fimY - 1);
    }

 

    /* 
     * Aqui é feito o relógio, posicionado certinho na tela. Converte o tempo percorrido para String,
     * e escreve essa mesma string na tela.
     */
    public void fzrRel(Graphics f) {
    	f.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
        f.setColor(Color.BLACK);
        String texto = tempo.toString();
        f.drawString(texto, 450, 35);
    }
    
    /*
     * Pra finalizar essa jornada mágica, um conjunto extenso de getters e setters que são usados
     * no decorrer do programa como um todo, em várias classes. Acho que comentar um por um seria muito
     * redundante, visto que seus nomes são bem característicos, o que deixa suas funções bem claras.
     */
    
    public double getFraps() {
        return framesPorSegundo;
    }

    public int getVidas() {
        return vidas;
    }

    public Asteroide[] getAsroid() {
        return objast;
    }

    public boolean getDebuger() {
        return debug;
    }

    public Image[] getBoom() {
        return boom;
    }

    public Point getpInicio() {
        return pInicio;
    }

    public boolean rodarJogo() {
        return rodarJogo;
    }

    public boolean getIncencibilidade() {
        return invin;
    }
    
    public void setTclCima(boolean confirmacao) {
        tclCima = confirmacao;
    }

    public void setTclBaixo(boolean confirmacao) {
        tclBaixo = confirmacao;
    }

    public void setTclEsq(boolean confirmacao) {
        tclEsq = confirmacao;
    }

    public void setTclDir(boolean confirmacao) {
        tclDir = confirmacao;
    }

    public void setTclEsp(boolean confirmacao) {
        tclEsp = confirmacao;
    }

    public void setTclT(boolean confirmacao) {
        tclT = confirmacao;
    }

    public void setTclComeco(boolean confirmacao) {
        tclComeco = confirmacao;
    }

    public void setJogoOn() {
        rodarJogo = true;
    }

    public void setJogoOff() {
        rodarJogo = false;
    }

    public void setDebugerOn() {
        debug = true;
    }

    public void setDebugerOff() {
        debug = false;
    }

    public void setInvencibilidadeOn() {
        invin = true;
    }

    public void setInvencibilidadeOff() {
        invin = false;
    }
}	

/*
 * ______________________________________________________________________________
 *|  Obrigado por ler e pela atenção, desculpe se ficou confuso de alguma forma. |
 *|  As vezes é difícil explicar a lógica, mas dei meu melhor.                   |
 *|  Espero que o jogo seja divertido, apesar de ser amador deu                  | 
 *|  muito trabalho. :)                                                          |
 *|______________________________________________________________________________|                                          
*/
