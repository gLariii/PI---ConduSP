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
        ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/Modulo de comunicação Microfone PA.jpg"));
        imagemDeFundo = icon.getImage();

        // Botões
         JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 10, 100, 30);
        btnVoltar.addActionListener(e -> substituirPainel(new CabineDeControleTela(parentFrame)));   
        add(btnVoltar);

        JButton botao1 = new JButton("Módulo de comunicação");
        botao1.setBounds(505, 260, 250, 100);
        botao1.addActionListener(e -> substituirPainel(new ModuloDeComunicacaoTelaInicial(parentFrame)));
        botao1.setOpaque(false);
        botao1.setContentAreaFilled(false);
        botao1.setBorderPainted(false);
        botao1.setFocusPainted(false);
        botao1.setForeground(new Color(0, 0, 0, 0));
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
}
