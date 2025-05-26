package Carro;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class Soleira extends JPanel {
    private Image imagemDeFundo;
    private JFrame parentFrame;

    // Botões como atributos
    private JButton  btnVoltar;

    public Soleira(JFrame frame) {
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
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/Soleira.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarBotoes() {
        btnVoltar = criarBotao(() -> substituirPainel(new Portas(parentFrame)));
        add(btnVoltar);
        reposicionarBotoes();
    }

    private JButton criarBotao(Runnable action) {
        JButton botao = new JButton("Voltar");
        botao.addActionListener(e -> action.run());
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setForeground(Color.BLACK); // cor padrão da borda
        
        return botao;
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
