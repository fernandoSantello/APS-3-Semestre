/*
*╔══════════════════════════════════════════════════════════╗
*║ *************       ◄ T E A M  L O V E L A C E ►         ║
*║ ▼   |\__/|  ▲        ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯          ║
*║ ▼  /     \  ▲       INTEGRANTES                  R. A.   ║
*║ ▼ /_.! !._\ ▲   |Fernando Santello ---------->  D92BAE0  ║   
*║ ▼    \@/    ▲   |Lucas Trombini    ---------->  D929BJ5  ║
*║ *************   |Wagner C. B. Pintão  ------->  N478365  ║
*║Project Name:─┐  |Luiz Henrique Perigo ------->  F035514  ║
*║ Jogo         │                                           ║
*║    Boatroids │       ► "The Best or Nothing!"            ║
*╚══════════════════════════════════════════════════════════╝
*/
package boatroids;

//Importação do que será utilizado.
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.Border;

//Main, onde definimos um pequeno menu para que o jogo não comece do nada.
public class Main extends JFrame implements ActionListener {

	//Criação dos botões e das labels que vão ser usados  na tela.
	JButton instrucoes = new JButton("Instruções");
	JButton jogar = new JButton("Jogar");
	JButton sair = new JButton("Sair");
	JLabel titulo = new JLabel("Boatroids");
	JLabel subtitulo = new JLabel("Controle o barco e limpe a água!");
	JButton easteregg = new JButton("Um oferecimento Equipe Lovelace");
	
	
    
	public Main() {
        /*
         * Colocando o método ActionListener nos botões e definindo para o 
         * Instruções e easteregg e o jogar que vai pegar as instruções do 
         * ActionPerformed definido mais abaixo.
         */
		instrucoes.addActionListener(this);
		easteregg.addActionListener(this);
		
		/*Para o botão jogar definimos as instruções dentro de seu addActionListener, 
		 * onde ao clicar é chamada a classe Runner que fará o jogo rodar.
		 * 
		 */
		jogar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Runner a_melhor_aps_de_todas = null;
				try {
					a_melhor_aps_de_todas = new Runner();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		        a_melhor_aps_de_todas.setVisible(true);
			}
		});
		sair.addActionListener(this);
		
		//Não há definição de nenhum layout pois fica mais fácil de manipular dentro da tela.
		setLayout(null);
		
		//Definindo o eixo x e y dos botões e labels, assim como seu WIDTH e HEIGHT.
		jogar.setBounds(215, 300, 200, 60);
		instrucoes.setBounds(507, 300, 200, 60);
		sair.setBounds(410, 550, 100, 60);
		titulo.setBounds(130, 2, 700, 140);
		subtitulo.setBounds(230, 190, 700, 100);
		easteregg.setBounds(220, 630, 500, 40);
		
		/*
		 * Adicionando os botões na tela, juntamente com suas respectivas característcas estéticas formosas.
		 * Não deu pra colocar tudo num método e chamar porque eles tem características diferentes, então
		 * foi melhor fazer assim.
		 */
	
		getContentPane().add(instrucoes);
		getContentPane().add(jogar);
		getContentPane().add(sair);
		getContentPane().add(easteregg);
		
		
		jogar.setBackground(new Color(59, 89, 182));
		jogar.setForeground(Color.WHITE);
		jogar.setFocusPainted(false);
		jogar.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		
		instrucoes.setBackground(new Color(59, 89, 182));
		instrucoes.setForeground(Color.WHITE);
		instrucoes.setFocusPainted(false);
		instrucoes.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		
		sair.setBackground(new Color(76, 7, 182));
		sair.setForeground(Color.WHITE);
		sair.setFocusPainted(false);
		sair.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		
		/*
		 * O botão secreto precisa ser secreto, e os botões geralmente tem uma bordinha pra que eles sejam
		 * identificados facilmente na tela. Então aqui a borda dele é removida, pra que ele fique realmente
		 * secreto. Pra ele ser secreto a cor de fundo dele tem que ser igual a cor do fundo também.
		 */
		easteregg.setBackground(new Color(12, 87, 153));
		Border emptyBorder = BorderFactory.createEmptyBorder();
		easteregg.setBorder(emptyBorder);
		easteregg.setForeground(Color.WHITE);
		easteregg.setFocusPainted(false);
		easteregg.setFont(new Font("Tahoma", Font.ITALIC, 20));	
		
		//As duas labels.
		titulo.setBackground(new Color(76, 7, 182));
		titulo.setForeground(Color.WHITE);
		titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 150));
		
		subtitulo.setBackground(new Color(76, 7, 182));
		subtitulo.setForeground(Color.white);
		subtitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		
		
		/*
		 * Definindo aqui as carateristicas básicas do JFrame, o titulo da tela, a resolução, 
		 * para sair quando fechar a tela, sua localizção relativa a nada
		 * para que possa ficar centralizada na tela do computador e para que fique visível.
		 */
		setTitle("Menu Boatroids");
		setSize(960, 720);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(titulo);
		add(subtitulo);
		getContentPane().setBackground(new Color(12, 87, 153));
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	//Para que o arquivo seja executado e a magia comece.
	public static void main(String[] args) throws Exception {
    	new Main();
    }
	
	//Definindo as ações para quando os botões forem clicados.
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * Isso daqui é pra mudar a cor da caixa de mensagem que aparece quando o botão secreto ou o botão
		 * instruções é pressionado.
		 */
		 UIManager.put("OptionPane.background", new Color(116, 186, 247));
		 UIManager.put("Panel.background", new Color(116, 186, 247));
		 
		if (e.getSource()==easteregg) {	
			JLabel texto = new JLabel("<html>Botão secreto, se você achou professor, esperamos que esteja tendo um bom dia!<br/> <br/> Ass: Time Lovelace</html>");
			texto.setFont(new Font("Arial", Font.PLAIN, 18));
			JOptionPane.showMessageDialog(null, texto);
		}
		if (e.getSource()==instrucoes) {
			JLabel texto = new JLabel("<html>Pressione ESPAÇO para atirar <br/> <br/> Pressione P para pausar <br/><br/> Use as setas para se mover <br/> <br/> Pressione T para se teleportar <br/><br/>  Munição: <br/> ' = ' para aumentar <br/>' - ' para diminuir</html>");
			texto.setFont(new Font("Arial", Font.BOLD, 18));
			JOptionPane.showMessageDialog(null, texto);
		}			
		if (e.getSource()==sair) {
			System.exit(0);
		}	
	}	

}




