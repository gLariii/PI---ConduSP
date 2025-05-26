package Carro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PainelExternoAberto extends JPanel {

    private final String[] backgrounds = {
        "Imagens/PainelExternoAberto(zoom).jpg",
        "Imagens/PainelExternoAbertoISOL.jpg",
    };

    public static int index = 0;
    private Image imagemDeFundo;
    private JFrame parentFrame;
    private JButton btnTrocar;
    private JButton btnVoltar;
    private JButton btnFechar;

    public PainelExternoAberto(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);

        carregarImagemFundo();

        // Botão para trocar o fundo
        btnTrocar = new JButton("");
        btnTrocar.setOpaque(false);
        btnTrocar.setContentAreaFilled(false);
        btnTrocar.setBorderPainted(true);
        btnTrocar.setFocusPainted(false);
        btnTrocar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTrocar.setForeground(new Color(0, 0, 0, 0));
        btnTrocar.addActionListener(e -> {
            index = (index + 1) % backgrounds.length;
            carregarImagemFundo();
            repaint();
        });
        add(btnTrocar);

        btnFechar = new JButton("Fechar");
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.setOpaque(false);
        btnFechar.setContentAreaFilled(false);
        btnFechar.setBorderPainted(true);
        btnFechar.setFocusPainted(false);
        btnFechar.setForeground(new Color(0, 0, 0, 0));
        btnFechar.addActionListener(e -> substituirPainel(new PainelExternoFechado(parentFrame)));
        add(btnFechar);

        // Botão Voltar
        btnVoltar = new JButton("Voltar");
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> substituirPainel(new Carro5VisaoGeral(parentFrame)));
        add(btnVoltar);

        adicionarListenerRedimensionamento();
    }

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo != null) {
            g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void reposicionarComponentes() {
        int w = getWidth();
        int h = getHeight();

        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        btnTrocar.setBounds((int)(w * 0.415), (int)(h * 0.56), (int)(w * 0.09), (int)(h * 0.13));
        btnFechar.setBounds((int)(w * 0.125), (int)(h * 0), (int)(w * 0.75), (int)(h * 0.13));
    }

    private void adicionarListenerRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarComponentes();
            }
        });
    }
}
