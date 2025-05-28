package DDUTela;

import javax.swing.*;
import CabineDeControleTela.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import Assets.*;

public class INFOPASS extends JPanel {

    private JFrame parentFrame;
    private Image imagemDeFundo;

    private JButton botao1;
    private JButton botao2;
    private JButton botao3;
    private JButton botao5;
    private JButton btnVoltar;

    private int ordemCliques;

    public INFOPASS(JFrame frame, int ordemCliques) {
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
            ImageIcon icon = new ImageIcon(getClass().getResource("/DDUTela/assets/images/DDUInfopass.jpg"));
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
        botao1 = criarBotao(e -> trocarTela(new DDUMenu(parentFrame, ordemCliques)));
        botao2 = criarBotao(e -> trocarTela(new FE(parentFrame, ordemCliques)));
        botao3 = criarBotao(e -> trocarTela(new INFOPASS(parentFrame, ordemCliques)));
        botao5 = criarBotao(e -> trocarTela(new MANUT(parentFrame, ordemCliques)));
        btnVoltar = criarBotaoVoltar(e -> trocarTela(new CabineDeControleTela(parentFrame, ordemCliques)));

        add(botao1);
        add(botao2);
        add(botao3);
        add(botao5);
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
        return btn;
    }

    private JButton criarBotaoVoltar(java.awt.event.ActionListener acao) {
        JButton btn = new JButton("Voltar");
        btn.addActionListener(acao);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();
        botao1.setBounds((int) (w * 0.285), (int) (h * 0.748), (int) (w * 0.03), (int) (h * 0.05));
        botao2.setBounds((int) (w * 0.415), (int) (h * 0.751), (int) (w * 0.03), (int) (h * 0.05));
        botao3.setBounds((int) (w * 0.46), (int) (h * 0.755), (int) (w * 0.03), (int) (h * 0.05));
        botao5.setBounds((int) (w * 0.592), (int) (h * 0.76), (int) (w * 0.03), (int) (h * 0.05));
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
