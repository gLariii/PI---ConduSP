package CabineDeControleTela;
import ChaveReversoraTela.ChaveReversoraTela;
import DDUTela.DDUMenu;
import ModuloDeComunicaçãoTela.ModuloDeComunicacaoTelaInicial;
import VDUTelas.VDUMenu;
import javax.swing.*;

import BoteiraLateralTelas.BoteiraLateralTela;

import java.awt.*;

public class CabineDeControleTela extends JPanel {

    private Image imagemDeFundo;

    private JFrame parentFrame;

    public CabineDeControleTela(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
        // Carrega imagem (caminho absoluto ou relativo ajustado)
        ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/01 - Painel (1).jpg"));
        imagemDeFundo = icon.getImage();

        // Botões
        JButton botao1 = new JButton("Chave Reversora");
        botao1.setBounds(570, 550, 120, 55);
        botao1.addActionListener(e -> substituirPainel(new ChaveReversoraTela(parentFrame)));
        botao1.setOpaque(false);
        botao1.setContentAreaFilled(false);
        botao1.setBorderPainted(false);
        botao1.setFocusPainted(false);
        botao1.setForeground(new Color(0, 0, 0, 0));
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao1);

        JButton botao2 = new JButton("Modulo de Comunicação");
        botao2.setBounds(490, 400, 180, 80);
        botao2.addActionListener(e -> substituirPainel(new ModuloDeComunicacaoTelaInicial(parentFrame)));
        botao2.setOpaque(false);
        botao2.setContentAreaFilled(false);
        botao2.setBorderPainted(false);
        botao2.setFocusPainted(false);
        botao2.setForeground(new Color(0, 0, 0, 0));
        botao2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao2);

        JButton botao3 = new JButton("DDU Tela Principal");
        botao3.setBounds(150, 455, 270, 120);
        botao3.addActionListener(e -> substituirPainel(new DDUMenu(parentFrame)));
        botao3.setOpaque(false);
        botao3.setContentAreaFilled(false);
        botao3.setBorderPainted(false);
        botao3.setFocusPainted(false);
        botao3.setForeground(new Color(0, 0, 0, 0));
        botao3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao3);

        JButton botao4 = new JButton("VDU Tela Principal");
        botao4.setBounds(1450, 455, 320, 120);
        botao4.addActionListener(e -> substituirPainel(new VDUMenu(parentFrame)));
        botao4.setOpaque(false);
        botao4.setContentAreaFilled(false);
        botao4.setBorderPainted(false);
        botao4.setFocusPainted(false);
        botao4.setForeground(new Color(0, 0, 0, 0));
        botao4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao4);

        JButton botao5 = new JButton("<html>B<br>a<br>r<br>r<br>a<br><br>L<br>a<br>t<br>e<br>r<br>a<br>l</html>");
        botao5.setBounds(10, 420, 70, 270);
        botao5.addActionListener(e -> substituirPainel(new AreaLateral(parentFrame)));
        
        botao5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao5);
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
            JFrame frame = new JFrame("Cabine de Controle");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new CabineDeControleTela(frame));
            frame.setVisible(true);
        });
    }
}
