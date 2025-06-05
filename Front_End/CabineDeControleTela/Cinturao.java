package CabineDeControleTela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Cinturao extends JPanel {

    private JFrame parentFrame;

    private final String[] backgrounds = {
        "Imagens/Cinturao.png",
        "Imagens/ItensColetados.jpg"
    };

    public static int index = 0;
    private Image imagemDeFundo;
    private JButton botao1;
    private JButton btnVoltar;
    private JLabel labelItensColetados;

    private int ordemCliques;

    public Cinturao(JFrame frame, int ordemCliques) {
        this.ordemCliques = ordemCliques;
        ordemCliques++;
        
        this.parentFrame = frame;
        setLayout(null);

        carregarImagemFundo();

        adicionarComponentes();

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reposicionarComponentes();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo != null) {
            g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }
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

    private void adicionarComponentes() {
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame, ordemCliques)));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        btnVoltar.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnVoltar);

        botao1 = new JButton("Coletar Cinturão e Adesivo");
        botao1.addActionListener(e -> {
            AudioPlayer.playSound("SomColetado.wav");
            index = 1;
            carregarImagemFundo();
            repaint();
            botao1.setVisible(false);
            labelItensColetados.setVisible(true);
        });
        botao1.setFont(new Font("Arial", Font.BOLD, 14));
        botao1.setForeground(Color.WHITE);
        botao1.setBackground(new Color(30, 60, 90));
        botao1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        botao1.setFocusPainted(false);
        botao1.setContentAreaFilled(false);
        botao1.setOpaque(true);
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (index == 0){
            add(botao1);
        }


        labelItensColetados = new JLabel("ITENS COLETADOS!");
        labelItensColetados.setFont(new Font("Arial", Font.BOLD, 50));
        labelItensColetados.setForeground(Color.YELLOW);
        labelItensColetados.setHorizontalAlignment(SwingConstants.CENTER);
        labelItensColetados.setVisible(false);
        add(labelItensColetados);

        reposicionarComponentes();
    }

    private void reposicionarComponentes() {
        int w = getWidth();
        int h = getHeight();

        botao1.setBounds((int)(w * 0.40), (int)(h * 0.10), (int)(w * 0.25), (int)(h * 0.05));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        labelItensColetados.setBounds(0, (int)(h * 0.08), w, 100);

        repaint();
    }

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}