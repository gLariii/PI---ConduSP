package ADUTela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import CabineDeControleTela.*;
import Assets.*;

// Representa o painel do menu ADU.
public class ADUMenu extends JPanel {

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

        setLayout(null);
        setSize(frame.getSize());

        adicionarComponentes();
        adicionarListenerDeRedimensionamento();
    }

    // Sobrescreve o método paintComponent para desenhar a imagem de fundo no painel.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo == null) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/ADUTela/imagens/ADUMenu.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        
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