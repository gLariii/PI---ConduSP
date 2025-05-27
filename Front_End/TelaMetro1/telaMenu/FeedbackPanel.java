package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import Assets.Cores; // Importe a classe Cores

public class FeedbackPanel extends JPanel {

    private Image ImagemDeFundo;
    private Image logoOriginal, logoRedimensionada;
    private int logoWidth = 60; // Aumentei o tamanho da logo para 60x60 para melhor visibilidade
    private int logoHeight = 60;
    private Runnable voltarAcao;

    // Adicione esta referência ao painel de estatísticas
    private EstatisticasInternoPanel estatisticasPanel;

    public FeedbackPanel(String imagemPath, Runnable voltarAcao) {
        this.voltarAcao = voltarAcao;

        carregarImagens(imagemPath);

        setLayout(new BorderLayout()); // Usar BorderLayout para organização geral
        setOpaque(false); // Para que o background desenhado no paintComponent seja visível

        add(criarNavBar(), BorderLayout.NORTH);

        // Chame criarPainelCentral para adicionar o painel de estatísticas
        add(criarPainelCentral(), BorderLayout.CENTER); // <-- AGORA CHAMARÁ O MÉTODO CORRETO

        redimensionarLogo(logoWidth, logoHeight); // Garante que a logo seja redimensionada na inicialização
    }

    private void carregarImagens(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null) {
                ImagemDeFundo = ImageIO.read(isFundo);
            } else {
                System.err.println("Erro: Imagem de fundo não encontrada! Caminho: " + imagemPath);
            }

            // MUDANÇA AQUI: Use a LogoCorrigida4.png ou mantenha logoORG.png.
            // Certifique-se que o caminho da imagem está correto.
            InputStream isLogo = getClass().getResourceAsStream("/Assets/Imagens/LogoCorrigida4.png"); // Ou /Assets/Imagens/logoORG.png
            if (isLogo != null) {
                logoOriginal = ImageIO.read(isLogo);
            } else {
                System.err.println("Erro: Logo não encontrada! Verifique o caminho: /Assets/Imagens/LogoCorrigida4.png");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar imagens no FeedbackPanel: " + e.getMessage());
        }
    }

    private JPanel criarNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO); // Cor da barra de navegação
        navBar.setPreferredSize(new Dimension(getWidth(), 60)); // Altura da barra de navegação

        // Use a classe 'botoes' para criar o botão Voltar
        // Certifique-se que 'botoes.java' está compilando e acessível
        JButton btnVoltar = botoes.criarBotaoVoltar(); 
        btnVoltar.addActionListener(e -> {
            if (voltarAcao != null) {
                voltarAcao.run(); // Executa a ação de voltar definida no construtor
            }
        });

        JPanel painelEsquerdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        painelEsquerdo.setOpaque(false);
        painelEsquerdo.add(btnVoltar);

        navBar.add(painelEsquerdo, BorderLayout.WEST);

        JLabel titulo = new JLabel("Histórico de Partidas", SwingConstants.CENTER); // Título para a tela de estatísticas
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        navBar.add(titulo, BorderLayout.CENTER);

        return navBar;
    }

    private JPanel criarPainelCentral() {
        JPanel centro = new JPanel(new GridBagLayout()); // Usando GridBagLayout para centralizar o painel de estatísticas
        centro.setOpaque(false); // Para que o background desenhado no paintComponent seja visível

        // INSTANCIA E ADICIONA O PAINEL DE ESTATÍSTICAS AQUI
        estatisticasPanel = new EstatisticasInternoPanel(); // <-- INSTANCIANDO O PAINEL CORRETO!

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE; // Não preenche todo o espaço, respeita o preferredSize do painel interno
        gbc.insets = new Insets(20, 20, 20, 20); // Margens para o painel de estatísticas dentro do centro

        centro.add(estatisticasPanel, gbc); // <-- ADICIONANDO O PAINEL CORRETO!

        return centro;
    }

    public void redimensionarLogo(int width, int height) {
        if (logoOriginal != null) {
            width = Math.max(20, Math.min(width, 150));
            height = Math.max(20, Math.min(height, 150));

            logoRedimensionada = logoOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.logoWidth = width;
            this.logoHeight = height;
            repaint(); // Redesenha o painel para mostrar a logo redimensionada
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenha a imagem de fundo
        if (ImagemDeFundo != null) {
            g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }

        // Desenha a logo no canto inferior direito
        if (logoRedimensionada != null) {
            int x = getWidth() - logoWidth - 15; // 15 pixels de margem da direita
            int y = getHeight() - logoHeight - 15; // 15 pixels de margem de baixo
            g.drawImage(logoRedimensionada, x, y, this);

            // Adiciona o texto "Condução SP" no canto inferior esquerdo
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10)); // Fonte menor para o texto

            String texto = "Condução SP";
            int textoX = 15; // 15 pixels de margem da esquerda
            int textoY = getHeight() - 15; // 15 pixels de margem de baixo

            g2d.drawString(texto, textoX, textoY);
            g2d.dispose(); // Libera os recursos gráficos
        }
    }
}