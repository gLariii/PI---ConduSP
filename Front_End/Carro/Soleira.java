package Carro;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import CabineDeControleTela.*;

public class Soleira extends JPanel {
    private Image imagemDeFundo; 
    private JFrame parentFrame; 

    // Botões da interface
    private JButton btnVoltar;

    private int ordemCliques; 
    private int idUsuarioLogado; 
    private String tipo_usuarioLogado; 

    public Soleira(JFrame frame, String tipo_usuario, int idUsuario) {
        this.tipo_usuarioLogado = tipo_usuario;
        this.idUsuarioLogado = idUsuario;
        this.ordemCliques = ordemCliques;
        ordemCliques++;

        this.parentFrame = frame;
        setLayout(null); // Layout absoluto
        adicionarBotoes(); 

        // Listener para reposicionar botões ao redimensionar a janela
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
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/Soleira.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);

        int w = getWidth();
        int h = getHeight();

        // Desenha o ícone da chave se coletada
        if (PainelCBTCeChave.indexChave == 1) {
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/ChaveIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.9), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }

        // Desenha o cinto e o adesivo se coletados
        if (Cinturao.index == 1) {
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/CinturaoIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.8), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);

            Image imagemExtra2 = new ImageIcon(getClass().getResource("/Assets/Imagens/AdesivoIcone.png")).getImage();
            g.drawImage(imagemExtra2, (int)(w * 0.7), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }
    }

    // Adiciona e configura os botões
    private void adicionarBotoes() {
        btnVoltar = criarBotao(() -> substituirPainel(new Portas(parentFrame, tipo_usuarioLogado, idUsuarioLogado)));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        add(btnVoltar);
        reposicionarBotoes();
    }

    // Cria um botão com ação específica
    private JButton criarBotao(Runnable action) {
        JButton botao = new JButton("Voltar");
        botao.addActionListener(e -> action.run());
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setForeground(Color.BLACK); 
        return botao;
    }

    // Define a posição e tamanho do botão
    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        repaint();
    }

    // Troca o painel atual por um novo
    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
