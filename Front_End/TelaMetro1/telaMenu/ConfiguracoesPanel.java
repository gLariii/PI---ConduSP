package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import Assets.Cores;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.border.AbstractBorder;

import TelaMetro1.Musica.Musica;
import DAO.UsuarioDAO;
import Model.Usuario; 

import javax.swing.SwingWorker;

public class ConfiguracoesPanel extends JPanel {
    // Declaração de componentes da interface e variáveis de controle
    private JSlider volumeSlider;
    private Runnable onVoltarAction;
    private Runnable onSobreAction;
    private Runnable onDesconectarAction;

    private Musica musicaPlayer;
    private UsuarioDAO usuarioDAO; // Instância de UsuarioDAO
    private int userId;

    private boolean isMuted = false;
    private int lastVolume = 100;

    private JPanel muteButtonPanel;
    private Color defaultMuteColor;
    private Color mutedColor;

    // Cria o painel de configurações
    public ConfiguracoesPanel(Runnable onVoltarAction, int userId) {
        this.onVoltarAction = onVoltarAction;
        this.onSobreAction = null;
        this.onDesconectarAction = null;
        this.userId = userId;
        this.usuarioDAO = new UsuarioDAO(); // Instancia UsuarioDAO aqui

        // Configura o layout e a cor de fundo do painel principal
        setLayout(new BorderLayout());
        setBackground(Cores.AZUL_METRO);

        // Cria o painel superior para o cabeçalho
        JPanel topHeaderPanel = new JPanel();
        topHeaderPanel.setLayout(new BoxLayout(topHeaderPanel, BoxLayout.Y_AXIS));
        topHeaderPanel.setBackground(Cores.AZUL_METRO);
        topHeaderPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 15, 25));

        // Cria o painel do botão "Voltar"
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new GridBagLayout());
        backButtonPanel.setOpaque(false);
        backButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        backButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        backButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Define a cor padrão para o botão "Voltar" e seu arredondamento
        Color defaultButtonColor = new Color(0, 40, 160);
        backButtonPanel.setBorder(new RoundBorder(40, defaultButtonColor));

        // Adiciona listeners para interações do mouse no botão "Voltar"
        backButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButtonPanel.setBorder(new RoundBorder(40, new Color(20, 60, 180)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButtonPanel.setBorder(new RoundBorder(40, defaultButtonColor));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                backButtonPanel.setBorder(new RoundBorder(40, new Color(0, 20, 100)));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (backButtonPanel.contains(evt.getPoint())) {
                    if (onVoltarAction != null) {
                        onVoltarAction.run();
                    }
                }
                if (backButtonPanel.contains(evt.getPoint())) {
                    backButtonPanel.setBorder(new RoundBorder(40, new Color(20, 60, 180)));
                } else {
                    backButtonPanel.setBorder(new RoundBorder(40, defaultButtonColor));
                }
            }
        });

        // Adiciona o ícone e o texto ao botão "Voltar"
        JLabel iconLabel = new JLabel();
        try (InputStream is = getClass().getResourceAsStream("/Assets/Imagens/seta.png")) {
            if (is != null) {
                ImageIcon backIcon = new ImageIcon(ImageIO.read(is).getScaledInstance(45, 45, Image.SCALE_SMOOTH));
                iconLabel.setIcon(backIcon);
            } else {
                System.err.println("Ícone de seta não encontrado em /Assets/Imagens/seta.png");
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar ícone de seta: " + e.getMessage());
        }

        JLabel textLabel = new JLabel("Voltar");
        textLabel.setFont(new Font("Arial", Font.BOLD, 32));
        textLabel.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 18);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        backButtonPanel.add(iconLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        backButtonPanel.add(textLabel, gbc);

        // Adiciona o botão "Voltar" e o título "Configurações" ao cabeçalho
        topHeaderPanel.add(backButtonPanel);
        topHeaderPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        JLabel titulo = new JLabel("Configurações", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        topHeaderPanel.add(titulo);

        add(topHeaderPanel, BorderLayout.NORTH);

        // Cria o painel central para o conteúdo principal
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BorderLayout());
        painelCentral.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        painelCentral.setBackground(Cores.AZUL_METRO);

        // Cria o painel de controle de volume
        JPanel volumeControlPanel = new JPanel(new GridBagLayout());
        volumeControlPanel.setOpaque(false);

        // Adiciona o label "Volume"
        JLabel volumeLabel = new JLabel("Volume");
        volumeLabel.setForeground(Color.WHITE);
        volumeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = 0;
        gbcLabel.anchor = GridBagConstraints.CENTER;
        gbcLabel.insets = new Insets(0, -20, 5, 0);
        volumeControlPanel.add(volumeLabel, gbcLabel);

        // Cria o slider de volume
        volumeSlider = new JSlider(JSlider.VERTICAL, 0, 100, 100);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setPaintTicks(false);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setBackground(Cores.AZUL_METRO);
        volumeSlider.setForeground(Color.WHITE);
        volumeSlider.setUI(new CustomSliderUI(volumeSlider));
        volumeSlider.setFocusable(false);

        // Carrega o volume salvo do banco de dados
        loadVolumeFromDatabase();

        // Define as cores para o botão de mudo
        defaultMuteColor = new Color(0, 40, 160);
        mutedColor = new Color(160, 0, 0);

        // Adiciona um listener para o slider de volume
        volumeSlider.addChangeListener(e -> {
            volumeSlider.repaint();

            int currentVolume = volumeSlider.getValue();

            if (currentVolume == 0) {
                if (!isMuted) {
                    isMuted = true;
                    updateMuteButtonBorder(muteButtonPanel, defaultMuteColor, mutedColor, isMuted);
                    volumeSlider.setEnabled(false);
                }
            } else {
                if (isMuted) {
                    isMuted = false;
                    updateMuteButtonBorder(muteButtonPanel, defaultMuteColor, mutedColor, isMuted);
                }
                lastVolume = currentVolume;
                volumeSlider.setEnabled(true);
            }

            if (musicaPlayer != null) {
                musicaPlayer.setVolume(currentVolume);
            }
            saveVolumeToDatabase(currentVolume);
        });

        // Adiciona o slider de volume ao painel de controle
        GridBagConstraints gbcSlider = new GridBagConstraints();
        gbcSlider.gridx = 0;
        gbcSlider.gridy = 1;
        gbcSlider.anchor = GridBagConstraints.CENTER;
        gbcSlider.fill = GridBagConstraints.VERTICAL;
        gbcSlider.weighty = 1.0;
        gbcSlider.insets = new Insets(0, 0, 0, 0);
        volumeControlPanel.add(volumeSlider, gbcSlider);

        // Cria o painel principal de conteúdo e adiciona o controle de volume
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setOpaque(false);

        mainContentPanel.add(volumeControlPanel);
        mainContentPanel.add(Box.createVerticalStrut(20));

        // Cria o painel do botão de "Silenciar"
        JPanel muteButtonContainerPanel = new JPanel();
        muteButtonContainerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        muteButtonContainerPanel.setOpaque(false);

        muteButtonPanel = new JPanel();
        muteButtonPanel.setLayout(new GridBagLayout());
        muteButtonPanel.setOpaque(false);
        muteButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Atualiza a borda do botão de mudo com base no estado atual
        updateMuteButtonBorder(muteButtonPanel, defaultMuteColor, mutedColor, isMuted);

        // Adiciona listeners para interações do mouse no botão de "Silenciar"
        muteButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (isMuted) {
                    muteButtonPanel.setBorder(new RoundBorder(20, new Color(180, 20, 20)));
                } else {
                    muteButtonPanel.setBorder(new RoundBorder(20, new Color(20, 60, 180)));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                updateMuteButtonBorder(muteButtonPanel, defaultMuteColor, mutedColor, isMuted);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (isMuted) {
                    muteButtonPanel.setBorder(new RoundBorder(20, new Color(100, 0, 0)));
                } else {
                    muteButtonPanel.setBorder(new RoundBorder(20, new Color(0, 20, 100)));
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (muteButtonPanel.contains(evt.getPoint())) {
                    if (isMuted) {
                        isMuted = false;
                        volumeSlider.setValue(lastVolume);
                    } else {
                        if (volumeSlider.getValue() > 0) {
                            lastVolume = volumeSlider.getValue();
                        }
                        volumeSlider.setValue(0);
                        isMuted = true;
                    }
                    volumeSlider.setEnabled(!isMuted);
                }
                updateMuteButtonBorder(muteButtonPanel, defaultMuteColor, mutedColor, isMuted);
            }
        });

        // Adiciona o ícone e o texto ao botão de "Silenciar"
        JLabel muteIconLabel = new JLabel();
        try (InputStream is = getClass().getResourceAsStream("/Assets/Imagens/mute.png")) {
            if (is != null) {
                ImageIcon muteIcon = new ImageIcon(ImageIO.read(is).getScaledInstance(25, 25, Image.SCALE_SMOOTH));
                muteIconLabel.setIcon(muteIcon);
            } else {
                System.err.println("Ícone de mute.png não encontrado em /Assets/Imagens/mute.png");
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar ícone mute.png: " + e.getMessage());
        }

        JLabel muteTextLabel = new JLabel("Silenciar");
        muteTextLabel.setFont(new Font("Arial", Font.BOLD, 18));
        muteTextLabel.setForeground(Color.WHITE);

        GridBagConstraints gbcMuteContent = new GridBagConstraints();
        gbcMuteContent.insets = new Insets(0, 0, 0, 10);
        gbcMuteContent.anchor = GridBagConstraints.CENTER;
        gbcMuteContent.gridx = 0;
        gbcMuteContent.gridy = 0;
        muteButtonPanel.add(muteIconLabel, gbcMuteContent);
        gbcMuteContent.gridx = 1;
        gbcMuteContent.gridy = 0;
        gbcMuteContent.insets = new Insets(0, 0, 0, 0);
        muteButtonPanel.add(muteTextLabel, gbcMuteContent);

        muteButtonContainerPanel.add(muteButtonPanel);
        mainContentPanel.add(muteButtonContainerPanel);

        painelCentral.add(mainContentPanel, BorderLayout.CENTER);

        // Cria o painel do rodapé
        JPanel painelRodape = new JPanel();
        painelRodape.setLayout(new BoxLayout(painelRodape, BoxLayout.Y_AXIS));
        painelRodape.setOpaque(false);
        painelRodape.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        painelRodape.add(Box.createRigidArea(new Dimension(0, 15)));

        // Cria o painel do botão "Desconectar"
        JPanel desconectarButtonPanel = new JPanel();
        desconectarButtonPanel.setLayout(new GridBagLayout());
        desconectarButtonPanel.setOpaque(false);
        desconectarButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));
        desconectarButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        desconectarButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Define a cor padrão para o botão "Desconectar" e seu arredondamento
        Color defaultDesconectarColor = new Color(160, 40, 0);
        desconectarButtonPanel.setBorder(new RoundBorder(30, defaultDesconectarColor));

        // Adiciona listeners para interações do mouse no botão "Desconectar"
        desconectarButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                desconectarButtonPanel.setBorder(new RoundBorder(30, new Color(180, 60, 20)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                desconectarButtonPanel.setBorder(new RoundBorder(30, defaultDesconectarColor));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                desconectarButtonPanel.setBorder(new RoundBorder(30, new Color(100, 20, 0)));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (desconectarButtonPanel.contains(evt.getPoint())) {
                    if (onDesconectarAction != null) {
                        onDesconectarAction.run();
                    }
                }
                if (desconectarButtonPanel.contains(evt.getPoint())) {
                    desconectarButtonPanel.setBorder(new RoundBorder(30, new Color(180, 60, 20)));
                } else {
                    desconectarButtonPanel.setBorder(new RoundBorder(30, defaultDesconectarColor));
                }
            }
        });

        // Adiciona o ícone e o texto ao botão "Desconectar"
        JLabel sairIconLabel = new JLabel();
        try (InputStream is = getClass().getResourceAsStream("/Assets/Imagens/sair.png")) {
            if (is != null) {
                ImageIcon sairIcon = new ImageIcon(ImageIO.read(is).getScaledInstance(35, 35, Image.SCALE_SMOOTH));
                sairIconLabel.setIcon(sairIcon);
            } else {
                System.err.println("Ícone de sair.png não encontrado em /Assets/Imagens/sair.png");
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar ícone sair.png: " + e.getMessage());
        }

        JLabel desconectarTextLabel = new JLabel("Desconectar");
        desconectarTextLabel.setFont(new Font("Arial", Font.BOLD, 28));
        desconectarTextLabel.setForeground(Color.WHITE);

        GridBagConstraints gbcDesconectar = new GridBagConstraints();
        gbcDesconectar.insets = new Insets(0, 0, 0, 15);
        gbcDesconectar.anchor = GridBagConstraints.CENTER;
        gbcDesconectar.gridx = 0;
        gbcDesconectar.gridy = 0;
        desconectarButtonPanel.add(sairIconLabel, gbcDesconectar);
        gbcDesconectar.gridx = 1;
        gbcDesconectar.gridy = 0;
        gbcDesconectar.insets = new Insets(0, 0, 0, 0);
        desconectarButtonPanel.add(desconectarTextLabel, gbcDesconectar);

        painelRodape.add(desconectarButtonPanel);
        painelRodape.add(Box.createRigidArea(new Dimension(0, 10)));

        // Cria o painel do botão "Sobre"
        JPanel sobreButtonPanel = new JPanel();
        sobreButtonPanel.setLayout(new GridBagLayout());
        sobreButtonPanel.setOpaque(false);
        sobreButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));
        sobreButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        sobreButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Define a cor padrão para o botão "Sobre" e seu arredondamento
        Color defaultSobreColor = new Color(0, 40, 160);
        sobreButtonPanel.setBorder(new RoundBorder(30, defaultSobreColor));

        // Adiciona listeners para interações do mouse no botão "Sobre"
        sobreButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sobreButtonPanel.setBorder(new RoundBorder(30, new Color(20, 60, 180)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sobreButtonPanel.setBorder(new RoundBorder(30, defaultSobreColor));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sobreButtonPanel.setBorder(new RoundBorder(30, new Color(0, 20, 100)));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (sobreButtonPanel.contains(evt.getPoint())) {
                    if (onSobreAction != null) {
                        onSobreAction.run();
                    }
                }
                if (sobreButtonPanel.contains(evt.getPoint())) {
                    sobreButtonPanel.setBorder(new RoundBorder(30, new Color(20, 60, 180)));
                } else {
                    sobreButtonPanel.setBorder(new RoundBorder(30, defaultSobreColor));
                }
            }
        });

        // Adiciona o ícone e o texto ao botão "Sobre"
        JLabel perfilIconLabel = new JLabel();
        try (InputStream is = getClass().getResourceAsStream("/Assets/Imagens/perfil.png")) {
            if (is != null) {
                ImageIcon perfilIcon = new ImageIcon(ImageIO.read(is).getScaledInstance(35, 35, Image.SCALE_SMOOTH));
                perfilIconLabel.setIcon(perfilIcon);
            } else {
                System.err.println("Ícone de perfil.png não encontrado em /Assets/Imagens/perfil.png");
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar ícone perfil.png: " + e.getMessage());
        }

        JLabel sobreTextLabel = new JLabel("Sobre");
        sobreTextLabel.setFont(new Font("Arial", Font.BOLD, 28));
        sobreTextLabel.setForeground(Color.WHITE);

        GridBagConstraints gbcSobre = new GridBagConstraints();
        gbcSobre.insets = new Insets(0, 0, 0, 15);
        gbcSobre.anchor = GridBagConstraints.CENTER;
        gbcSobre.gridx = 0;
        gbcSobre.gridy = 0;
        sobreButtonPanel.add(perfilIconLabel, gbcSobre);
        gbcSobre.gridx = 1;
        gbcSobre.gridy = 0;
        gbcSobre.insets = new Insets(0, 0, 0, 0);
        sobreButtonPanel.add(sobreTextLabel, gbcSobre);

        painelRodape.add(sobreButtonPanel);
        painelRodape.add(Box.createRigidArea(new Dimension(0, 10)));

        // Cria o painel de versão e adiciona o texto da versão
        JPanel versaoPanel = new JPanel(new BorderLayout());
        versaoPanel.setOpaque(false);

        JLabel versao = new JLabel("Versão: 1.2", SwingConstants.LEFT);
        versao.setForeground(Color.WHITE);
        versao.setFont(new Font("Arial", Font.PLAIN, 14));

        versaoPanel.add(versao, BorderLayout.WEST);
        painelRodape.add(versaoPanel);

        painelCentral.add(painelRodape, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);
    }

    // Define o player de música para o painel
    public void setMusicaPlayer(Musica musicaPlayer) {
        this.musicaPlayer = musicaPlayer;
        if (this.musicaPlayer != null) {
            this.musicaPlayer.setVolume(volumeSlider.getValue());
        }
    }

    // Define a ação para o botão "Sobre"
    public void setOnSobreAction(Runnable action) {
        this.onSobreAction = action;
    }

    // Define a ação para o botão "Desconectar"
    public void setOnDesconectarAction(Runnable action) {
        this.onDesconectarAction = action;
    }

    // Atualiza a borda do botão de mudo
    private void updateMuteButtonBorder(JPanel buttonPanel, Color defaultColor, Color mutedColor, boolean isMutedState) {
        if (buttonPanel != null) {
            if (isMutedState) {
                buttonPanel.setBorder(new RoundBorder(20, mutedColor));
            } else {
                buttonPanel.setBorder(new RoundBorder(20, defaultColor));
            }
        }
    }

    // Carrega o volume salvo do banco de dados
    private void loadVolumeFromDatabase() {
        if (userId == 0) {
            System.err.println("ID de usuário inválido para carregar volume.");
            return;
        }

        // Carrega o volume em segundo plano
        new SwingWorker<Usuario, Void>() {
            @Override
            protected Usuario doInBackground() throws Exception {
                // CORREÇÃO: Chama o método da instância de usuarioDAO
                return usuarioDAO.getUsuarioById(userId);
            }

            @Override
            protected void done() {
                try {
                    Usuario usuario = get();
                    if (usuario != null) {
                        int savedVolume = usuario.getVolume();
                        volumeSlider.setValue(savedVolume);
                        lastVolume = savedVolume;
                        isMuted = (savedVolume == 0);
                        volumeSlider.setEnabled(!isMuted);
                        updateMuteButtonBorder(muteButtonPanel, defaultMuteColor, mutedColor, isMuted);
                    } else {
                        System.err.println("Usuário com ID " + userId + " não encontrado no banco de dados.");
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar volume do banco de dados: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    // Salva o volume no banco de dados
    private void saveVolumeToDatabase(int volume) {
        if (userId == 0) {
            System.err.println("ID de usuário inválido para salvar volume.");
            return;
        }
        final int volumeToSave = volume;

        // Salva o volume em segundo plano
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                usuarioDAO.atualizarVolumeById(userId, volumeToSave);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                } catch (Exception e) {
                    System.err.println("Erro ao salvar volume no banco de dados: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    // Personaliza a aparência do slider de volume
    private static class CustomSliderUI extends BasicSliderUI {
        public CustomSliderUI(JSlider b) {
            super(b);
        }

        // Desenha a "trilha" do slider
        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            Rectangle trackBounds = trackRect;

            final int TRACK_WIDTH = 8;
            final int ARC_SIZE = 8;
            int trackX = trackBounds.x + (trackBounds.width / 2) - (TRACK_WIDTH / 2);

            g2d.setColor(new Color(255, 255, 255, 60));
            g2d.fillRoundRect(trackX, trackBounds.y, TRACK_WIDTH, trackBounds.height, ARC_SIZE, ARC_SIZE);

            int fillHeight = (int) (slider.getValue() / (double) slider.getMaximum() * trackBounds.height);

            if (slider.isEnabled()) {
                g2d.setColor(Color.WHITE);
            } else {
                g2d.setColor(new Color(150, 150, 150));
            }

            if (slider.getValue() > 0) {
                if (fillHeight > ARC_SIZE) {
                    g2d.fillRect(trackX, trackBounds.y + trackBounds.height - fillHeight, TRACK_WIDTH, fillHeight - ARC_SIZE / 2);
                    g2d.fillRoundRect(trackX, trackBounds.y + trackBounds.height - ARC_SIZE, TRACK_WIDTH, ARC_SIZE, ARC_SIZE, ARC_SIZE);
                } else {
                    g2d.fillRoundRect(trackX, trackBounds.y + trackBounds.height - fillHeight, TRACK_WIDTH, fillHeight, ARC_SIZE, ARC_SIZE);
                }
            }
        }

        // Desenha o "polegar" do slider
        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int diameter = 10;
            int x = thumbRect.x + (thumbRect.width - diameter) / 2;
            int y = thumbRect.y + (thumbRect.height - diameter) / 2;

            if (slider.isEnabled()) {
                g2d.setColor(Cores.AZUL_BORDA_BARRA);
            } else {
                g2d.setColor(new Color(100, 100, 100));
            }
            g2d.fillOval(x, y, diameter, diameter);

            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x, y, diameter, diameter);
        }

        // Desenha os rótulos do slider
        @Override
        public void paintLabels(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            if (slider.isEnabled()) {
                g2d.setColor(Color.WHITE);
            } else {
                g2d.setColor(new Color(150, 150, 150));
            }
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));

            super.paintLabels(g);
        }
    }

    // Cria bordas arredondadas personalizadas
    private static class RoundBorder extends AbstractBorder {
        private int radius;
        private Color color;

        RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        // Desenha a borda arredondada
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        // Define as margens da borda
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }

        @Override
    public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = this.radius / 2;
            return insets;
        }
    }
}