package BoteiraLateralTelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import CabineDeControleTela.AreaLateral;

public class BoteiraLateralTela extends JPanel {

    private final String[] backgrounds = {
        "Imagens/BarraLateralPortasFechadas.jpg",
        "Imagens/BarraLateralPortasAbertas.jpg"
    };

    private int index = 0;
    private Image imagemDeFundo;
    private JFrame parentFrame;
    private JButton btnTrocar;
    private JButton btnVoltar;

    public BoteiraLateralTela(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);

        carregarImagemFundo();

        // Botão para trocar o fundo
        btnTrocar = new JButton("");
        btnTrocar.setOpaque(false);
        btnTrocar.setContentAreaFilled(false);
        btnTrocar.setBorderPainted(false);
        btnTrocar.setFocusPainted(false);
        btnTrocar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTrocar.setForeground(new Color(0, 0, 0, 0));
        btnTrocar.addActionListener(e -> {
            index = (index + 1) % backgrounds.length;
            carregarImagemFundo();
            reposicionarComponentes();
            repaint();
        });
        add(btnTrocar);

        // Botão Voltar
        btnVoltar = new JButton("Voltar");
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> voltarParaLateral());
        add(btnVoltar);

        adicionarListenerRedimensionamento();
        reposicionarComponentes();
    }

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }

    private void voltarParaLateral() {
        parentFrame.setContentPane(new AreaLateral(parentFrame));
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

        // Troca posição do botão de acordo com a imagem de fundo
        if (index == 0) {
            btnTrocar.setBounds((int)(w * 0.479), (int)(h * 0.481), (int)(w * 0.042), (int)(h * 0.074));
        } else {
            btnTrocar.setBounds((int)(w * 0.479), (int)(h * 0.657), (int)(w * 0.042), (int)(h * 0.074));
        }
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
