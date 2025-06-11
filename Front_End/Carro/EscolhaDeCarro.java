package Carro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import CabineDeControleTela.*;
import Model.SalvarResposta;

public class EscolhaDeCarro extends JPanel {

    // Caminho da imagem de fundo
    private final String imagemFundoPath = "Imagens/EscolhaDeCarro.jpg";
    private Image imagemDeFundo;
    private JFrame parentFrame;

    // Botões das portas e botão voltar
    private JButton btnVoltar;
    private JButton btnPorta1;
    private JButton btnPorta2;
    private JButton btnPorta3;
    private JButton btnPorta4;
    private JButton btnPorta5;
    private JButton btnPorta6;

    // Variáveis de controle
    private int ordemCliques;
    private int idUsuarioLogado;
    private String tipo_usuarioLogado; // Tipo do usuário logado
    private static boolean primeiroClique = true;
    private int feedback;

    // Construtor da classe
    public EscolhaDeCarro(JFrame frame, String tipo_usuario, int idUsuario) {
        this.parentFrame = frame;
        this.idUsuarioLogado = idUsuario;
        this.tipo_usuarioLogado = tipo_usuario;
        this.ordemCliques = ordemCliques;
        this.parentFrame = frame;
        setLayout(null);

        carregarImagemFundo(); // Carrega imagem de fundo

        // Criação dos botões das portas
        btnPorta1 = criarBotaoPorta(false);
        btnPorta2 = criarBotaoPorta(false);
        btnPorta3 = criarBotaoPorta(false);
        btnPorta4 = criarBotaoPorta(false);
        btnPorta5 = criarBotaoPorta(true); // Porta correta
        btnPorta6 = criarBotaoPorta(false);

        // Adiciona botões ao painel
        add(btnPorta1);
        add(btnPorta2);
        add(btnPorta3);
        add(btnPorta4);
        add(btnPorta5);
        add(btnPorta6);

        // Configura botão Voltar
        btnVoltar = new JButton("Voltar");
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        btnVoltar.addActionListener(e -> substituirPainel(new AreaLateral(parentFrame, tipo_usuarioLogado, idUsuarioLogado)));
        add(btnVoltar);

        adicionarListenerRedimensionamento(); // Ajusta componentes ao redimensionar
    }

    // Cria botões das portas
    private JButton criarBotaoPorta(boolean isBotaoCorreto) {
        JButton btn = new JButton("");
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setForeground(new Color(0, 0, 0, 0));

        // Ação do botão
        btn.addActionListener(e -> {
            if (isBotaoCorreto) {
                AudioPlayer.playSound("SomPorta.wav");
                substituirPainel(new Carro5VisaoGeral(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
                if (primeiroClique == true){
                    SalvarResposta.pontuacao += 1;
                    this.feedback = 18;
                    SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                    primeiroClique = false;
                }
            } else {
                SalvarResposta.pontuacao -= 3;
                this.feedback = 19;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                AudioPlayer.playSound("SomErro.wav");
                JOptionPane.showMessageDialog(this, "Você clicou no botão errado!", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        return btn;
    }

    // Carrega imagem de fundo
    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagemFundoPath));
        imagemDeFundo = icon.getImage();
    }

    // Troca o painel atual pelo novo
    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    // Desenha os componentes do painel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo != null) {
            g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }

        int w = getWidth();
        int h = getHeight();

        // Desenha ícone da chave se necessário
        if (PainelCBTCeChave.indexChave == 1) {
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/ChaveIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.9), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }

        // Desenha ícones do cinturão e adesivo se necessário
        if (Cinturao.index == 1) {
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/CinturaoIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.8), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
            Image imagemExtra2 = new ImageIcon(getClass().getResource("/Assets/Imagens/AdesivoIcone.png")).getImage();
            g.drawImage(imagemExtra2, (int)(w * 0.7), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }
    }

    // Reposiciona componentes ao redimensionar
    private void reposicionarComponentes() {
        int w = getWidth();
        int h = getHeight();

        // Define posições dos botões
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        
        btnPorta1.setBounds((int)(w * 0.24), (int)(h * 0.426), (int)(w * 0.12), (int)(h * 0.11));
        btnPorta2.setBounds((int)(w * 0.44), (int)(h * 0.426), (int)(w * 0.12), (int)(h * 0.11));
        btnPorta3.setBounds((int)(w * 0.64), (int)(h * 0.428), (int)(w * 0.12), (int)(h * 0.11));
        btnPorta4.setBounds((int)(w * 0.24), (int)(h * 0.575), (int)(w * 0.12), (int)(h * 0.11));
        btnPorta5.setBounds((int)(w * 0.44), (int)(h * 0.575), (int)(w * 0.12), (int)(h * 0.11));
        btnPorta6.setBounds((int)(w * 0.64), (int)(h * 0.579), (int)(w * 0.12), (int)(h * 0.11));
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
