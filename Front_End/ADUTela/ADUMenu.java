package ADUTela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import CabineDeControleTela.*;
import Assets.*;
import Carro.*;

// Representa o painel do menu ADU.
public class ADUMenu extends JPanel {

    // Array de caminhos para as imagens de fundo
    private final String[] backgrounds = {
        "imagens/ADUMenu.jpg",
        "imagens/ADULuzAcesa.png",
    };

    // Índice para selecionar a imagem de fundo a ser exibida
    public static int index = 0;

    private JFrame parentFrame;
    private Image imagemDeFundo;
    private JButton btnVoltar;
    private int ordemCliques;
    private int idUsuarioLogado;
    private String tipo_usuarioLogado; 

    // Construtor da classe, inicializa o painel com os dados do usuário e da janela principal.
    public ADUMenu(JFrame frame, String tipo_usuario, int idUsuario) {
        this.tipo_usuarioLogado = tipo_usuario;
        this.parentFrame = frame;
        this.idUsuarioLogado = idUsuario;
        this.ordemCliques = ordemCliques;
        this.parentFrame = frame;

        if (PainelExternoAberto.index == 1) {
            index = 1;
        }else {
            index = 0;
        }
        setLayout(null);
        setSize(frame.getSize());

        adicionarComponentes();
        adicionarListenerDeRedimensionamento();
        carregarImagemFundo();
    }

    // Sobrescreve o método paintComponent para desenhar a imagem de fundo no painel.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo != null) {
            g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }

        int w = getWidth();
        int h = getHeight();

        // Adiciona o cinturão e adesivo se eles tiverem sido coletados.
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


    // Inicializa e adiciona todos os componentes visuais, como botões, ao painel.
    private void adicionarComponentes() {
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame, tipo_usuarioLogado, idUsuarioLogado)));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnVoltar);

        reposicionarComponentes();
    }

    // Define a posição e o tamanho dos componentes com base nas dimensões atuais do painel.
    private void reposicionarComponentes() {
        int w = getWidth();
        int h = getHeight();

        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        
        repaint();
    }

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }

    // Adiciona um listener que chama 'reposicionarComponentes' sempre que o painel é redimensionado.
    private void adicionarListenerDeRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarComponentes();
            }
        });
    }

    // Substitui o painel atual no JFrame por um novo painel.
    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}