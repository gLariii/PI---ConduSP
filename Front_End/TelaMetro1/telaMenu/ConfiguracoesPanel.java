package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import Assets.Cores;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

public class ConfiguracoesPanel extends JPanel {
    private JSlider volumeSlider;
    private Runnable onVoltarAction;
    private Runnable onSobreAction;
    private Runnable onDesconectarAction;

    private static final int VERSAO_FONT_SIZE = 14;

    public ConfiguracoesPanel(Runnable onVoltarAction) {
        this.onVoltarAction = onVoltarAction;
        this.onSobreAction = null;
        this.onDesconectarAction = null;

        setLayout(new BorderLayout());
        setBackground(Cores.AZUL_METRO);

        JPanel topHeaderPanel = new JPanel();
        topHeaderPanel.setLayout(new BoxLayout(topHeaderPanel, BoxLayout.Y_AXIS));
        topHeaderPanel.setBackground(Cores.AZUL_METRO);
        topHeaderPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 15, 25));

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new GridBagLayout());
        backButtonPanel.setOpaque(false);

        backButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        backButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        backButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Color defaultButtonColor = new Color(0, 40, 160);

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
                    backButtonPanel.setBorder(new RoundBorder(40, new Color(20, 60, 180)));
                } else {
                    backButtonPanel.setBorder(new RoundBorder(40, defaultButtonColor));
                }
                if (onVoltarAction != null) {
                    onVoltarAction.run();
                }
            }
        });

        backButtonPanel.setBorder(new RoundBorder(40, defaultButtonColor));

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

        topHeaderPanel.add(backButtonPanel);
        topHeaderPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        JLabel titulo = new JLabel("Configurações", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        topHeaderPanel.add(titulo);

        add(topHeaderPanel, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BorderLayout());
        painelCentral.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        painelCentral.setBackground(Cores.AZUL_METRO);

        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setOpaque(false);

        volumeSlider = new JSlider(0, 100, 100);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setBackground(Cores.AZUL_METRO);
        volumeSlider.setForeground(Color.WHITE);
        volumeSlider.setUI(new CustomSliderUI(volumeSlider));
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.setBackground(Cores.AZUL_METRO);
        JLabel volumeLabel = new JLabel("Volume:");
        volumeLabel.setForeground(Color.WHITE);
        volumeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        sliderPanel.add(volumeLabel, BorderLayout.WEST);
        sliderPanel.add(volumeSlider, BorderLayout.CENTER);
        mainContentPanel.add(sliderPanel);
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel muteButtonPanel = new JPanel();
        muteButtonPanel.setLayout(new GridBagLayout());
        muteButtonPanel.setOpaque(false);
        muteButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        muteButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        muteButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Color defaultMuteColor = new Color(0, 40, 160);

        muteButtonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                muteButtonPanel.setBorder(new RoundBorder(20, new Color(20, 60, 180)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                muteButtonPanel.setBorder(new RoundBorder(20, defaultMuteColor));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                muteButtonPanel.setBorder(new RoundBorder(20, new Color(0, 20, 100)));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (muteButtonPanel.contains(evt.getPoint())) {
                    muteButtonPanel.setBorder(new RoundBorder(20, new Color(20, 60, 180)));
                } else {
                    muteButtonPanel.setBorder(new RoundBorder(20, defaultMuteColor));
                }
                System.out.println("Botão Silenciar clicado!");
            }
        });
        muteButtonPanel.setBorder(new RoundBorder(20, defaultMuteColor));

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

        GridBagConstraints gbcMute = new GridBagConstraints();
        gbcMute.insets = new Insets(0, 0, 0, 10);
        gbcMute.anchor = GridBagConstraints.CENTER;

        gbcMute.gridx = 0;
        gbcMute.gridy = 0;
        muteButtonPanel.add(muteIconLabel, gbcMute);

        gbcMute.gridx = 1;
        gbcMute.gridy = 0;
        gbcMute.insets = new Insets(0, 0, 0, 0);
        muteButtonPanel.add(muteTextLabel, gbcMute);

        mainContentPanel.add(muteButtonPanel);
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 15)));


        painelCentral.add(mainContentPanel, BorderLayout.CENTER);

        JPanel painelRodape = new JPanel();
        painelRodape.setLayout(new BoxLayout(painelRodape, BoxLayout.Y_AXIS));
        painelRodape.setOpaque(false);
        painelRodape.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JPanel desconectarButtonPanel = new JPanel();
        desconectarButtonPanel.setLayout(new GridBagLayout());
        desconectarButtonPanel.setOpaque(false);
        desconectarButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));
        desconectarButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        desconectarButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Color defaultDesconectarColor = new Color(160, 40, 0);

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
                    desconectarButtonPanel.setBorder(new RoundBorder(30, new Color(180, 60, 20)));
                } else {
                    desconectarButtonPanel.setBorder(new RoundBorder(30, defaultDesconectarColor));
                }
                if (onDesconectarAction != null) {
                    onDesconectarAction.run();
                }
            }
        });
        desconectarButtonPanel.setBorder(new RoundBorder(30, defaultDesconectarColor));

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

        JPanel sobreButtonPanel = new JPanel();
        sobreButtonPanel.setLayout(new GridBagLayout());
        sobreButtonPanel.setOpaque(false);

        sobreButtonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));
        sobreButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        sobreButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Color defaultSobreColor = new Color(0, 40, 160);

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
                    sobreButtonPanel.setBorder(new RoundBorder(30, new Color(20, 60, 180)));
                } else {
                    sobreButtonPanel.setBorder(new RoundBorder(30, defaultSobreColor));
                }
                if (onSobreAction != null) {
                    onSobreAction.run();
                }
            }
        });

        sobreButtonPanel.setBorder(new RoundBorder(30, defaultSobreColor));

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

        JPanel versaoPanel = new JPanel(new BorderLayout());
        versaoPanel.setOpaque(false);

        JLabel versao = new JLabel("Versão: 1.2", SwingConstants.LEFT);
        versao.setForeground(Color.WHITE);
        versao.setFont(new Font("Arial", Font.PLAIN, VERSAO_FONT_SIZE));

        versaoPanel.add(versao, BorderLayout.WEST);
        painelRodape.add(versaoPanel);

        painelCentral.add(painelRodape, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);
    }

    public void setOnSobreAction(Runnable action) {
        this.onSobreAction = action;
    }

    public void setOnDesconectarAction(Runnable action) {
        this.onDesconectarAction = action;
    }

    private static class CustomSliderUI extends BasicSliderUI {

        public CustomSliderUI(JSlider b) {
            super(b);
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Rectangle trackBounds = trackRect;

            g2d.setColor(new Color(255, 255, 255, 100));
            g2d.fillRect(trackBounds.x, trackBounds.y + (trackBounds.height / 2) - 2, trackBounds.width, 4);

            int thumbX = thumbRect.x + (thumbRect.width / 2);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(trackBounds.x, trackBounds.y + (trackBounds.height / 2) - 2, thumbX - trackBounds.x, 4);
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(Color.WHITE);
            g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);

            g2d.setColor(Cores.AZUL_METRO);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }

        @Override
        public void paintTicks(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);
            super.paintTicks(g);
        }

        @Override
        public void paintLabels(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            super.paintLabels(g);
        }
    }

    private static class RoundBorder extends AbstractBorder {
        private int radius;
        private Color color;

        RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

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