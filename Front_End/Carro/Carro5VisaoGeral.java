package Carro;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import CabineDeControleTela.*;

public class Carro5VisaoGeral extends JPanel {
    public final String[] backgrounds = {
        "Imagens/PortaAbertaSinalizacao.png",
        "Imagens/PortaAbertaSemSinalizacao.png",
        "Imagens/PortaFechadaSinalizacao.jpg",
        "Imagens/PortaFechadaLacrada.png"
    };
    public static int index = 0;
    private Image imagemDeFundo;
    private JFrame parentFrame;

    private int ordemCliques;

    // Botões como atributos
    private JButton btnPortas, btnVoltar, btnPainel, btnSinalização;

    public Carro5VisaoGeral(JFrame frame, int ordemCliques) {
        this.ordemCliques = ordemCliques;
        ordemCliques++;
        this.parentFrame = frame;
        if (PainelExternoAberto.index == 1){
            index = 1;
            if (Portas.index == 1){
                index = 2;
                }
            else if (Portas.index == 2){
                index = 3;
            }
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

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo == null) {
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/Carro5falha.png"));
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
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> substituirPainel(new AreaLateral(parentFrame, ordemCliques)));
        btnPortas = criarBotao(() -> substituirPainel(new Portas(parentFrame, ordemCliques)));
        btnPainel = criarBotao(() -> substituirPainel(new PainelExternoFechado(parentFrame, ordemCliques)));
        btnSinalização = criarBotao (() -> substituirPainel(new Sinalizacao(parentFrame)));
        add(btnVoltar);
        add(btnPainel);
        add(btnPortas);
        add(btnSinalização);
        reposicionarBotoes();
    }

    private JButton criarBotao(Runnable action) {
        JButton botao = new JButton("");
        botao.addActionListener(e -> action.run());
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setForeground(Color.BLACK); // cor padrão da borda
        return botao;
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        //Tamanho e Posicionamento
        btnPortas.setBounds((int)(w * 0.79), (int)(h * 0.33), (int)(w * 0.025), (int)(h * 0.3));
        btnPainel.setBounds((int)(w * 0.765), (int)(h * 0.69), (int)(w * 0.18), (int)(h * 0.13));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        btnSinalização.setBounds((int)(w * 0.835), (int)(h * 0.305), (int)(w * 0.032), (int)(h * 0.045));
        
        repaint();
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
