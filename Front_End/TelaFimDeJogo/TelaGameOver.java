package TelaFimDeJogo;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import ChaveReversoraTela.ChaveReversoraTela;
import TelaMetro1.telaMenu.Menu;

public class TelaGameOver extends JPanel {
    private Image imagemDeFundo;
    private Image logoMetro;
    private JFrame parentFrame;

    private String tipo_usuarioLogado;
    private int idUsuarioLogado;

    // Componentes Visuais
    private JButton botaoMenu;
    private JLabel tituloLabel;
    private JLabel subtituloLabel;

    private Timer fadeInTimer;
    private float alpha = 0f;

    // Construtor que inicializa a tela de fim de jogo (sucesso ou falha).
    public TelaGameOver(JFrame frame, String tipo_usuario, int idUsuarioLogado) {
        this.parentFrame = frame;
        this.tipo_usuarioLogado = tipo_usuario;
        this.idUsuarioLogado = idUsuarioLogado;
        
        setOpaque(false);
        setLayout(null);
        
        adicionarComponentes();

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reposicionarComponentes();
            }
        });
    }

    // Inicia um timer para criar um efeito de fade-in gradual na tela.
    public void iniciarFadeIn() {
        if (fadeInTimer != null && fadeInTimer.isRunning()) {
            return;
        }

        fadeInTimer = new Timer(40, e -> {
            alpha += 0.05f;
            if (alpha >= 1.0f) {
                alpha = 1.0f;
                fadeInTimer.stop();
            }
            repaint();
        });
        fadeInTimer.start();
    }

    // Sobrescreve o método 'paint' para aplicar o efeito de transparência (alpha) em todos os componentes.
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            super.paint(g2d);
        } finally {
            g2d.dispose();
        }
    }

    // Desenha os elementos de fundo do painel, como a cor e o logotipo.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(new Color(240, 240, 245));
        g.fillRect(0, 0, getWidth(), getHeight());

        if (logoMetro == null) {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Assets/Imagens/logo3.png"));
            logoMetro = logoIcon.getImage();
        }
        if (logoMetro != null) {
            int w = getWidth();
            int h = getHeight();
            int logoWidth = (int)(w * 0.12); 
            int logoHeight = (int)(h * 0.12);
            int logoX = (w - logoWidth) / 2;
            int logoY = (int)(h * 0.12);
            g.drawImage(logoMetro, logoX, logoY, logoWidth, logoHeight, this);
        }
    }

    // Adiciona os componentes visuais, como textos e botões, com base no resultado do jogo.
    private void adicionarComponentes() {
    // Variáveis para ambos os textos
    String tituloTexto;
    String subtituloTexto; // <-- 1. Declare a nova variável

    // Checa a condição UMA VEZ para definir ambos os textos
    if (!ChaveReversoraTela.falhaResolviada) {
        tituloTexto = "<html><center>VOCÊ COMETEU UM<br>ERRO GRAVE</center></html>";
        subtituloTexto = "Por favor, tente novamente."; // <-- 2. Defina o texto de falha
    } else {
        tituloTexto = "<html><center>PARABÉNS<br>FALHA RESOLVIDA</center></html>";
        subtituloTexto = "Por favor, volte ao menu."; // <-- 2. Defina o texto de sucesso
    }

    // Cria o JLabel do título
    tituloLabel = new JLabel(tituloTexto);
    tituloLabel.setFont(new Font("Arial", Font.BOLD, 46));
    tituloLabel.setForeground(new Color(25, 47, 89));
    tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(tituloLabel);

    // Cria o JLabel do subtítulo usando a variável
    subtituloLabel = new JLabel(subtituloTexto, SwingConstants.CENTER); // <-- 3. Use a variável aqui
    subtituloLabel.setFont(new Font("Arial", Font.PLAIN, 22));
    subtituloLabel.setForeground(Color.DARK_GRAY);
    add(subtituloLabel);

    // O resto do código continua igual...
    botaoMenu = criarBotaoEstilizado("VOLTAR", () -> {
        String imagemPath = "/Assets/Imagens/TelaInicial4Corrigida.png";
        Menu painelMenu = new Menu(parentFrame, imagemPath, tipo_usuarioLogado, idUsuarioLogado);
        substituirPainel(painelMenu);
    });
    add(botaoMenu);

    reposicionarComponentes();
}
    
    // Cria um botão customizado com um estilo visual específico (fonte, cor, etc.).
    private JButton criarBotaoEstilizado(String texto, Runnable action) {
        JButton botao = new JButton(texto);
        
        // Estilo da fonte
        botao.setFont(new Font("Arial", Font.BOLD, 24));
        botao.setForeground(Color.WHITE);
        
        // Cor de fundo
        botao.setBackground(new Color(0, 84, 168)); // Tom de azul similar ao do Metrô
        botao.setOpaque(true); // Essencial para que a cor de fundo apareça
        
        // Estilo do botão (plano/flat)
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);

        // Comportamento
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.addActionListener(e -> action.run());
        
        return botao;
    }

    // Reposiciona e redimensiona os componentes com base no tamanho do painel.
    private void reposicionarComponentes() {
        int w = getWidth();
        int h = getHeight();
        
        tituloLabel.setBounds(0, (int)(h * 0.30), w, 140);
        subtituloLabel.setBounds(0, (int)(h * 0.45), w, 40);

        if (botaoMenu != null) {
            int btnWidth = (int)(w * 0.25);
            int btnHeight = (int)(h * 0.08);
            botaoMenu.setBounds((w - btnWidth) / 2, (int)(h * 0.60), btnWidth, btnHeight);
        }
    }

    // Substitui o conteúdo da janela principal por um novo painel.
    private void substituirPainel(Container novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}