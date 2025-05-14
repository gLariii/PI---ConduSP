package ModuloDeComunicaçãoTela;
import CabineDeControleTela.CabineDeControleTela;
import javax.swing.*;
import java.awt.*;
public class PATela extends JPanel {

    private Image imagemDeFundo;
    
    private JFrame parentFrame;

    public PATela(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
        // Carrega imagem (caminho absoluto ou relativo ajustado)
        ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/módulo de comunicação - PA.jpg"));
        imagemDeFundo = icon.getImage();

        // Botões
         JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 10, 100, 30);
        btnVoltar.addActionListener(e -> substituirPainel(new CabineDeControleTela(parentFrame)));   
        add(btnVoltar);
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
            JFrame frame = new JFrame("Modulo de Comunicação");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new PATela(frame));
            frame.setVisible(true);
        });
    }
}
