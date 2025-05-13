package Front_End.CabineDeControleTela;
import Front_End.ChaveReversoraTela.ChaveReversoraTela;
import javax.swing.*;
import java.awt.*;

public class CabineDeControleTela extends JPanel {

    private Image imagemDeFundo;

    private JFrame parentFrame;

    public CabineDeControleTela(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
        // Carrega imagem (caminho absoluto ou relativo ajustado)
        ImageIcon icon = new ImageIcon(getClass().getResource("/Front_End/CabineDeControleTela/Imagens/01 - Painel (1).jpg"));
        imagemDeFundo = icon.getImage();

        // Botões
        JButton botao2 = new JButton("Chave Reversora");
        botao2.setBounds(570, 550, 120, 55);
        botao2.addActionListener(e -> substituirPainel(new ChaveReversoraTela(parentFrame)));
        botao2.setOpaque(false);
        botao2.setContentAreaFilled(false);
        botao2.setBorderPainted(false);
        botao2.setFocusPainted(false);
        botao2.setForeground(new Color(0, 0, 0, 0));
        botao2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Painel Principal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new CabineDeControleTela(frame));
            frame.setVisible(true);
        });
    }
}
