package Carro;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import CabineDeControleTela.*;

public class Sinalizacao extends JPanel {
    private final String[] backgrounds = { // Imagens possíveis de fundo
        "Imagens/SinalizacaoExternaAcessa.jpg",
        "Imagens/SinalizacaoExternaApagada.jpg",
    };

    public static int index = 0; // Índice para controlar qual imagem exibir
    private Image imagemDeFundo; // Imagem de fundo atual
    private JFrame parentFrame; // Frame pai que contém este painel
    private int ordemCliques; // Contador de cliques (não utilizado)
    private String tipo_usuarioLogado; // Tipo do usuário logado
    private int idUsuarioLogado; // ID do usuário logado

    // Botão da interface
    private JButton btnVoltar;

    public Sinalizacao(JFrame frame, String tipo_usuario, int idUsuario) {
        this.tipo_usuarioLogado = tipo_usuario;
        this.idUsuarioLogado = idUsuario;
        this.ordemCliques = ordemCliques;
        ordemCliques++;
        this.parentFrame = frame;

        // Atualiza o índice caso o painel externo esteja aberto
        if (PainelExternoAberto.index == 1){
            index = 1;
        }

        setLayout(null); // Layout absoluto
        adicionarBotoes(); // Adiciona os botões
        carregarImagemFundo(); // Carrega imagem inicial

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
        if (imagemDeFundo == null) { // Carrega imagem padrão se não carregada
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/01 - Painel (1).jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this); // Desenha imagem de fundo
    }

    // Adiciona e configura o botão Voltar
    private void adicionarBotoes() {
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            AudioPlayer.playSound("SomCaminhar.wav"); 
            substituirPainel(new Carro5VisaoGeral(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
        });
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

    // Carrega a imagem de fundo com base no índice
    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
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
