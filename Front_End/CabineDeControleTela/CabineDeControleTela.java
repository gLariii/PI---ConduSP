package CabineDeControleTela; 

import BoteiraLateralTelas.BoteiraLateralTela; 
import ChaveReversoraTela.ChaveReversoraTela; 
import DDUTela.DDUMenu; 
import ModuloDeComunicaçãoTela.ModuloDeComunicacaoTelaInicial; 
import VDUTelas.VDUMenu; 
import java.awt.*; 
import javax.swing.*; 

public class CabineDeControleTela extends JPanel { 
    private Image imagemDeFundo; 
    private JFrame parentFrame; 
    public CabineDeControleTela(JFrame frame) { 
        this.parentFrame = frame; 
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza a janela
        frame.setUndecorated(true); // Remove bordas e barra de título
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        setLayout(null); // Manter layout absoluto para definir posições manualmente 
    } 

    @Override 
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g); 
        // Aqui, garantimos que a imagem de fundo seja ajustada conforme o tamanho da tela 
        if (imagemDeFundo == null) { 
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/01 - Painel (1).jpg")); 
            imagemDeFundo = icon.getImage(); 
        } 
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this); 
        // Agora, ajustamos os botões depois que o painel foi renderizado 

        adicionarBotoes(); 

    } 

    private void adicionarBotoes() { 

        // Botões com posições relativas ao tamanho do painel 
        JButton botao1 = new JButton(""); 
        botao1.setBounds((int)(getWidth() * 0.29), (int)(getHeight() * 0.53), (int)(getWidth() * 0.08), (int)(getHeight() * 0.05)); 
        botao1.addActionListener(e -> substituirPainel(new ChaveReversoraTela(parentFrame))); 
        personalizarBotao(botao1); 
        add(botao1); 

        JButton botao2 = new JButton(""); 
        botao2.setBounds((int)(getWidth() * 0.25), (int)(getHeight() * 0.38), (int)(getWidth() * 0.08), (int)(getHeight() * 0.08)); 
        botao2.addActionListener(e -> substituirPainel(new ModuloDeComunicacaoTelaInicial(parentFrame))); 
        personalizarBotao(botao2);
        add(botao2); 

        JButton botao3 = new JButton(""); 
        botao3.setBounds((int)(getWidth() * 0.06), (int)(getHeight() * 0.43), (int)(getWidth() * 0.16), (int)(getHeight() * 0.14)); 
        botao3.addActionListener(e -> substituirPainel(new DDUMenu(parentFrame)));
        personalizarBotao(botao3); 
        add(botao3); 

        JButton botao4 = new JButton(""); 
        botao4.setBounds((int)(getWidth() * 0.75), (int)(getHeight() * 0.42), (int)(getWidth() * 0.17), (int)(getHeight() * 0.15)); 
        botao4.addActionListener(e -> substituirPainel(new VDUMenu(parentFrame))); 
        personalizarBotao(botao4);  
        add(botao4); 

        JButton botao5 = new JButton("<html>B<br>a<br>r<br>r<br>a<br><br>L<br>a<br>t<br>e<br>r<br>a<br>l</html>"); 
        botao5.setBounds((int)(getWidth() * 0.005), (int)(getHeight() * 0.55), (int)(getWidth() * 0.035), (int)(getHeight() * 0.25)); 
        botao5.addActionListener(e -> substituirPainel(new BoteiraLateralTela(parentFrame))); 
        botao5.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        botao5.setOpaque(false);
        botao5.setContentAreaFilled(false);  
        add(botao5); 

        // Revalida o painel para garantir que a disposição seja feita corretamente 
        revalidate(); 
        repaint(); 

    } 

    private void personalizarBotao(JButton botao) { 

        botao.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        botao.setOpaque(false);
        botao.setContentAreaFilled(false); 
        botao.setBorderPainted(false);

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