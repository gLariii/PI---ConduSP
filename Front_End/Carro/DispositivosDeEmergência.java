package Carro;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class DispositivosDeEmergência extends JPanel {
    private Image imagemDeFundo;
    private JFrame parentFrame;

    // Botões como atributos
    private JButton btnVoltar;

    public DispositivosDeEmergência(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
        adicionarBotoes();

        // Listener para redimensionar os botões conforme a tela
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo == null) {
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/16 - Dispostivos de emergência.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarBotoes() {
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> substituirPainel(new Portas(parentFrame)));
        add(btnVoltar);
        reposicionarBotoes();
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        //Tamanho e Posicionamento
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        repaint();
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
