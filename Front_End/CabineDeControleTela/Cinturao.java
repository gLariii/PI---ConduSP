package CabineDeControleTela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Cinturao extends JPanel {

    private JFrame parentFrame;

    private Image imagemNormal;
    private Image imagemColetado;
    private boolean mostrandoColetado = false;

    private JButton botao1;
    private JButton btnVoltar;
    private JLabel labelItensColetados;

    private int ordemCliques;

    public Cinturao(JFrame frame, int ordemCliques) {
        this.ordemCliques = ordemCliques;
        ordemCliques++;
        
        this.parentFrame = frame;
        setLayout(null);

        imagemNormal = new ImageIcon(getClass().getResource("Imagens/Cinturao.png")).getImage();
        imagemColetado = new ImageIcon(getClass().getResource("Imagens/ItensColetados.png")).getImage();

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
        Image img = mostrandoColetado ? imagemColetado : imagemNormal;
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarComponentes() {
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame, ordemCliques)));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnVoltar);

        botao1 = new JButton("Coletar Cinturão e Adesivo");
        botao1.addActionListener(e -> mostrarImagemColetadoTemporariamente());
        botao1.setFont(new Font("Arial", Font.BOLD, 14));
        botao1.setForeground(Color.WHITE);
        botao1.setBackground(Color.BLACK);
        botao1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        botao1.setFocusPainted(false);
        botao1.setContentAreaFilled(true);
        botao1.setOpaque(true);
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao1);

        labelItensColetados = new JLabel("ITENS COLETADOS!");
        labelItensColetados.setFont(new Font("Arial", Font.BOLD, 50));
        labelItensColetados.setForeground(Color.YELLOW);
        labelItensColetados.setHorizontalAlignment(SwingConstants.CENTER);
        labelItensColetados.setVisible(false);
        add(labelItensColetados);

        reposicionarComponentes();
    }

    private void mostrarImagemColetadoTemporariamente() {
        mostrandoColetado = true;
        botao1.setVisible(false);
        btnVoltar.setVisible(false);
        labelItensColetados.setVisible(true);
        repaint();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                mostrandoColetado = false;
                SwingUtilities.invokeLater(() -> {
                    botao1.setVisible(true);
                    btnVoltar.setVisible(true);
                    labelItensColetados.setVisible(false);
                    repaint();
                });
            }
        }, 2000);
    }

    private void reposicionarComponentes() {
        int w = getWidth();
        int h = getHeight();

        botao1.setBounds((int)(w * 0.40), (int)(h * 0.10), (int)(w * 0.20), (int)(h * 0.05));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        labelItensColetados.setBounds(0, (int)(h * 0.08), w, 100);

        repaint();
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}