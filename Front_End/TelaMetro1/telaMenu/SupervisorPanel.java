package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import Assets.Cores;
import TelaMetro1.telaMenu.FeedbackGeralPanel;

public class SupervisorPanel extends JPanel {
    private Image ImagemDeFundo;
    private Image logoOriginal, logoRedimensionada;
    private int logoWidth = 40;
    private int logoHeight = 40;
    private Runnable voltarAcao;

    private JPanel mainContentPanel;
    private BuscarSupervisorPanel buscarSupervisorPanel;
    private FeedbackGeralPanel feedbackGeralPanel;
    private CardLayout cardLayout; // Gerencia a troca de painéis
    private JPanel cardPanel; // Contêiner para os painéis gerenciados pelo CardLayout

    // Cria o painel do supervisor
    public SupervisorPanel(String imagemPath, Runnable voltarAcao) {
        this.voltarAcao = voltarAcao;
        carregarImagem(imagemPath); // Carrega a imagem de fundo
        carregarLogo("/Assets/Imagens/logoORG.png"); // Carrega a logo

        setLayout(new BorderLayout()); // Define o layout principal
        setOpaque(true);

        // Inicializa o CardLayout e o painel que o usará
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        // Cria o painel de conteúdo principal do supervisor (com botões de navegação)
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setOpaque(false);

        mainContentPanel.add(criarNavBarSupervisor(), BorderLayout.NORTH);

        // Cria o painel para os botões de ação
        JPanel botoesPanel = new JPanel(new GridBagLayout());
        botoesPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0); // Define margens entre os botões
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza os botões

        // Cria e configura o botão "Acessar relatórios operários"
        JButton btnRelatorios = botoes.criarBotaoPadrao(
                "Acessar relatórios operários",
                "/Assets/Imagens/relatorio.png",
                80, 80, SwingConstants.RIGHT, new Dimension(600, 150), 30);
        btnRelatorios.addActionListener(e -> {
            cardLayout.show(cardPanel, "feedbackGeral"); // Mostra o painel de feedback geral
        });
        botoesPanel.add(btnRelatorios, gbc);

        // Cria e configura o botão "Cadastrar novos supervisores"
        gbc.gridy = 1; // Próxima linha na grade
        JButton btnCadastrarSupervisor = botoes.criarBotaoPadrao(
                "Cadastrar novos supervisores",
                "/Assets/Imagens/cadastro.png",
                80, 80, SwingConstants.RIGHT, new Dimension(600, 150), 30);
        btnCadastrarSupervisor.addActionListener(e -> {
            cardLayout.show(cardPanel, "buscarSupervisor"); // Mostra o painel de buscar supervisor
        });
        botoesPanel.add(btnCadastrarSupervisor, gbc);

        mainContentPanel.add(botoesPanel, BorderLayout.CENTER);

        // Inicializa os painéis que serão trocados pelo CardLayout
        buscarSupervisorPanel = new BuscarSupervisorPanel(imagemPath, () -> cardLayout.show(cardPanel, "main"));
        feedbackGeralPanel = new FeedbackGeralPanel(imagemPath, () -> cardLayout.show(cardPanel, "main"));

        // Adiciona os painéis ao CardLayout com seus nomes
        cardPanel.add(mainContentPanel, "main"); // Painel principal
        cardPanel.add(buscarSupervisorPanel, "buscarSupervisor"); // Painel de busca/cadastro
        cardPanel.add(feedbackGeralPanel, "feedbackGeral"); // Painel de relatórios

        // Adiciona o painel do CardLayout ao painel do supervisor
        add(cardPanel, BorderLayout.CENTER);

        // Mostra o painel principal inicialmente
        cardLayout.show(cardPanel, "main");
    }

    // Carrega a imagem de fundo do painel
    private void carregarImagem(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null) {
                ImagemDeFundo = ImageIO.read(isFundo);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar imagem de fundo para SupervisorPanel: " + imagemPath);
        }
    }

    // Carrega a imagem da logo
    private void carregarLogo(String logoPath) {
        try (InputStream isLogo = getClass().getResourceAsStream(logoPath)) {
            if (isLogo != null) {
                logoOriginal = ImageIO.read(isLogo);
                if (logoOriginal != null) {
                    redimensionarLogo(logoWidth, logoHeight); // Redimensiona a logo após carregar
                }
            } else {
                System.err.println("Logo não encontrada: " + logoPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar logo para SupervisorPanel: " + logoPath);
        }
    }

    // Redimensiona a logo
    private void redimensionarLogo(int width, int height) {
        if (logoOriginal != null) {
            this.logoWidth = width;
            this.logoHeight = height;
            logoRedimensionada = logoOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    }

    // Cria a barra de navegação do supervisor
    private JPanel criarNavBarSupervisor() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO);
        navBar.setPreferredSize(new Dimension(getWidth(), 60));

        // Cria o botão "Voltar"
        JButton btnVoltar = botoes.criarBotaoVoltar();
        btnVoltar.addActionListener(e -> {
            // Lógica para voltar: se estiver no painel principal, executa a ação de voltar do supervisor.
            // Caso contrário, volta para o painel principal gerenciado pelo CardLayout.
            if (cardPanel.getComponent(0).isVisible()) { // Verifica se o painel principal está visível (assumindo que é o primeiro componente)
                if (voltarAcao != null) {
                    voltarAcao.run();
                }
            } else {
                cardLayout.show(cardPanel, "main");
            }
        });

        // Cria painel esquerdo para o botão "Voltar"
        JPanel painelEsquerdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        painelEsquerdo.setOpaque(false);
        painelEsquerdo.add(btnVoltar);
        navBar.add(painelEsquerdo, BorderLayout.WEST);

        // Cria o título da barra de navegação
        JLabel titulo = new JLabel("Área Supervisor", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        navBar.add(titulo, BorderLayout.CENTER);

        // Cria painel direito vazio para alinhamento
        JPanel painelDireito = new JPanel();
        painelDireito.setOpaque(false);
        painelDireito.setPreferredSize(new Dimension(btnVoltar.getPreferredSize().width + 30, 60));
        navBar.add(painelDireito, BorderLayout.EAST);

        return navBar;
    }

    // Desenha componentes personalizados no painel (imagem de fundo e logo)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ImagemDeFundo != null) {
            g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }

        if (logoRedimensionada != null) {
            int x = getWidth() - logoWidth - 15;
            int y = getHeight() - logoHeight - 15;
            g.drawImage(logoRedimensionada, x, y, this);

            // Desenha texto "Condução SP"
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));

            String texto = "Condução SP";
            int textoX = 15;
            int textoY = getHeight() - 15;

            g2d.drawString(texto, textoX, textoY);
            g2d.dispose();
        }
    }
}