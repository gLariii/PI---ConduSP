package Carro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import CabineDeControleTela.*;
import Model.SalvarResposta;

public class PainelExternoAberto extends JPanel {

    private final String[] backgrounds = {
        "Imagens/PainelExternoAberto(zoom).jpg",
        "Imagens/PainelExternoAbertoISOL.jpg",
    };

    public static int index = 0;
    private Image imagemDeFundo;
    private JFrame parentFrame;

    private JButton btnVoltar; 
    private JButton btnFechar; 

    // Botões das portas
    private JButton btnPorta1;
    private JButton btnPorta2;
    private JButton btnPorta3;
    private JButton btnPorta4;
    private JButton btnPorta5;
    private JButton btnPorta6;
    private JButton btnPorta7;
    private JButton btnPorta8;

    private int idUsuarioLogado;
    private String tipo_usuarioLogado; 
    private static boolean primeiroClique = true; // Primeiro clique porta
    private static boolean primeiroCliqueFechar = true; // Primeiro clique fechar
    private int feedback;
    private int ordemCliques;

    public PainelExternoAberto(JFrame frame, String tipo_usuario, int idUsuario) {
        this.parentFrame = frame;
        this.idUsuarioLogado = idUsuario;
        this.tipo_usuarioLogado = tipo_usuario;
        this.ordemCliques = ordemCliques;
        setLayout(null);

        carregarImagemFundo(); // Carrega imagem fundo

        // Cria botões das portas
        btnPorta1 = criarBotaoPorta(1);
        btnPorta2 = criarBotaoPorta(2);
        btnPorta3 = criarBotaoPorta(3);
        btnPorta4 = criarBotaoPorta(4);
        btnPorta5 = criarBotaoPorta(5);
        btnPorta6 = criarBotaoPorta(6);
        btnPorta7 = criarBotaoPorta(7);
        btnPorta8 = criarBotaoPorta(8);

        // Adiciona botões das portas
        add(btnPorta1);
        add(btnPorta2);
        add(btnPorta3);
        add(btnPorta4);
        add(btnPorta5);
        add(btnPorta6);
        add(btnPorta7);
        add(btnPorta8);

        btnFechar = new JButton("Fechar"); 
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.setOpaque(false);
        btnFechar.setContentAreaFilled(false);
        btnFechar.setBorderPainted(true);
        btnFechar.setFocusPainted(false);
        btnFechar.setForeground(new Color(0, 0, 0, 0));
        btnFechar.addActionListener(e -> {
            AudioPlayer.playSound("SomPorta.wav");
            if (primeiroCliqueFechar) {
                SalvarResposta.pontuacao += 1;
                this.feedback = 24;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                primeiroCliqueFechar = false;
            }
            substituirPainel(new PainelExternoFechado(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
        });
        add(btnFechar);

        btnVoltar = new JButton("Voltar"); // Botão Voltar tela
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        btnVoltar.addActionListener(e -> {
            if (primeiroCliqueFechar) {
                SalvarResposta.pontuacao -= 3;
                this.feedback = 25;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                primeiroCliqueFechar = false;
            }
            substituirPainel(new Carro5VisaoGeral(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
        });
        add(btnVoltar);

        adicionarListenerRedimensionamento(); // Listener redimensionamento
    }

    // Cria botão invisível para porta
    private JButton criarBotaoPorta(int portaIndex) {
        JButton btn = new JButton("");
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setForeground(new Color(0, 0, 0, 0));

        btn.addActionListener(e -> {
            if (portaIndex != 6) { // Porta errada
                AudioPlayer.playSound("SomErro.wav");
                JOptionPane.showMessageDialog(this, "Você clicou no botão errado!", "Aviso", JOptionPane.WARNING_MESSAGE);
                SalvarResposta.pontuacao -= 3;
                this.feedback = 23;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                primeiroClique = false;
            } else { // Porta correta
                ChaveReversoraTela.AudioPlayer.playSound("SomAlavanca.wav");
                index = (index + 1) % backgrounds.length;
                if (primeiroClique) {
                    SalvarResposta.pontuacao += 3;
                    this.feedback = 22;
                    SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                    primeiroClique = false;
                }
                carregarImagemFundo();
                repaint();
            }
        });
        return btn;
    }

    // Carrega imagem de fundo
    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }

    // Troca o painel atual
    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo != null) {
            g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }
        int w = getWidth();
        int h = getHeight();
        // Mostra ícone chave
        if (PainelCBTCeChave.indexChave == 1) {
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/ChaveIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.9), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }
        // Mostra ícones cinto e adesivo
        if (Cinturao.index == 1) {
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/CinturaoIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.8), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
            Image imagemExtra2 = new ImageIcon(getClass().getResource("/Assets/Imagens/AdesivoIcone.png")).getImage();
            g.drawImage(imagemExtra2, (int)(w * 0.7), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }
    }

    // Reposiciona botões ao redimensionar
    private void reposicionarComponentes() {
        int w = getWidth();
        int h = getHeight();

        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        btnFechar.setBounds((int)(w * 0.125), 0, (int)(w * 0.75), (int)(h * 0.13));

        btnPorta1.setBounds((int)(w * 0.29), (int)(h * 0.29), (int)(w * 0.08), (int)(h * 0.12));
        btnPorta2.setBounds((int)(w * 0.42), (int)(h * 0.29), (int)(w * 0.08), (int)(h * 0.12));
        btnPorta3.setBounds((int)(w * 0.55), (int)(h * 0.29), (int)(w * 0.08), (int)(h * 0.12));
        btnPorta4.setBounds((int)(w * 0.69), (int)(h * 0.29), (int)(w * 0.08), (int)(h * 0.12));

        btnPorta5.setBounds((int)(w * 0.29), (int)(h * 0.565), (int)(w * 0.08), (int)(h * 0.12));
        btnPorta6.setBounds((int)(w * 0.42), (int)(h * 0.565), (int)(w * 0.08), (int)(h * 0.12));
        btnPorta7.setBounds((int)(w * 0.55), (int)(h * 0.565), (int)(w * 0.08), (int)(h * 0.12));
        btnPorta8.setBounds((int)(w * 0.69), (int)(h * 0.565), (int)(w * 0.08), (int)(h * 0.12));
    }

    // Adiciona listener para redimensionamento
    private void adicionarListenerRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarComponentes();
            }
        });
    }
}
