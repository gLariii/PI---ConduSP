package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.io.File;

import Assets.Cores;
import CabineDeControleTela.CabineDeControleTela;
import Model.*;
import TelaMetro1.Musica.InicialMusica;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.application.Platform;

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

    private JFrame parentFrame;
    private String tipo_usuarioLogado;
    private int idUsuarioLogado;

    private final int SIDEBAR_WIDTH = 300;

    private JFXPanel videoJFXPanel;
    private MediaPlayer currentMediaPlayer;


    public Menu(JFrame frame, String imagemPath, String tipo_usuario, int idUsuarioLogado) {
        this.parentFrame = frame;
        this.tipo_usuarioLogado = tipo_usuario;
        this.idUsuarioLogado = idUsuarioLogado;

        InicialMusica.startBackgroundMusic("/TelaMetro1/Musica/musicamenu.wav", true);

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

        configuracoesPanel = new ConfiguracoesPanel(() -> {toggleConfigPanel(false);},idUsuarioLogado);
        configuracoesPanel.setOnSobreAction(() -> {
            showPanel("sobre");
        });

        configuracoesPanel.setOnDesconectarAction(() -> {
            SwingUtilities.invokeLater(() -> {
                ConfirmarSaida dialog = new ConfirmarSaida(parentFrame);
                dialog.setVisible(true);

                if (dialog.isConfirmed()) {
                    InicialMusica.stopMusic();
                    System.exit(0);
                }
            });
        });

        configuracoesPanel.addPropertyChangeListener("ancestor", evt -> {
            if (evt.getNewValue() != null && InicialMusica.getBackgroundMusicPlayer() != null) {
                configuracoesPanel.setMusicaPlayer(InicialMusica.getBackgroundMusicPlayer());
            }
        });

        sidebarContainerPanel.add(configuracoesPanel, BorderLayout.CENTER);

        add(sidebarContainerPanel, JLayeredPane.PALETTE_LAYER);

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

        videoJFXPanel = new JFXPanel();
        videoJFXPanel.setBackground(Color.BLACK);
        videoJFXPanel.setOpaque(true);
        add(videoJFXPanel, JLayeredPane.PALETTE_LAYER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                mainContentPanel.setBounds(0, 0, getWidth(), getHeight());

                if (sidebarContainerPanel.isVisible()) {
                    sidebarContainerPanel.setBounds(0, 0, SIDEBAR_WIDTH, getHeight());
                } else {
                    sidebarContainerPanel.setBounds(-SIDEBAR_WIDTH, 0, SIDEBAR_WIDTH, getHeight());
                }

                supervisorPanel.setBounds(0, 0, getWidth(), getHeight());
                feedbackPanel.setBounds(0, 0, getWidth(), getHeight());
                sobrePanel.setBounds(0, 0, getWidth(), getHeight());
                videoJFXPanel.setBounds(0, 0, getWidth(), getHeight());

                revalidate();
                repaint();
            }
        });

        showPanel("main");
        videoJFXPanel.setVisible(false);
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
            mainContentPanel.setVisible(false);
            sidebarContainerPanel.setVisible(false);
            supervisorPanel.setVisible(false);
            feedbackPanel.setVisible(false);
            sobrePanel.setVisible(false);
            videoJFXPanel.setVisible(true);

            playVideoIntro("/Assets/Imagens/videoJogo.mp4", () -> {
                SwingUtilities.invokeLater(() -> {
                    InicialMusica.stopMusic();
                    parentFrame.setContentPane(new CabineDeControleTela(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
                    parentFrame.revalidate();
                    parentFrame.repaint();
                    SalvarResposta.pontuacao = 0;
                });
            });
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

        if ("supervisor".equalsIgnoreCase(tipo_usuarioLogado)) {
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
        mainContentPanel.setVisible(false);
        sidebarContainerPanel.setVisible(false);
        supervisorPanel.setVisible(false);
        feedbackPanel.setVisible(false);
        sobrePanel.setVisible(false);
        videoJFXPanel.setVisible(false);

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

    private void playVideoIntro(String videoPath, Runnable onVideoEnd) {
        if (currentMediaPlayer != null) {
            currentMediaPlayer.stop();
            currentMediaPlayer.dispose();
        }

        JButton skipButton = new JButton("Pular Introdução");
        skipButton.setFont(new Font("Arial", Font.BOLD, 20));
        skipButton.setBackground(Cores.AZUL_METRO);
        skipButton.setForeground(Color.WHITE);
        skipButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        skipButton.setFocusPainted(false);
        skipButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        skipButton.setOpaque(true);

        add(skipButton, JLayeredPane.DRAG_LAYER);

        skipButton.setBounds(
            getWidth() - skipButton.getPreferredSize().width - 20,
            getHeight() - skipButton.getPreferredSize().height - 20,
            skipButton.getPreferredSize().width,
            skipButton.getPreferredSize().height
        );
        skipButton.setVisible(true);
        revalidate();
        repaint();


        Platform.runLater(() -> {
            try {
                java.net.URL resource = getClass().getResource(videoPath);
                if (resource == null) {
                    System.err.println("Recurso de vídeo não encontrado: " + videoPath);
                    JOptionPane.showMessageDialog(parentFrame,
                        "Erro: Arquivo de vídeo não encontrado! Caminho: " + videoPath,
                        "Erro de Carregamento", JOptionPane.ERROR_MESSAGE);
                    videoJFXPanel.setVisible(false);
                    skipButton.setVisible(false);
                    remove(skipButton);
                    if (onVideoEnd != null) {
                        onVideoEnd.run();
                    }
                    return;
                }
                InicialMusica.stopMusic();
                Media media = new Media(resource.toExternalForm());
                currentMediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(currentMediaPlayer);

                Group root = new Group();
                Scene scene = new Scene(root);
                videoJFXPanel.setScene(scene);

                root.getChildren().add(mediaView);

                mediaView.fitWidthProperty().bind(scene.widthProperty());
                mediaView.fitHeightProperty().bind(scene.heightProperty());
                mediaView.setPreserveRatio(false);


                Runnable finishAction = () -> {
                    if (currentMediaPlayer != null) {
                        currentMediaPlayer.stop();
                        currentMediaPlayer.dispose();
                        currentMediaPlayer = null;
                    }
                    videoJFXPanel.setScene(null);
                    videoJFXPanel.setVisible(false);
                    skipButton.setVisible(false);
                    remove(skipButton);
                    if (onVideoEnd != null) {
                        onVideoEnd.run();
                    }
                    revalidate();
                    repaint();
                };

                skipButton.addActionListener(e -> {
                    System.out.println("Vídeo pulado pelo usuário.");
                    finishAction.run();
                });

                currentMediaPlayer.setOnEndOfMedia(() -> {
                    System.out.println("Vídeo de introdução terminou.");
                    finishAction.run();
                });

                currentMediaPlayer.setOnError(() -> {
                    System.err.println("Erro durante a reprodução do vídeo: " + currentMediaPlayer.getError());
                    JOptionPane.showMessageDialog(parentFrame,
                        "Erro ao reproduzir o vídeo: " + currentMediaPlayer.getError().getMessage(),
                        "Erro de Vídeo", JOptionPane.ERROR_MESSAGE);
                    finishAction.run();
                });

                currentMediaPlayer.play();
            } catch (Exception ex) {
                System.err.println("Erro ao inicializar o player de vídeo: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentFrame,
                    "Erro inesperado ao carregar o vídeo: " + ex.getMessage(),
                    "Erro de Vídeo", JOptionPane.ERROR_MESSAGE);
                videoJFXPanel.setVisible(false);
                skipButton.setVisible(false);
                remove(skipButton);
                if (onVideoEnd != null) {
                    onVideoEnd.run();
                }
                revalidate();
                repaint();
            }
        });
    }
}