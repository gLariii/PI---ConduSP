package ADUTela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import CabineDeControleTela.*;
import Assets.*;

public class ADUMenu extends JPanel {

    private JFrame parentFrame;
    private Image imagemDeFundo;
    private JButton btnVoltar;

    public ADUMenu(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
        setSize(frame.getSize());

        adicionarComponentes();

        adicionarListenerDeRedimensionamento();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo == null) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/ADUTela/imagens/ADUMenu.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        
    }

    private void adicionarComponentes() {
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame, 0)));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnVoltar);

        reposicionarComponentes();
    }

    private void reposicionarComponentes() {
        int w = getWidth();
        int h = getHeight();

        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        
        repaint();
    }

    private void adicionarListenerDeRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarComponentes();
            }
        });
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
