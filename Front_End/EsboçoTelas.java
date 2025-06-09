import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import Model.*;

public class EsboçoTelas extends JPanel {
    private Image imagemDeFundo;
    private JFrame parentFrame;
    private int idUsuarioLogado;
    private boolean primeiroClique = true;
    private int feedback;

    // Botões como atributos
    private JButton botao1;

    public EsboçoTelas(JFrame frame, int idUsuario) {
        this.parentFrame = frame;
        this.idUsuarioLogado = idUsuario;
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
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/01 - Painel (1).jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarBotoes() {
        //botao1 = criarBotao(() -> substituirPainel(new ChaveReversoraTela(parentFrame)));
        add(botao1);
        reposicionarBotoes();
    }

    private JButton criarBotao(Runnable action) {
        JButton botao = new JButton("");
        botao.addActionListener(e -> action.run());
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setForeground(new Color(0, 0, 0, 0));
        return botao;
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        //Tamanho e Posicionamento
        botao1.setBounds((int)(w * 0.29), (int)(h * 0.53), (int)(w * 0.08), (int)(h * 0.05));
        repaint();
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
