package CabineDeControleTela;
import ChaveReversoraTela.ChaveReversoraTela;
import DDUTela.DDUMenu;
import ModuloDeComunicaçãoTela.ModuloDeComunicacaoTelaInicial;
import VDUTelas.VDUMenu;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import javax.swing.border.Border;
import ADUTela.ADUMenu;
import Carro.*;

public class CabineDeControleTela extends JPanel {

    private Image imagemDeFundo;
    private JFrame parentFrame;

    private static int ordemCliques = 0;
    
        // Botões como atributos
        private JButton botao1, botao2, botao3, botao4, botao5, botao6, botao7;
    
        public CabineDeControleTela(JFrame frame, int ordemCliques) {
            this.ordemCliques = ordemCliques;
    
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
            int w = getWidth();
            int h = getHeight();
            if (PainelCBTCeChave.indexChave == 1) {
                Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/ChaveIcone.png")).getImage();
                g.drawImage(imagemExtra, (int)(w * 0.9), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
            }
            if (Cinturao.index == 1) {
                Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/CinturaoIcone.png")).getImage();
                g.drawImage(imagemExtra, (int)(w * 0.8), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
                Image imagemExtra2 = new ImageIcon(getClass().getResource("/Assets/Imagens/AdesivoIcone.png")).getImage();
                g.drawImage(imagemExtra2, (int)(w * 0.7), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
            }
        }
    
        private void adicionarBotoes() {
            botao1 = criarBotao(() -> substituirPainel(new ChaveReversoraTela(parentFrame, ordemCliques)));
            add(botao1);
    
            botao2 = criarBotao(() -> substituirPainel(new ModuloDeComunicacaoTelaInicial(parentFrame, ordemCliques)));
            add(botao2);
    
            botao3 = criarBotao(() -> substituirPainel(new DDUMenu(parentFrame, ordemCliques)));
            add(botao3);
    
            botao4 = criarBotao(() -> substituirPainel(new VDUMenu(parentFrame, ordemCliques)));
            add(botao4);

            botao5 = criarBotao(() -> substituirPainel(new ADUMenu(parentFrame)));
            add(botao5);
    
            botao6 = new JButton("<html><center>Barra<br>Lateral</center></html>");
            botao6.addActionListener(e -> substituirPainel(new AreaLateral(parentFrame, ordemCliques)));
            botao6.setCursor(new Cursor(Cursor.HAND_CURSOR));
            botao6.setBackground(new Color(30, 60, 90));
            botao6.setForeground(Color.WHITE);
            botao6.setFont(new Font("Segoe UI", Font.BOLD, 13));
            botao6.setFocusPainted(false);
            botao6.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
            botao6.setOpaque(true);
            add(botao6);

            botao7 = new JButton("<html><center>Painel<br>Chave</center></html>");
            botao7.addActionListener(e -> substituirPainel(new PainelCBTCeChave(parentFrame, ordemCliques)));
            botao7.setCursor(new Cursor(Cursor.HAND_CURSOR));
            botao7.setBackground(new Color(30, 60, 90));
            botao7.setForeground(Color.WHITE);
            botao7.setFont(new Font("Segoe UI", Font.BOLD, 13));
            botao7.setFocusPainted(false);
            botao7.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
            botao7.setOpaque(true);
            add(botao7);

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
            botao1.setBounds((int)(w * 0.29), (int)(h * 0.56), (int)(w * 0.08), (int)(h * 0.05));
            botao2.setBounds((int)(w * 0.25), (int)(h * 0.38), (int)(w * 0.08), (int)(h * 0.08));
            botao3.setBounds((int)(w * 0.06), (int)(h * 0.43), (int)(w * 0.16), (int)(h * 0.14));
            botao4.setBounds((int)(w * 0.75), (int)(h * 0.42), (int)(w * 0.17), (int)(h * 0.15));
            botao5.setBounds((int)(w * 0.375), (int)(h * 0.35), (int)(w * 0.20), (int)(h * 0.20));
            botao6.setBounds((int)(w * 0.005), (int)(h * 0.5), (int)(w * 0.035), (int)(h * 0.25));
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
            frame.setContentPane(new CabineDeControleTela(frame, ordemCliques));
        frame.setVisible(true);
    });
}

}
