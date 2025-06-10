package Carro;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import CabineDeControleTela.*;

public class Sinalizacao extends JPanel {
    private final String[] backgrounds = {
        "Imagens/SinalizacaoExternaAcessa.jpg",
        "Imagens/SinalizacaoExternaApagada.jpg",
    };

    public static int index = 0;
    private Image imagemDeFundo;
    private JFrame parentFrame;
    private int ordemCliques;
    private String tipo_usuarioLogado; // Variável para armazenar o tipo de usuário logado
    private int idUsuarioLogado; // Variável para armazenar o ID do usuário logado

    // Botões como atributos
    private JButton btnVoltar;

    public Sinalizacao(JFrame frame, String tipo_usuario, int idUsuario) {
        this.tipo_usuarioLogado = tipo_usuario;
        this.idUsuarioLogado = idUsuario; // Armazena o ID do usuário logado
        this.ordemCliques = ordemCliques;
        ordemCliques++;
        this.parentFrame = frame;
        if (PainelExternoAberto.index == 1){
            index = 1;
        }
        setLayout(null);
        adicionarBotoes();
        carregarImagemFundo();
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
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/01 - Painel (1).jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarBotoes() {
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {AudioPlayer.playSound("SomCaminhar.wav");substituirPainel(new Carro5VisaoGeral(parentFrame, tipo_usuarioLogado, idUsuarioLogado));});
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

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        //Tamanho e Posicionamento
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        repaint();
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
