package ChaveReversoraTela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import CabineDeControleTela.CabineDeControleTela;

public class ChaveReversoraTela extends JPanel {

    private String[] backgrounds = {
        "/Front_End/ChaveReversoraTela/ChaveReversoraFrente.jpg",
        "/Front_End/ChaveReversoraTela/ChaveReversoraNeutro.jpg"
    };

    private int index = 0;
    private BackgroundPanel backgroundPanel;
    private JButton btnTrocar;
    private JFrame parentFrame;

    public ChaveReversoraTela(JFrame frame) {
        this.parentFrame = frame;

        setLayout(new BorderLayout());
        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(null);
        add(backgroundPanel, BorderLayout.CENTER);

        btnTrocar = new JButton("Trocar Background");

        // Estilo do botão
        btnTrocar.setOpaque(false);
        btnTrocar.setContentAreaFilled(false);
        btnTrocar.setBorderPainted(false);
        btnTrocar.setFocusPainted(false);
        btnTrocar.setForeground(new Color(0, 0, 0, 0));

        btnTrocar.addActionListener(e -> {
            index = (index + 1) % backgrounds.length;
            backgroundPanel.setImage(backgrounds[index]);
        });

        backgroundPanel.add(btnTrocar);
        backgroundPanel.setImage(backgrounds[index]);

        // Ajustar botão ao redimensionar
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                backgroundPanel.repaint();
                adjustButtonSizeAndPosition();
            }
        });

        adjustButtonSizeAndPosition();

        // Botão de voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 10, 100, 30);
        btnVoltar.addActionListener(e -> voltarParaCabine());
        backgroundPanel.add(btnVoltar);
    }

    private void adjustButtonSizeAndPosition() {
        int buttonWidth = 200;
        int buttonHeight = 220;
        int x = (getWidth() - buttonWidth) / 2;
        int y = (getHeight() - buttonHeight) / 2 - 40;

        if (getWidth() > 800) {
            buttonWidth = 400;
            buttonHeight = 440;
            x = (getWidth() - buttonWidth) / 2;
            y = (getHeight() - buttonHeight) / 2 - 40;
        }

        btnTrocar.setBounds(x, y, buttonWidth, buttonHeight);
    }

    private void voltarParaCabine() {
        parentFrame.setContentPane(new CabineDeControleTela(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    // Painel interno com imagem
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
}
