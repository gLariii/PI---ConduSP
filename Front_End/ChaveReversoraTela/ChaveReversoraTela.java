package TelaMetro1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChaveReversoraTela extends JFrame {
    private String[] backgrounds = {
        "C:\\Users\\25.00552-1\\OneDrive - Instituto Mauá de Tecnologia\\PI\\Front\\ChaveReversora\\Imagens\\ChaveReversoraFrente.jpg",
        "C:\\Users\\25.00552-1\\OneDrive - Instituto Mauá de Tecnologia\\PI\\Front\\ChaveReversora\\Imagens\\ChaveReversoraNeutro.jpg"
    };
    private int index = 0;

    private BackgroundPanel backgroundPanel;
    private JButton btnTrocar;

    public ChaveReversoraTela() {
        setTitle("Trocar Background");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(null);  // Manter o layout null
        setContentPane(backgroundPanel);

        btnTrocar = new JButton("Trocar Background");

        // Estilo do botão
        btnTrocar.setOpaque(false);
        btnTrocar.setContentAreaFilled(false);
        btnTrocar.setBorderPainted(false);
        btnTrocar.setFocusPainted(false);
        btnTrocar.setForeground(new Color(0, 0, 0, 0)); 

        // Ação do botão
        btnTrocar.addActionListener(e -> {
            index = (index + 1) % backgrounds.length;
            backgroundPanel.setImage(backgrounds[index]);
        });

        backgroundPanel.add(btnTrocar);
        backgroundPanel.setImage(backgrounds[index]);

        setVisible(true);

        // Ajusta o layout ao redimensionar
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                backgroundPanel.repaint(); // Redesenha a imagem de fundo
                adjustButtonSizeAndPosition(); // Ajusta o botão no redimensionamento
            }
        });

        adjustButtonSizeAndPosition(); // Ajusta a posição inicial
    }

    // Método para ajustar o botão conforme o tamanho da janela
    private void adjustButtonSizeAndPosition() {
        int buttonWidth = 200;
        int buttonHeight = 220;
        int x = (getWidth() - buttonWidth) / 2;
        int y = (getHeight() - buttonHeight) / 2 - 40;

        // Aumenta o tamanho proporcionalmente se a janela for redimensionada
        if (getWidth() > 800) {
            buttonWidth = 400;
            buttonHeight = 440;
            x = (getWidth() - buttonWidth) / 2;
            y = (getHeight() - buttonHeight) / 2 - 40;
        }

        // Mantém o botão centralizado e ajusta o tamanho conforme necessário
        btnTrocar.setBounds(x, y, buttonWidth, buttonHeight);
    }

    // Painel que desenha imagem de fundo redimensionada
    class BackgroundPanel extends JPanel {
        private Image image;

        public void setImage(String path) {
            image = new ImageIcon(path).getImage();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChaveReversoraTela::new);
    }
}
