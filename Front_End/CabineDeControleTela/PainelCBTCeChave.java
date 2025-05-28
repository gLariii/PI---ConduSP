package CabineDeControleTela;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class PainelCBTCeChave extends JPanel {
    public static int indexCBCT = 0;
    public static int indexChave = 0;
    private Image imagemDeFundo;
    private JFrame parentFrame;
    private int ordemCliques;

    private final String[] backgroundsCBTC = {
        "Imagens/ChaveCBTCRM.jpg",
        "Imagens/ChaveCBTCAM.jpg",
    };
    private final String[] backgroundsChave = {
        "Imagens/ComChaveOperador.jpg",
        "Imagens/SemChaveoperador.jpg",
    };

    // Botões como atributos
    private JButton btnVoltar, btnCBTC, btnChave;

    public PainelCBTCeChave(JFrame frame, int ordemCliques) {
        this.parentFrame = frame;
        this.ordemCliques = ordemCliques;
        ordemCliques++;

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
            indexChave = (indexChave + 1) % backgroundsChave.length;
            atualizarIconeChave();
            repaint();
        });
        atualizarIconeChave(); // <- Aqui! Para carregar inicialmente
        add(btnChave);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> substituirPainel(new CabineDeControleTela(parentFrame, ordemCliques)));
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
