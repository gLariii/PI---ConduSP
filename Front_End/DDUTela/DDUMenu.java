package DDUTela;

import javax.swing.*;
import CabineDeControleTela.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import Carro.*;

public class DDUMenu extends JPanel {

    // Array de caminhos para as imagens de fundo
    private final String[] backgrounds = {
        "assets/images/DDUMenuPortaAberta.jpg",
        "assets/images/DDUPortaIsolada.jpg",
    };

    // Índice para selecionar a imagem de fundo a ser exibida
    public static int index = 0;

    private JFrame parentFrame; // A janela principal que contém este painel
    private Image imagemDeFundo; // A imagem de fundo carregada

    // Botões da interface
    private JButton botaoFE;
    private JButton botaoINFOPASS;
    private JButton botaoMANUT;
    private JButton btnVoltar;

    // Variáveis para controle de estado e dados do usuário
    private int ordemCliques;
    private int idUsuarioLogado;
    private String tipo_usuarioLogado;


    public DDUMenu(JFrame frame, String tipo_usuario, int idUsuario) {
        this.tipo_usuarioLogado = tipo_usuario;
        this.idUsuarioLogado = idUsuario;
        this.ordemCliques = ordemCliques;
        ordemCliques++;

        this.parentFrame = frame;
        if (PainelExternoAberto.index == 1) {
            index = 1;
        }
        setLayout(null); // Usa layout absoluto para posicionar componentes manualmente
        setSize(frame.getSize());

        adicionarBotoes();
        adicionarListenerDeRedimensionamento();
        carregarImagemFundo();
    }

    //Sobrescreve o método paintComponent para desenhar a imagem de fundo e ícones dependendo da co (chave, cinturão) sobre o painel.

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

    //Inicializa e adiciona todos os botões ao painel.
     
    private void adicionarBotoes() {
        botaoFE = criarBotao(e -> trocarTela(new FE(parentFrame, tipo_usuarioLogado, idUsuarioLogado)));
        botaoINFOPASS = criarBotao(e -> trocarTela(new INFOPASS(parentFrame, tipo_usuarioLogado, idUsuarioLogado)));
        botaoMANUT = criarBotao(e -> trocarTela(new MANUT(parentFrame, tipo_usuarioLogado, idUsuarioLogado)));
        
        btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame, tipo_usuarioLogado, idUsuarioLogado)));

        add(botaoFE);
        add(botaoINFOPASS);
        add(botaoMANUT);
        add(btnVoltar);

        reposicionarBotoes();
    }

    
     //Cria um botão personalizado,que exibe uma borda amarela, quando o mouse passa por cima (efeito hover).

    private JButton criarBotao(java.awt.event.ActionListener acao) {
        JButton btn = new JButton("") {
            private boolean isHovering = false;
            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        isHovering = true;
                        repaint();
                    }
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        isHovering = false;
                        repaint();
                    }
                });
            }
            @Override
            protected void paintBorder(Graphics g) {
                if (isHovering) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.YELLOW);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                }
            }
        };
        btn.addActionListener(acao);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setForeground(Color.BLACK);
        return btn;
    }

    //Define a posição e o tamanho dos botões com base nas dimensões do painel, permitindo que eles se ajustem ao redimensionamento da janela.

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();
        botaoFE.setBounds((int) (w * 0.414), (int) (h * 0.755), (int) (w * 0.03), (int) (h * 0.05));
        botaoINFOPASS.setBounds((int) (w * 0.459), (int) (h * 0.755), (int) (w * 0.03), (int) (h * 0.05));
        botaoMANUT.setBounds((int) (w * 0.594), (int) (h * 0.76), (int) (w * 0.03), (int) (h * 0.05));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
    }

    
    //Carrega a imagem de fundo a partir do caminho especificado no array 'backgrounds'.

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }


     //Adiciona um listener que chama 'reposicionarBotoes' sempre que o painel é redimensionado
    private void adicionarListenerDeRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    
       //Substitui o painel atual no JFrame por um novo painel
    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}