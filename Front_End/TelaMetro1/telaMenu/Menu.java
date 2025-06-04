package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver; // Importado, mas as constantes não serão mais usadas para atribuição

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import Assets.Cores;
import CabineDeControleTela.CabineDeControleTela;

public class Menu extends JLayeredPane {
    private Image ImagemDeFundo, logoOriginal, logoRedimensionada;
    private int logoWidth = 40;  
    private int logoHeight = 40; 

    private JButton btnMaquinario, btnFeedbacks, btnSupervisor, btnConfiguracoes; 

    private JPanel mainContentPanel;
    private ConfiguracoesPanel configuracoesPanel;
    private JPanel sidebarContainerPanel;
    private SupervisorPanel supervisorPanel;
    private FeedbackPanel feedbackPanel;
    private SobrePanel sobrePanel;

    private JFrame parentFrame;
    private String tipoUsuarioLogado;
    private int idUsuarioLogado; 

    private final int SIDEBAR_WIDTH = 300;

    public Menu(JFrame frame, String imagemPath, String tipoUsuario, int idUsuario) {
        this.parentFrame = frame;
        this.tipoUsuarioLogado = tipoUsuario;
        this.idUsuarioLogado = idUsuario;

        carregarImagens(imagemPath);

        
        setLayout(null); 

        mainContentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (ImagemDeFundo != null) {
                    g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
                }
                if (logoRedimensionada != null) {
                    int x = getWidth() - Menu.this.logoWidth - 15; 
                    int y = getHeight() - Menu.this.logoHeight - 15; 
                    g.drawImage(logoRedimensionada, x, y, this);
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

        add(mainContentPanel, JLayeredPane.DEFAULT_LAYER);

        redimensionarLogo(40, 40); 


        sidebarContainerPanel = new JPanel(new BorderLayout());
        sidebarContainerPanel.setOpaque(true);
        sidebarContainerPanel.setBackground(Cores.AZUL_METRO); 

      
        configuracoesPanel = new ConfiguracoesPanel(() -> {
            toggleConfigPanel(false); 
        });
        configuracoesPanel.setOnSobreAction(() -> {
            showPanel("sobre"); 
        });
        

        configuracoesPanel.setOnDesconectarAction(() -> {
            // Garante que a caixa de diálogo seja exibida na Event Dispatch Thread (EDT)
            SwingUtilities.invokeLater(() -> {
                ConfirmarSaidaDialog dialog = new ConfirmarSaidaDialog(parentFrame);
                dialog.setVisible(true); // Exibe o diálogo (bloqueia até ser fechado)

                if (dialog.isConfirmed()) { // Verifica o resultado retornado pelo diálogo
                    System.out.println("DEBUG: Usuário confirmou a saída. Encerrando aplicação.");
                    System.exit(0); // Encerra a aplicação Java
                } else {
                    System.out.println("DEBUG: Usuário cancelou a saída.");
                }
            });
        });
        // >>>>> FIM DA MODIFICAÇÃO <<<<<

        sidebarContainerPanel.add(configuracoesPanel, BorderLayout.CENTER);

        add(sidebarContainerPanel, JLayeredPane.PALETTE_LAYER);

        supervisorPanel = new SupervisorPanel("/Assets/Imagens/TelaInicial4Corrigida.png", () -> {
            showPanel("main"); 
        });
        add(supervisorPanel, JLayeredPane.MODAL_LAYER); 

        System.out.println("DEBUG: ID do usuário autenticado passado para FeedbackPanel: " + idUsuarioLogado);
        feedbackPanel = new FeedbackPanel("/Assets/Imagens/TelaInicial4Corrigida.png", () -> {
            showPanel("main"); 
        }, idUsuarioLogado);
        add(feedbackPanel, JLayeredPane.MODAL_LAYER);

        sobrePanel = new SobrePanel("/Assets/Imagens/TelaInicial4Corrigida.png", () -> {
            showPanel("config"); 
        });
        add(sobrePanel, JLayeredPane.MODAL_LAYER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                System.out.println("DEBUG: Menu resized to: " + getWidth() + "x" + getHeight());
                mainContentPanel.setBounds(0, 0, getWidth(), getHeight());
                System.out.println("DEBUG: mainContentPanel setBounds to: " + mainContentPanel.getBounds());

                if (sidebarContainerPanel.isVisible()) {
                    sidebarContainerPanel.setBounds(0, 0, SIDEBAR_WIDTH, getHeight());
                } else {
                    sidebarContainerPanel.setBounds(-SIDEBAR_WIDTH, 0, SIDEBAR_WIDTH, getHeight());
                }
                
                supervisorPanel.setBounds(0, 0, getWidth(), getHeight());
                feedbackPanel.setBounds(0, 0, getWidth(), getHeight());
                sobrePanel.setBounds(0, 0, getWidth(), getHeight());

                revalidate();
                repaint();
            }
        });

