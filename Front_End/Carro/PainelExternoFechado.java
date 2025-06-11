package Carro;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import CabineDeControleTela.*;

public class PainelExternoFechado extends JPanel {
    private Image imagemDeFundo; // Imagem de fundo do painel
    private JFrame parentFrame; // Janela principal

    // Botões como atributos
    private JButton btnAbrir, btnVoltar;

    private int ordemCliques; 
    private int idUsuarioLogado; 
    private String tipo_usuarioLogado; 

    public PainelExternoFechado(JFrame frame, String tipo_usuario, int idUsuario) {
        this.tipo_usuarioLogado = tipo_usuario; 
        this.idUsuarioLogado = idUsuario; 
        this.parentFrame = frame; // Referência da janela principal
        setLayout(null); // Layout absoluto
        adicionarBotoes(); 

        // Listener para redimensionamento do painel
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes(); // Reposiciona os botões ao redimensionar
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo == null) { // Carrega a imagem de fundo
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/PainelExternoFechado.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this); // Desenha a imagem de fundo
        int w = getWidth();
        int h = getHeight();

        if (PainelCBTCeChave.indexChave == 1) { // Se chave está presente
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/ChaveIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.9), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }
        if (Cinturao.index == 1) { // Se cinto está presente
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/CinturaoIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.8), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
            Image imagemExtra2 = new ImageIcon(getClass().getResource("/Assets/Imagens/AdesivoIcone.png")).getImage();
            g.drawImage(imagemExtra2, (int)(w * 0.7), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }
    }

    
    private void adicionarBotoes() {
        btnAbrir = criarBotao(() -> substituirPainel(new PainelExternoAberto(parentFrame, tipo_usuarioLogado, idUsuarioLogado))); 
        add(btnAbrir);

        // Botão Voltar
        btnVoltar = new JButton("Voltar"); 
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        btnVoltar.addActionListener(e -> {
            AudioPlayer.playSound("SomCaminhar.wav"); 
            substituirPainel(new Carro5VisaoGeral(parentFrame, tipo_usuarioLogado, idUsuarioLogado)); 
        });
        add(btnVoltar);
        reposicionarBotoes(); // Define posições iniciais
    }

    private JButton criarBotao(Runnable action) { 
        JButton botao = new JButton("");
        botao.addActionListener(e -> {
            AudioPlayer.playSound("SomPorta.wav"); 
            action.run(); // Executa ação ao clicar 
        });
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setForeground(new Color(0, 0, 0, 0));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private void reposicionarBotoes() { // Reposiciona os botões
        int w = getWidth();
        int h = getHeight();

        // Define tamanho e posição dos botões
        btnAbrir.setBounds((int)(w * 0.29), (int)(h * 0.32), (int)(w * 0.37), (int)(h * 0.42));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        repaint(); // Atualiza o painel
    }

    private void substituirPainel(JPanel novoPainel) { 
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
