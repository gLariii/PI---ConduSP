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
        ImageIcon icon = new ImageIcon(getClass().getResource("/Front_End/ModuloDeComunicaçãoTela/Imagens/módulo de comunicação - PA.jpg"));
        imagemDeFundo = icon.getImage();

        // Botões
        JButton botao1 = new JButton("Cabine De Controle");
        botao1.setSize(400, 100); // Define o tamanho do botão

        // Posição inicial (vai ajustar automaticamente ao exibir)
        int panelWidth = 1920; 
        int panelHeight = 1080; 

        int x = (panelWidth - botao1.getWidth()) / 2;      // Centraliza horizontalmente
        int y = panelHeight - botao1.getHeight() - 80;     // 50px acima do rodapé

        botao1.setLocation(x, y);

        botao1.addActionListener(e -> substituirPainel(new CabineDeControleTela(parentFrame)));
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao1);
        
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
