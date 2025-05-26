package CabineDeControleTela;
import ChaveReversoraTela.ChaveReversoraTela;
import DDUTela.DDUMenu;
import ModuloDeComunicaçãoTela.ModuloDeComunicacaoTelaInicial;
import VDUTelas.VDUMenu;
import alavancaComando.AlavancaFrenagem;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

import CabineDeControleTela.*;
import Carro.PainelExternoAberto;
import Carro.PainelExternoFechado;
import Carro.Portas;

public class CabineDeControleTela extends JPanel {

    private Image imagemDeFundo;
    private JFrame parentFrame;

    // Botões como atributos
    private JButton botao1, botao2, botao3, botao4, botao5, botao6, botao7;

    public CabineDeControleTela(JFrame frame) {
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
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/CabineOperario.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarBotoes() {
        botao1 = criarBotao(() -> substituirPainel(new ChaveReversoraTela(parentFrame)));
        add(botao1);

        botao2 = criarBotao(() -> substituirPainel(new ModuloDeComunicacaoTelaInicial(parentFrame)));
        add(botao2);

        botao3 = criarBotao(() -> substituirPainel(new DDUMenu(parentFrame)));
        add(botao3);

        botao4 = criarBotao(() -> substituirPainel(new VDUMenu(parentFrame)));
        add(botao4);

        botao5 = new JButton("<html>B<br>a<br>r<br>r<br>a<br><br>L<br>a<br>t<br>e<br>r<br>a<br>l</html>");
        botao5.addActionListener(e -> substituirPainel(new AreaLateral(parentFrame)));
        botao5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao5);

        botao7 = new JButton("<html>P<br>a<br>i<br>n<br>e<br>l<br><br>C<br>h<br>a<br>v<br>e</html>");
        botao7.addActionListener(e -> substituirPainel(new PainelCBTCeChave(parentFrame)));
        botao7.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao7);

        botao6 = criarBotao(() -> substituirPainel(new AlavancaFrenagem(parentFrame)));
        add(botao6);

        reposicionarBotoes();
    }

    private JButton criarBotao(Runnable action) {
        JButton botao = new JButton("");
        botao.addActionListener(e -> action.run());
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(true);
        botao.setFocusPainted(false);
        botao.setForeground(new Color(0, 0, 0, 0));
        return botao;
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        //Tamanho e Posicionamento
        botao1.setBounds((int)(w * 0.29), (int)(h * 0.56), (int)(w * 0.08), (int)(h * 0.05));
        botao2.setBounds((int)(w * 0.25), (int)(h * 0.38), (int)(w * 0.08), (int)(h * 0.08));
        botao3.setBounds((int)(w * 0.06), (int)(h * 0.43), (int)(w * 0.16), (int)(h * 0.14));
        botao4.setBounds((int)(w * 0.75), (int)(h * 0.42), (int)(w * 0.17), (int)(h * 0.15));
        botao5.setBounds((int)(w * 0.005), (int)(h * 0.5), (int)(w * 0.035), (int)(h * 0.25));
        botao6.setBounds((int)(w * 0.535), (int)(h * 0.60), (int)(w * 0.04), (int)(h * 0.17));
        botao7.setBounds((int)(w * 0.96), (int)(h * 0.5), (int)(w * 0.035), (int)(h * 0.25));

        repaint();
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

        // Tela cheia
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true); // opcional: remove bordas e barra de título
        frame.setContentPane(new CabineDeControleTela(frame));
        frame.setVisible(true);
    });
}

}
