package ChaveReversoraTela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import CabineDeControleTela.CabineDeControleTela;

public class ChaveReversoraTela extends JPanel {

    private final String[] backgrounds = {
        "ChaveReversoraFrente.jpg",
        "ChaveReversoraNeutro.jpg"
    };

    private int index = 0;
    private Image imagemDeFundo;
    private JFrame parentFrame;
    private JButton btnTrocar;
    private JButton btnVoltar;

    public ChaveReversoraTela(JFrame frame) {
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
        btnTrocar.addActionListener(e -> trocarImagemFundo());
        add(btnTrocar);

        // Botão Voltar
        btnVoltar = new JButton("Voltar");
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> voltarParaCabine());
        add(btnVoltar);

        adicionarListenerRedimensionamento();
        reposicionarComponentes();
    }

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }

    private void trocarImagemFundo() {
        index = (index + 1) % backgrounds.length;
        carregarImagemFundo();
        repaint();
    }

    private void voltarParaCabine() {
        parentFrame.setContentPane(new CabineDeControleTela(parentFrame));
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

        //Tamanho e Posicionamento
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        btnTrocar.setBounds((int)(w * 0.396), (int)(h * 0.296), (int)(w * 0.208), (int)(h * 0.407));
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
