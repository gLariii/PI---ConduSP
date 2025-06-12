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

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        // Use GridBagLayout para um controle mais preciso do layout geral do diálogo
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Cores.AZUL_METRO);
        contentPanel.setBorder(new RoundBorder(20, Cores.AZUL_METRO));

        GridBagConstraints gbc = new GridBagConstraints(); // Objeto para configurar constraints do GridBagLayout
        gbc.gridx = 0; // Coluna 0 para todos os componentes principais
        gbc.weightx = 3.0; // Pega todo o espaço horizontal disponível
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza horizontalmente

        // 1. Painel para a mensagem e o ícone
        JPanel iconAndTextPanel = new JPanel();
        iconAndTextPanel.setLayout(new BoxLayout(iconAndTextPanel, BoxLayout.Y_AXIS)); 
        iconAndTextPanel.setOpaque(false); // Torna transparente para ver o fundo

        ImageIcon avisoIcon = carregarIcone("/Assets/Imagens/aviso.png", 70, 70);

        JLabel avisoLabel = new JLabel();
        if (avisoIcon != null) {
            avisoLabel.setIcon(avisoIcon);
        }
        // Centraliza o ícone horizontalmente dentro do BoxLayout
        avisoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // <-- ADIÇÃO

        // Cria e configura o rótulo da mensagem de confirmação
        // Usamos HTML para quebrar a linha e centralizar o texto internamente
        JLabel messageLabel = new JLabel("<html><center>Você tem certeza <br>que deseja sair?</center></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 32));
        messageLabel.setForeground(Color.WHITE);
        // Centraliza o texto horizontalmente dentro do BoxLayout
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // <-- ADIÇÃO
        messageLabel.setBorder(new EmptyBorder(5, 0, 0, 0)); // Pequena margem superior para separar do ícone

        // Adiciona o ícone e o texto ao painel, na ordem desejada
        iconAndTextPanel.add(avisoLabel); // Primeiro o ícone
        iconAndTextPanel.add(Box.createVerticalStrut(10)); // Espaçamento entre ícone e texto
        iconAndTextPanel.add(messageLabel); // Depois o texto

        // Adiciona o painel que contém o ícone e o texto ao contentPanel principal
        gbc.gridy = 0; // Linha 0 no GridBagLayout
        gbc.insets = new Insets(20, 0, 10, 0); // Margem superior 20, inferior 10
        contentPanel.add(iconAndTextPanel, gbc);


        // 2. Painel para os botões "Sim" e "Não"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setOpaque(false);

        JButton simButton = createStyledButton("", new Color(40, 160, 40), "/Assets/Imagens/sim.png", 40, 40);
        simButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        JButton naoButton = createStyledButton("", new Color(160, 40, 40), "/Assets/Imagens/nao.png", 40, 40);
        naoButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(simButton);
        buttonPanel.add(naoButton);

        // Adiciona o buttonPanel ao contentPanel usando GridBagLayout
        gbc.gridy = 1; // Linha 1 no GridBagLayout
        gbc.insets = new Insets(10, 0, 20, 0); // Margem superior 10, inferior 20
        contentPanel.add(buttonPanel, gbc);

        // Define o contentPanel como o painel de conteúdo do diálogo
        setContentPane(contentPanel);

        // Ajusta o tamanho do diálogo ao seu conteúdo e centraliza-o na tela pai
        pack();
        setLocationRelativeTo(parent);
    }

    // Método auxiliar para carregar e redimensionar ícones
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

    // Método auxiliar para criar botões estilizados com ícones e efeitos de hover
    private JButton createStyledButton(String text, Color bgColor, String iconPath, int iconWidth, int iconHeight) {
        JButton button = new JButton();

        ImageIcon buttonIcon = carregarIcone(iconPath, iconWidth, iconHeight);

        if (buttonIcon != null) {
            JLabel iconLabel = new JLabel(buttonIcon);
            iconLabel.setPreferredSize(new Dimension(iconWidth, iconHeight));
            iconLabel.setMaximumSize(new Dimension(iconWidth, iconHeight));
            iconLabel.setMinimumSize(new Dimension(iconWidth, iconHeight));

            button.setLayout(new BorderLayout()); // Usa BorderLayout para centralizar o ícone
            button.add(iconLabel, BorderLayout.CENTER);
        } else {
            button.setText(text);
        }

        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(new RoundBorder(15, bgColor));

        button.setPreferredSize(new Dimension(140, 80));

        Color hoverColor = new Color(Math.max(0, bgColor.getRed() - 20),
                                      Math.max(0, bgColor.getGreen() - 20),
                                      Math.max(0, bgColor.getBlue() - 20));
        Color pressedColor = new Color(Math.max(0, bgColor.getRed() - 40),
                                       Math.max(0, bgColor.getGreen() - 40),
                                       Math.max(0, bgColor.getBlue() - 40));

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

    // Método para retornar o estado de confirmação (true se "Sim", false se "Não")
    public boolean isConfirmed() {
        return confirmed;
    }

    // Classe interna para criar bordas arredondadas personalizadas
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