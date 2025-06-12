package TelaMetro1.telaMenu;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import Assets.Cores;

public class ConfirmarSaida extends JDialog {

    private boolean confirmed = false;

    // Construtor do diálogo de confirmação de saída
    public ConfirmarSaida(JFrame parent) {
        super(parent, "Confirmação de Saída", true);

        // Configura o diálogo para não ter barra de título e ser transparente
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        // Cria o painel de conteúdo principal com GridBagLayout para controle preciso
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Cores.AZUL_METRO);
        contentPanel.setBorder(new RoundBorder(20, Cores.AZUL_METRO));

        // ADIÇÃO IMPORTANTE: Define o tamanho preferido para o contentPanel
        contentPanel.setPreferredSize(new Dimension(400, 300)); // Ajuste esses valores conforme necessário
        // Você pode usar setMinimumSize() se quiser que ele possa crescer, mas não encolher abaixo disso.
        // contentPanel.setMinimumSize(new Dimension(400, 300));


        // Configurações do GridBagLayout para os componentes principais
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Todos os componentes na primeira coluna
        gbc.weightx = 1.0; // Faz com que a coluna se expanda
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza horizontalmente
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz o componente preencher horizontalmente

        // Cria Painel para o ícone e a mensagem
        JPanel iconAndTextPanel = new JPanel();
        iconAndTextPanel.setLayout(new BoxLayout(iconAndTextPanel, BoxLayout.Y_AXIS));
        iconAndTextPanel.setOpaque(false);

        // Carrega e configura o ícone de aviso
        ImageIcon avisoIcon = carregarIcone("/Assets/Imagens/aviso.png", 70, 70);

        JLabel avisoLabel = new JLabel();
        if (avisoIcon != null) {
            avisoLabel.setIcon(avisoIcon);
        }
        avisoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cria e configura o rótulo da mensagem de confirmação
        JLabel messageLabel = new JLabel("<html><center>Você tem certeza <br>que deseja sair?</center></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 32));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setBorder(new EmptyBorder(5, 0, 0, 0));

        // Adiciona o ícone e a mensagem ao painel, empilhando-os
        iconAndTextPanel.add(avisoLabel);
        iconAndTextPanel.add(Box.createVerticalStrut(10));
        iconAndTextPanel.add(messageLabel);

        // Adiciona o painel de ícone/mensagem ao painel de conteúdo principal
        gbc.gridy = 0; // Primeira linha
        gbc.insets = new Insets(20, 0, 10, 0); // Margens
        contentPanel.add(iconAndTextPanel, gbc);

        // Cria Painel para os botões "Sim" e "Não"
        JPanel buttonPanel = new JPanel(new GridBagLayout()); // Usar GridBagLayout para os botões
        buttonPanel.setOpaque(false);

        // Configurações do GridBagLayout para os botões dentro do buttonPanel
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.weightx = 1.0; // Faz com que cada botão se estique horizontalmente
        gbcButtons.fill = GridBagConstraints.HORIZONTAL; // Preenche o espaço horizontal
        gbcButtons.insets = new Insets(0, 10, 0, 10); // Espaçamento entre os botões

        // Cria Botão "Sim"
        JButton simButton = createStyledButton("", new Color(40, 160, 40), "/Assets/Imagens/sim.png", 40, 40);
        simButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        // Cria Botão "Não"
        JButton naoButton = createStyledButton("", new Color(160, 40, 40), "/Assets/Imagens/nao.png", 40, 40);
        naoButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        // Adiciona os botões ao painel de botões
        gbcButtons.gridx = 0; // Botão "Sim" na primeira coluna do buttonPanel
        buttonPanel.add(simButton, gbcButtons);

        gbcButtons.gridx = 1; // Botão "Não" na segunda coluna do buttonPanel
        buttonPanel.add(naoButton, gbcButtons);

        // Adiciona o painel de botões ao painel de conteúdo principal
        gbc.gridy = 1; // Segunda linha
        gbc.insets = new Insets(10, 20, 20, 20); // Margens
        contentPanel.add(buttonPanel, gbc);

        // Define o painel de conteúdo como o conteúdo principal do diálogo
        setContentPane(contentPanel);

        // Ajusta o tamanho do diálogo e centraliza na tela pai
        pack();
        setLocationRelativeTo(parent);
    }

    // Carrega e redimensiona ícones
    private ImageIcon carregarIcone(String path, int width, int height) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                Image img = ImageIO.read(is).getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar ícone: " + path + " - " + e.getMessage());
        }
        return null;
    }

    // Cria botões estilizados com ícones e efeitos de hover
    private JButton createStyledButton(String text, Color bgColor, String iconPath, int iconWidth, int iconHeight) {
        JButton button = new JButton();

        ImageIcon buttonIcon = carregarIcone(iconPath, iconWidth, iconHeight);

        if (buttonIcon != null) {
            JLabel iconLabel = new JLabel(buttonIcon);
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            iconLabel.setVerticalAlignment(SwingConstants.CENTER);

            button.setLayout(new BorderLayout());
            button.add(iconLabel, BorderLayout.CENTER);
        } else {
            button.setText(text);
            button.setHorizontalAlignment(SwingConstants.CENTER);
        }

        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(new RoundBorder(15, bgColor));

        // Define tamanho mínimo e máximo para controle do esticamento
        button.setMinimumSize(new Dimension(80, 80));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // Permite esticar horizontalmente

        // Cores para efeitos de mouse
        Color hoverColor = new Color(Math.max(0, bgColor.getRed() - 20),
                                      Math.max(0, bgColor.getGreen() - 20),
                                      Math.max(0, bgColor.getBlue() - 20));
        Color pressedColor = new Color(Math.max(0, bgColor.getRed() - 40),
                                       Math.max(0, bgColor.getGreen() - 40),
                                       Math.max(0, bgColor.getBlue() - 40));

        // Adiciona listeners para efeitos de mouse
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(pressedColor);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (button.contains(evt.getPoint())) {
                    button.setBackground(hoverColor);
                } else {
                    button.setBackground(bgColor);
                }
            }
        });

        return button;
    }

    // Retorna se a ação foi confirmada
    public boolean isConfirmed() {
        return confirmed;
    }

    // Classe interna: RoundBorder
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