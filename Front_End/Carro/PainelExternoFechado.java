package Carro;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class PainelExternoFechado extends JPanel {
    private Image imagemDeFundo;
    private JFrame parentFrame;

    // Botões como atributos
    private JButton btnAbrir, btnVoltar;

    private int ordemCliques;

    public PainelExternoFechado(JFrame frame, int ordemCliques) {
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
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/PainelExternoFechado.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarBotoes() {
        btnAbrir = criarBotao(() -> substituirPainel(new PainelExternoAberto(parentFrame, ordemCliques)));
        add(btnAbrir);
        btnVoltar = new JButton("Voltar");
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> substituirPainel(new Carro5VisaoGeral(parentFrame, ordemCliques)));
        add(btnVoltar);
        reposicionarBotoes();
        
    }

    private JButton criarBotao(Runnable action) {
        JButton botao = new JButton("");
        botao.addActionListener(e -> action.run());
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(true);
        botao.setFocusPainted(false);
        botao.setForeground(new Color(0, 0, 0, 0));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        //Tamanho e Posicionamento
        btnAbrir.setBounds((int)(w * 0.29), (int)(h * 0.32), (int)(w * 0.37), (int)(h * 0.42));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        repaint();
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
