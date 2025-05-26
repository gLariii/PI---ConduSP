package CabineDeControleTela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CabineTraseira extends JPanel {

    private JFrame parentFrame;
    private Image imagemDeFundoLateral;

    private JButton botao1;
    private JButton btnVoltar;

    public CabineTraseira(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
        adicionarBotoes();

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundoLateral == null) {
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/CabineTraseira.jpg"));
            imagemDeFundoLateral = icon.getImage();
        }
        g.drawImage(imagemDeFundoLateral, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarBotoes() {
        botao1 = new JButton();
        botao1.addActionListener(e -> trocarTela(new Cinturao(parentFrame)));
        botao1.setOpaque(false);
        botao1.setContentAreaFilled(false);
        botao1.setBorderPainted(true);
        botao1.setFocusPainted(false);
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao1);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame)));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnVoltar);

        reposicionarBotoes();
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        botao1.setBounds((int)(w * 0.76), (int)(h * 0.30), (int)(w * 0.14), (int)(h * 0.20));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));

        repaint();
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
