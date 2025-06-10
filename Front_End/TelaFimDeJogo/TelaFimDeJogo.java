import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import TelaMetro1.telaMenu.Menu; // Importação da classe Menu que você forneceu

/**
 * Representa a tela de fim de jogo.
 * Esta tela é exibida quando o jogo termina e oferece a opção de voltar ao menu principal.
 */
public class TelaFimDeJogo extends JPanel {
    private Image imagemDeFundo;
    private JFrame parentFrame;
    private int idUsuarioLogado; // Variável para armazenar o ID do usuário

    // O botão foi renomeado para maior clareza.
    private JButton botaoMenu;

    /**
     * Construtor da tela de fim de jogo.
     * @param frame O JFrame principal da aplicação.
     * @param idUsuario O ID do usuário logado.
     */
    public TelaFimDeJogo(JFrame frame, int idUsuario) {
        this.parentFrame = frame;
        this.idUsuarioLogado = idUsuario; // Armazena o ID do usuário
        setLayout(null);
        adicionarBotoes();

        // Listener para reposicionar os componentes quando a janela for redimensionada.
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    /**
     * Desenha a imagem de fundo na tela.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo == null) {
            // Carrega a imagem de fundo. O caminho com "/" busca a partir da raiz do classpath.
            ImageIcon icon = new ImageIcon(getClass().getResource("/Imagens/TelaGameOver.png"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Cria e adiciona o botão que leva de volta ao menu principal.
     */
    private void adicionarBotoes() {
        // Ação do botão para criar uma nova instância da tela de Menu.
        botaoMenu = criarBotao(() -> {
            // Um tipo de usuário padrão é mantido, pois o construtor do Menu o exige.
            String tipoUsuarioDefault = "operador"; 

            // O idUsuarioLogado, recebido pelo construtor, é passado para a tela de Menu.
            Menu painelMenu = new Menu(parentFrame, "/Assets/Imagens/TelaInicial4Corrigida.png", tipoUsuarioDefault, this.idUsuarioLogado);
            substituirPainel(painelMenu);
        });
        add(botaoMenu);
        reposicionarBotoes(); // Garante que o botão seja posicionado corretamente na inicialização.
    }

    /**
     * Método auxiliar para criar um botão transparente e estilizado.
     * @param action A ação a ser executada quando o botão for clicado.
     * @return Um JButton configurado.
     */
    private JButton criarBotao(Runnable action) {
        JButton botao = new JButton("");
        botao.addActionListener(e -> action.run());
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Configurações para tornar o botão invisível, funcionando apenas como uma área clicável.
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setForeground(new Color(0, 0, 0, 0));
        return botao;
    }

    /**
     * Calcula e define a posição e o tamanho do botão do menu com base no tamanho atual da janela.
     */
    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        if (botaoMenu != null) {
            // Define a posição e o tamanho do botão em proporção ao tamanho da tela.
            botaoMenu.setBounds((int)(w * 0.29), (int)(h * 0.53), (int)(w * 0.08), (int)(h * 0.05));
        }
        repaint();
    }

    /**
     * Substitui o painel de conteúdo atual do frame principal por um novo painel.
     * @param novoPainel O Container que será exibido.
     */
    private void substituirPainel(Container novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
