package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException; // Mantido, mesmo que não usado diretamente
import java.io.File; // Mantido, mesmo que não usado diretamente

import Assets.Cores; // Classe de cores personalizada
import CabineDeControleTela.CabineDeControleTela; // Exemplo de importação, ajuste se necessário
import Model.*; // Exemplo de importação, ajuste se necessário
import TelaMetro1.Musica.InicialMusica; // Classe para controle de música

// Importação da nova tela de vídeo (exemplo)
import TelaMetro1.telaMenu.Video;

public class Menu extends JLayeredPane {
    private Image ImagemDeFundo, logoOriginal, logoRedimensionada;
    private int logoWidth = 40;
    private int logoHeight = 40;

    private JButton btnMaquinario, btnFeedbacks, btnSupervisor;

    private JPanel mainContentPanel;
    private ConfiguracoesPanel configuracoesPanel;
    private JPanel sidebarContainerPanel;
    private SupervisorPanel supervisorPanel;
    private FeedbackPanel feedbackPanel;
    private SobrePanel sobrePanel;

    private JFrame parentFrame; // O JFrame principal que contém este menu
    private String tipo_usuarioLogado;
    private int idUsuarioLogado;

    private final int SIDEBAR_WIDTH = 300;

    // Construtor do painel de menu
    public Menu(JFrame frame, String imagemPath, String tipo_usuario, int idUsuarioLogado) {
        this.parentFrame = frame;
        this.tipo_usuarioLogado = tipo_usuario;
        this.idUsuarioLogado = idUsuarioLogado;

        // Inicia a música de fundo do menu
        InicialMusica.startBackgroundMusic("/TelaMetro1/Musica/musicamenu.wav", true);

        // Carrega as imagens de fundo e logo
        carregarImagens(imagemPath);

        // Define o layout como nulo para posicionamento manual dos componentes
        setLayout(null); // Permite setBounds() para posicionamento absoluto

        // Cria painel principal de conteúdo
        mainContentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Desenha imagem de fundo
                if (ImagemDeFundo != null) {
                    g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
                }
                // Desenha logo e texto no canto inferior
                if (logoRedimensionada != null) {
                    int x = getWidth() - Menu.this.logoWidth - 15;
                    int y = getHeight() - Menu.this.logoHeight - 15;
                    g.drawImage(logoRedimensionada, x, y, this);
                    // Desenha o texto "Condução SP"
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
        };
        mainContentPanel.setOpaque(false);
        mainContentPanel.add(criarNavBar(), BorderLayout.NORTH);
        mainContentPanel.add(criarPainelCentral(), BorderLayout.CENTER);

        // Adiciona painel de conteúdo principal
        add(mainContentPanel, JLayeredPane.DEFAULT_LAYER);

        // Redimensiona a logo
        redimensionarLogo(40, 40);

        // Cria painel contêiner para a barra lateral de configurações
        sidebarContainerPanel = new JPanel(new BorderLayout());
        sidebarContainerPanel.setOpaque(true);
        sidebarContainerPanel.setBackground(Cores.AZUL_METRO);

        // Inicializa painel de configurações
        configuracoesPanel = new ConfiguracoesPanel(() -> {toggleConfigPanel(false);},idUsuarioLogado);
        // Define ação para botão "Sobre"
        configuracoesPanel.setOnSobreAction(() -> {
            showPanel("sobre");
        });

        // Define ação para botão "Desconectar"
        configuracoesPanel.setOnDesconectarAction(() -> {
            showConfirmarSaidaDialog(); // Chama método de diálogo com overlay
        });

        // Adiciona listener para passar player de música ao painel de configurações
        configuracoesPanel.addPropertyChangeListener("ancestor", evt -> {
            if (evt.getNewValue() != null && InicialMusica.getBackgroundMusicPlayer() != null) {
                configuracoesPanel.setMusicaPlayer(InicialMusica.getBackgroundMusicPlayer());
            }
        });

        sidebarContainerPanel.add(configuracoesPanel, BorderLayout.CENTER);

        // Adiciona painel da barra lateral
        add(sidebarContainerPanel, JLayeredPane.PALETTE_LAYER);

        // Inicializa e adiciona os painéis (Supervisor, Feedback, Sobre)
        supervisorPanel = new SupervisorPanel("/Assets/Imagens/TelaInicial4Corrigida.png", () -> {
            showPanel("main");
        });
        add(supervisorPanel, JLayeredPane.MODAL_LAYER);

        feedbackPanel = new FeedbackPanel("/Assets/Imagens/TelaInicial4Corrigida.png", () -> {
            showPanel("main");
        }, idUsuarioLogado);
        add(feedbackPanel, JLayeredPane.MODAL_LAYER);

        sobrePanel = new SobrePanel("/Assets/Imagens/TelaInicial4Corrigida.png", () -> {
            showPanel("config");
        });
        add(sobrePanel, JLayeredPane.MODAL_LAYER);

        // Adiciona listener para redimensionamento do componente Menu
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                // Atualiza limites dos painéis ao redimensionar a janela
                mainContentPanel.setBounds(0, 0, getWidth(), getHeight());

                // Lógica de posicionamento da sidebar
                if (sidebarContainerPanel.isVisible()) {
                    sidebarContainerPanel.setBounds(0, 0, SIDEBAR_WIDTH, getHeight());
                } else {
                    sidebarContainerPanel.setBounds(-SIDEBAR_WIDTH, 0, SIDEBAR_WIDTH, getHeight());
                }

                // Garante que painéis modais cubram toda a área
                supervisorPanel.setBounds(0, 0, getWidth(), getHeight());
                feedbackPanel.setBounds(0, 0, getWidth(), getHeight());
                sobrePanel.setBounds(0, 0, getWidth(), getHeight());

                // Revalida e redesenha componentes
                revalidate();
                repaint();
            }
        });

        // Exibe o painel principal
        showPanel("main");
    }

    // Carrega imagens de fundo e logo
    private void carregarImagens(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null)
                ImagemDeFundo = ImageIO.read(isFundo);

            InputStream isLogo = getClass().getResourceAsStream("/Assets/Imagens/logoORG.png");
            if (isLogo != null) {
                logoOriginal = ImageIO.read(isLogo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Redimensiona a logo
    public void redimensionarLogo(int width, int height) {
        if (logoOriginal != null) {
            width = Math.max(20, Math.min(width, 100));
            height = Math.max(20, Math.min(height, 100));

            logoRedimensionada = logoOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.logoWidth = width;
            this.logoHeight = height;
            if (mainContentPanel != null) {
                mainContentPanel.repaint();
            }
        }
    }

    // Cria a barra de navegação
    private JPanel criarNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO);
        navBar.setPreferredSize(new Dimension(this.getWidth(), 60));

        JLabel titulo = new JLabel("Sistema de Gerenciamento");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 350, 0, 0));

        navBar.add(titulo, BorderLayout.CENTER);

        // Cria botão de configurações
        JButton btnConfiguracoes = botoes.criarBotaoConfiguracoes();
        btnConfiguracoes.addActionListener(e -> {
            toggleConfigPanel(true);
        });

        JPanel painelConfig = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        painelConfig.setOpaque(false);
        painelConfig.add(btnConfiguracoes);
        navBar.add(painelConfig, BorderLayout.WEST);

        return navBar;
    }

    // Cria o painel central com botões de ação
    private JPanel criarPainelCentral() {
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);

        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 0, 40));
        painelBotoes.setOpaque(false);
        painelBotoes.setPreferredSize(new Dimension(500, 570));

        // Cria os botões
        btnMaquinario = botoes.criarBotaoMaquinario();
        btnFeedbacks = botoes.criarBotaoFeedBackPessoal();
        btnSupervisor = botoes.criarBotaoSupervisor();

        // Adiciona listener para botão "Maquinário"
        btnMaquinario.addActionListener(e -> {
            InicialMusica.stopMusic();
            parentFrame.setContentPane(new Video(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        // Adiciona listener para botão "Supervisor"
        btnSupervisor.addActionListener(e -> {
            showPanel("supervisor");
        });

        // Adiciona listener para botão "Feedbacks"
        btnFeedbacks.addActionListener(e -> {
            if (feedbackPanel instanceof FeedbackPanel) {
                ((FeedbackPanel)feedbackPanel).carregarDadosFeedbackGeral(idUsuarioLogado);
            }
            showPanel("feedback");
        });

        // Adiciona botões ao painel de botões
        painelBotoes.add(btnMaquinario);
        painelBotoes.add(btnFeedbacks);

        // Adiciona botão "Supervisor" se o usuário for supervisor
        if ("supervisor".equalsIgnoreCase(tipo_usuarioLogado)) {
            painelBotoes.add(btnSupervisor);
        } else {
            btnSupervisor.setVisible(false);
        }

        // Configurações de layout para centralizar botões
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(60, 0, 0, 0);

        centro.add(painelBotoes, gbc);
        return centro;
    }

    // Substitui painel de conteúdo do JFrame pai
    private void substituirPainel(JPanel newPanel) {
        if (parentFrame != null) {
            parentFrame.setContentPane(newPanel);
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    // Gerencia visibilidade dos painéis do menu
    private void showPanel(String panelName) {
        mainContentPanel.setVisible(false);
        sidebarContainerPanel.setVisible(false);
        supervisorPanel.setVisible(false);
        feedbackPanel.setVisible(false);
        sobrePanel.setVisible(false);

        switch (panelName) {
            case "main":
                mainContentPanel.setVisible(true);
                break;
            case "config":
                mainContentPanel.setVisible(true);
                sidebarContainerPanel.setVisible(true);
                break;
            case "supervisor":
                supervisorPanel.setVisible(true);
                break;
            case "feedback":
                feedbackPanel.setVisible(true);
                break;
            case "sobre":
                sobrePanel.setVisible(true);
                break;
        }
        revalidate();
        repaint();
    }

    // Alterna visibilidade do painel de configurações com animação
    private void toggleConfigPanel(boolean show) {
        if (show) {
            showPanel("config");
            configuracoesPanel.revalidate();
            configuracoesPanel.repaint();
            animatePanel(sidebarContainerPanel, -SIDEBAR_WIDTH, 0, SIDEBAR_WIDTH, getHeight(), null);
        } else {
            animatePanel(sidebarContainerPanel, 0, -SIDEBAR_WIDTH, SIDEBAR_WIDTH, getHeight(), () -> {
                sidebarContainerPanel.setVisible(false);
                showPanel("main");
            });
        }
    }

    // Realiza animação de deslizamento horizontal
    private void animatePanel(JPanel panel, int startX, int endX, int width, int height, Runnable onComplete) {
        final int frames = 20;
        final int int_delay = 10;
        Timer timer = new Timer(int_delay, new AbstractAction() {
            int currentX = startX;
            int step = (endX - startX) / frames;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentX += step;
                if ((step > 0 && currentX >= endX) || (step < 0 && currentX <= endX)) {
                    currentX = endX;
                    ((Timer) e.getSource()).stop();
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
                panel.setBounds(currentX, 0, width, height);
            }
        });
        timer.start();
    }

    // Gerencia diálogo ConfirmarSaida com overlay de tela preta
    private void showConfirmarSaidaDialog() {
        // Prepara o GlassPane da janela principal para escurecer a tela
        JPanel glassPane = (JPanel) parentFrame.getGlassPane();
        glassPane.setLayout(new BorderLayout());
        glassPane.setBackground(new Color(0, 0, 0, 255)); // Fundo 100% preto
        glassPane.setOpaque(true);
        glassPane.setVisible(true);

        // Cria e exibe o diálogo de confirmação
        ConfirmarSaida dialog = new ConfirmarSaida(parentFrame);
        dialog.setVisible(true);

        // Esconde o GlassPane e restaura a tela após o diálogo fechar
        glassPane.setVisible(false);
        glassPane.setOpaque(false);
        glassPane.removeAll();
        glassPane.revalidate();
        glassPane.repaint();

        // Verifica a resposta do diálogo e age de acordo
        if (dialog.isConfirmed()) {
            System.out.println("Usuário confirmou a saída. Fechando a aplicação...");
            InicialMusica.stopMusic();
            System.exit(0);
        } else {
            System.out.println("Usuário cancelou a saída.");
        }
    }
}