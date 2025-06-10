package CabineDeControleTela;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import ChaveReversoraTela.*;
import Carro.*;
import Model.*;
public class PainelCBTCeChave extends JPanel {
    private int idUsuarioLogado;
    private String tipo_usuarioLogado;
    public static int indexCBCT = 0;
    public static int indexChave = 0;
    private Image imagemDeFundo;
    private JFrame parentFrame;
    private int ordemCliques;
    private boolean geraPontuacao = false;
    private int pontuacao;
    private int feedback;
    private static boolean primeiroClique = true;
    private static boolean segundoClique = true;
    public static boolean chaveInserida = true; 

    private final String[] backgroundsCBTC = {
        "Imagens/ChaveCBTCAM.jpg",
        "Imagens/ChaveCBTCRM.jpg",
    };
    private final String[] backgroundsChave = {
        "Imagens/ComChaveOperador.jpg",
        "Imagens/SemChaveoperador.jpg",
    };

    // Botões como atributos
    private JButton btnVoltar, btnCBTC, btnChave;

    public PainelCBTCeChave(JFrame frame, String tipo_usuario, int idUsuario) { // Construtor agora recebe o ID do usuário
        this.idUsuarioLogado = idUsuario;
        this.tipo_usuarioLogado = tipo_usuario;
        this.parentFrame = frame;
        this.ordemCliques = 0;

        setLayout(null);
        adicionarBotoes();

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
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/PainelCBTCeChave.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        int w = getWidth();
        int h = getHeight();
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

    private void adicionarBotoes() {
        // Dentro do construtor, após criar o btnCBTC:
        btnCBTC = new JButton("");
        btnCBTC.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCBTC.setOpaque(false);
        btnCBTC.setContentAreaFilled(false);
        btnCBTC.setBorderPainted(false);
        btnCBTC.setFocusPainted(false);
        btnCBTC.addActionListener(e -> {
            if (geraPontuacao == false && ChaveReversoraTela.indexChaveReversora == 2){
                SalvarResposta.pontuacao +=1;
                this.feedback = 4;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                geraPontuacao = true;
            }
            indexCBCT = (indexCBCT + 1) % backgroundsCBTC.length;
            atualizarIconeCBTC();
        });
        atualizarIconeCBTC(); // <- Aqui! Para carregar inicialmente
        add(btnCBTC);


        btnChave = new JButton("");
        btnChave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChave.setOpaque(false);
        btnChave.setContentAreaFilled(false);
        btnChave.setBorderPainted(false);
        btnChave.setFocusPainted(false);
        btnChave.addActionListener(e -> {
            AudioPlayer.playSound("SomChave.wav");
            indexChave = (indexChave + 1) % backgroundsChave.length;
            atualizarIconeChave();
            repaint();
            if (primeiroClique == true){
                SalvarResposta.pontuacao += 2;
                this.feedback = 16;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                primeiroClique = false;
                segundoClique = true;
            }
            if (segundoClique == true && Portas.index != 0){
                SalvarResposta.pontuacao += 2;
                this.feedback = 33;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                segundoClique = false;
            }
            chaveInserida = !chaveInserida;
        });
        atualizarIconeChave(); // <- Aqui! Para carregar inicialmente
        add(btnChave);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> substituirPainel(new CabineDeControleTela(parentFrame, tipo_usuarioLogado, idUsuarioLogado)));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnVoltar);

        reposicionarBotoes();
    }

    private void atualizarIconeCBTC() {
    ImageIcon icon = new ImageIcon(getClass().getResource(backgroundsCBTC[indexCBCT]));
    Image img = icon.getImage();

    // Garante que o botão já foi posicionado
    int largura = btnCBTC.getWidth();
    int altura = btnCBTC.getHeight();

    // Só redimensiona se o botão tiver tamanho definido
    if (largura > 0 && altura > 0) {
        Image imgRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        btnCBTC.setIcon(new ImageIcon(imgRedimensionada));
    } else {
        // Se o botão ainda não tiver tamanho (em layout inicial), usa ícone normal
        btnCBTC.setIcon(icon);
    }
}

    private void atualizarIconeChave() {
    ImageIcon icon = new ImageIcon(getClass().getResource(backgroundsChave[indexChave]));
    Image img = icon.getImage();

    // Garante que o botão já foi posicionado
    int largura = btnChave.getWidth();
    int altura = btnChave.getHeight();

    // Só redimensiona se o botão tiver tamanho definido
    if (largura > 0 && altura > 0) {
        Image imgRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        btnChave.setIcon(new ImageIcon(imgRedimensionada));
    } else {
        // Se o botão ainda não tiver tamanho (em layout inicial), usa ícone normal
        btnChave.setIcon(icon);
    }
}


    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        //Tamanho e Posicionamento
        btnCBTC.setBounds((int)(w * 0.438), (int)(h * 0.775), (int)(w * 0.065), (int)(h * 0.12));
        btnChave.setBounds((int)(w * 0.435), (int)(h * 0.467), (int)(w * 0.08), (int)(h * 0.095));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        atualizarIconeCBTC();
        atualizarIconeChave();
        repaint();
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
