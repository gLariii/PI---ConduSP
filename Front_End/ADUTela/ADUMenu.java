package ADUTela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ADUMenu extends JPanel {

    private JFrame parentFrame;
    private Image imagemDeFundo;

    public ADUMenu(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
        setSize(frame.getSize());

        adicionarListenerDeRedimensionamento();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo == null) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/ADUTela/images/ADUMenu.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarListenerDeRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });
    }
}
