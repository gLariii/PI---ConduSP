package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException; // Importação adicionada
import java.io.InputStream; // Importação adicionada
import javax.imageio.ImageIO; // Importação adicionada
import javax.swing.border.AbstractBorder; // Importação adicionada
import javax.swing.border.Border; // Importação adicionada

import Assets.Cores; // Assumindo que a classe Cores existe no pacote Assets

public class ConfirmarJogar extends JDialog {
    private boolean confirmed = false;

    public ConfirmarJogar(JFrame parentFrame) {
        super(parentFrame, "Iniciar Simulação", true); // true para modal
        setUndecorated(true); // Remove a barra de título padrão
        setBackground(new Color(0, 0, 0, 0)); // Torna o fundo transparente para a borda arredondada

        // Painel de conteúdo principal com layout BorderLayout para organização
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Cores.AZUL_GRADIENTE_START); // Fundo do diálogo para contraste
        contentPanel.setBorder(new RoundBorder(20, Cores.AZUL_GRADIENTE_START, 3)); // Borda arredondada personalizada

        // Painel para o ícone e a mensagem na parte superior
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        messagePanel.setOpaque(false); // Transparente para usar o fundo do contentPanel

        // Adicionar um ícone
        JLabel iconLabel = new JLabel();
        try (InputStream is = getClass().getResourceAsStream("/Assets/Imagens/metro.png")) { // Caminho do ícone
            if (is != null) {
                ImageIcon originalIcon = new ImageIcon(ImageIO.read(is));
                Image scaledImage = originalIcon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                System.err.println("Ícone do metrô não encontrado em /Assets/Imagens/metro.png para ConfirmarJogarDialog.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar ícone para ConfirmarJogarDialog: " + e.getMessage());
        }
        messagePanel.add(iconLabel);

        JLabel messageLabel = new JLabel("Tem certeza que deseja iniciar a simulação do maquinário?");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messagePanel.add(messageLabel);

        contentPanel.add(messagePanel, BorderLayout.CENTER);

        // Painel para os botões na parte inferior
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15)); // Espaçamento entre botões
        buttonPanel.setOpaque(false);

        // Botão "Sim, Iniciar"
        JButton confirmButton = new JButton("Sim, Iniciar");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setBackground(new Color(46, 204, 113)); // Verde vibrante
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorder(BorderFactory.createLineBorder(new Color(39, 174, 96), 2));
        confirmButton.setPreferredSize(new Dimension(150, 45));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                dispose(); // Fecha o diálogo
            }
        });
        buttonPanel.add(confirmButton);

        // Botão "Não, Cancelar"
        JButton cancelButton = new JButton("Não, Cancelar");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(new Color(231, 76, 60)); // Vermelho vibrante
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(192, 57, 43), 2));
        cancelButton.setPreferredSize(new Dimension(150, 45));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dispose(); // Fecha o diálogo
            }
        });
        buttonPanel.add(cancelButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona um listener para redimensionar a borda arredondada quando o diálogo for exibido
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                contentPanel.repaint(); // Garante que a borda seja redesenhada corretamente
            }
        });

        setContentPane(contentPanel);
        pack(); // Ajusta o tamanho do diálogo ao conteúdo
        setLocationRelativeTo(parentFrame); // Centraliza na tela pai
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    // Classe para borda arredondada (igual à do seu ConfiguracoesPanel)
    private static class RoundBorder extends AbstractBorder {
        private int radius;
        private Color color;
        private int thickness;

        RoundBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.color = color;
            this.thickness = thickness;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create(); 
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            
            // Desenha a borda mais grossa
            for (int i = 0; i < thickness; i++) {
                g2.drawRoundRect(x + i, y + i, width - 1 - 2 * i, height - 1 - 2 * i, radius, radius);
            }
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2 + thickness, radius / 2 + thickness, radius / 2 + thickness, radius / 2 + thickness);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = this.radius / 2 + this.thickness;
            return insets;
        }
    }
}