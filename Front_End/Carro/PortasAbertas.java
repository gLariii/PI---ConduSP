package Carro;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class PortasAbertas extends JPanel {
    private Image imagemDeFundo;
    private JFrame parentFrame;

    // Botões como atributos
    private JButton botao1, botao2, btnVoltar;

    public PortasAbertas(JFrame frame) {
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
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/PortaAberta.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarBotoes() {
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> substituirPainel(new Carro5VisaoGeral(parentFrame)));
        botao1 = criarBotao(() -> substituirPainel(new DispositivosDeEmergência(parentFrame)));
        botao2 = criarBotao(() -> substituirPainel(new Soleira(parentFrame)));
        add(botao1);
        add(botao2);
        add(btnVoltar);
        reposicionarBotoes();
    }

    private JButton criarBotao(Runnable action) {
        JButton botao = new JButton("");
        botao.addActionListener(e -> action.run());
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setForeground(Color.BLACK); // cor padrão da borda
        
        return botao;
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        //Tamanho e Posicionamento
        botao1.setBounds((int)(w * 0.66), (int)(h * 0.42), (int)(w * 0.08), (int)(h * 0.14));
        botao2.setBounds((int)(w * 0.37), (int)(h * 0.92), (int)(w * 0.28), (int)(h * 0.06));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        repaint();
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
