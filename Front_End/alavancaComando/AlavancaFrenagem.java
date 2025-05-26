package alavancaComando;

import javax.swing.*;
import CabineDeControleTela.CabineDeControleTela;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AlavancaFrenagem extends JPanel {

    private JFrame parentFrame;
    private Image imagemDeFundo;

    private JButton botaoAceleracao;
    private JButton botaoEmergencia;
    private JButton btnVoltar;

    private int ordemCliques;
    
    public AlavancaFrenagem(JFrame frame, int ordemCliques) {
        this.ordemCliques = ordemCliques;
        ordemCliques++;

        this.parentFrame = frame;
        setLayout(null);
        setSize(frame.getSize());

        adicionarBotoes();
        adicionarListenerDeRedimensionamento();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo == null) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/alavancaComando/imagens/alavancaFrenagem.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarBotoes() {
        botaoAceleracao = criarBotao(e -> trocarTela(new AlavancaAceleracao(parentFrame, ordemCliques)));
        botaoEmergencia = criarBotao(e -> trocarTela(new AlavancaEmergencia(parentFrame, ordemCliques)));
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame, ordemCliques)));

        add(botaoAceleracao);
        add(botaoEmergencia);
        add(btnVoltar);

        reposicionarBotoes();
    }

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
                    g2.setColor(Color.YELLOW); // Cor da borda no hover
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
        return btn;
    }
    

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();
        //Tamanho e Posicionamento
        botaoAceleracao.setBounds((int) (w * 0.4), (int) (h * 0.8), (int) (w * 0.2), (int) (h * 0.1));
        botaoEmergencia.setBounds((int) (w * 0.4), (int) (h * 0.2), (int) (w * 0.2), (int) (h * 0.1));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
    }

    private void adicionarListenerDeRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