        showPanel("main");       
        System.out.println("DEBUG: Chamada inicial showPanel(\"main\") executada.");
    }

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

    private JPanel criarNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO);
        navBar.setPreferredSize(new Dimension(this.getWidth(), 60)); 

        JLabel titulo = new JLabel("Sistema de Gerenciamento");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 350, 0, 0)); 

        navBar.add(titulo, BorderLayout.CENTER);

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

    private JPanel criarPainelCentral() {
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);

        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 0, 40));
        painelBotoes.setOpaque(false);
        painelBotoes.setPreferredSize(new Dimension(500, 570));

        btnMaquinario = botoes.criarBotaoMaquinario();
        btnFeedbacks = botoes.criarBotaoFeedBackPessoal();
        btnSupervisor = botoes.criarBotaoSupervisor();

        btnMaquinario.addActionListener(e -> {
            substituirPainel(new CabineDeControleTela(parentFrame, idUsuarioLogado)); 
        });

        btnSupervisor.addActionListener(e -> {
            showPanel("supervisor"); 
        });

        btnFeedbacks.addActionListener(e -> {
            if (feedbackPanel instanceof FeedbackPanel) {
                ((FeedbackPanel)feedbackPanel).carregarDadosFeedbackGeral(idUsuarioLogado); 
            }
            showPanel("feedback");
        });

        painelBotoes.add(btnMaquinario);
        painelBotoes.add(btnFeedbacks);

        if ("supervisor".equalsIgnoreCase(tipoUsuarioLogado)) {
            painelBotoes.add(btnSupervisor);
        } else {
            btnSupervisor.setVisible(false);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(60, 0, 0, 0);

        centro.add(painelBotoes, gbc);
        return centro;
    }

    private void substituirPainel(JPanel newPanel) {
        if (parentFrame != null) {
            parentFrame.setContentPane(newPanel);
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    private void showPanel(String panelName) {
        System.out.println("DEBUG: showPanel chamado: " + panelName);
        mainContentPanel.setVisible(false);
        sidebarContainerPanel.setVisible(false);
        supervisorPanel.setVisible(false);
        feedbackPanel.setVisible(false);
        sobrePanel.setVisible(false);

        switch (panelName) {
            case "main":
                mainContentPanel.setVisible(true);
                System.out.println("DEBUG: mainContentPanel agora visível: " + mainContentPanel.isVisible());
                break;
            case "config":
                mainContentPanel.setVisible(true); // Manter o fundo do metrô visível
                sidebarContainerPanel.setVisible(true);
                System.out.println("DEBUG: sidebarContainerPanel agora visível: " + sidebarContainerPanel.isVisible());
                break;
            case "supervisor":
                supervisorPanel.setVisible(true);
                System.out.println("DEBUG: supervisorPanel agora visível: " + supervisorPanel.isVisible());
                break;
            case "feedback":
                feedbackPanel.setVisible(true);
                System.out.println("DEBUG: feedbackPanel agora visível: " + feedbackPanel.isVisible());
                break;
            case "sobre":
                sobrePanel.setVisible(true);
                System.out.println("DEBUG: sobrePanel agora visível: " + sobrePanel.isVisible());
                break;
        }
        revalidate(); 
        repaint();    
        System.out.println("DEBUG: Revalidate e Repaint chamados para JLayeredPane.");
    }

    private void toggleConfigPanel(boolean show) {
        System.out.println("DEBUG: toggleConfigPanel chamado com show=" + show);
        if (show) {
            showPanel("config"); 
            
            System.out.println("DEBUG: ConfiguracoesPanel visível? " + configuracoesPanel.isVisible());
            System.out.println("DEBUG: ConfiguracoesPanel tamanho preferido: " + configuracoesPanel.getPreferredSize());
            System.out.println("DEBUG: ConfiguracoesPanel está com componentes? " + (configuracoesPanel.getComponentCount() > 0));

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
}