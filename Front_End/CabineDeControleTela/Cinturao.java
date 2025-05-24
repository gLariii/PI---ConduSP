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

    public Cinturao(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);

        // Carrega as duas imagens
        imagemNormal = new ImageIcon(getClass().getResource("Imagens/Cinturao.png")).getImage();
        imagemColetado = new ImageIcon(getClass().getResource("Imagens/CinturaoAdesivo.jpg")).getImage();

        adicionarBotoes();

        // Responsividade
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    @Override 
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g); 
        Image img = mostrandoColetado ? imagemColetado : imagemNormal;
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this); 
    } 

    private void adicionarBotoes() { 
        // Botão Voltar (estilo padrão)
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame)));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnVoltar);

        // Botão Coletar (estilizado)
        botao1 = new JButton("Coletar Cinturão e Adesivo");
        botao1.addActionListener(e -> mostrarImagemColetadoTemporariamente());            
        botao1.setFont(new Font("Arial", Font.BOLD, 14));
        botao1.setForeground(Color.WHITE);
        botao1.setBackground(Color.BLACK); // Fundo sólido
        botao1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        botao1.setFocusPainted(false);
        botao1.setContentAreaFilled(true);
        botao1.setOpaque(true);
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao1);

        reposicionarBotoes();
    }

    private void mostrarImagemColetadoTemporariamente() {
        mostrandoColetado = true;

        // Esconde os botões
        botao1.setVisible(false);
        btnVoltar.setVisible(false);
        repaint();

        // Timer para mostrar imagem normal e reaparecer os botões após 4 segundos
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                mostrandoColetado = false;

                SwingUtilities.invokeLater(() -> {
                    botao1.setVisible(true);
                    btnVoltar.setVisible(true);
                    repaint();
                });
            }
        }, 2000);
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        // Posicionamento adequado
        botao1.setBounds((int)(w * 0.35), (int)(h * 0.10), (int)(w * 0.30), (int)(h * 0.07));
        btnVoltar.setBounds((int)(w * 0.02), (int)(h * 0.02), (int)(w * 0.10), (int)(h * 0.05));

        repaint();
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
