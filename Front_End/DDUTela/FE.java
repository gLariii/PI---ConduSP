package DDUTela;

import javax.swing.*;
import CabineDeControleTela.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FE extends JPanel {

    private JFrame parentFrame;
    private Image imagemDeFundo;

    private JButton botao1;
    private JButton botaoINFOPASS;
    private JButton botaoMANUT;
    private JButton btnVoltar;

    private int ordemCliques;

    public FE(JFrame frame, int ordemCliques) {
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
            ImageIcon icon = new ImageIcon(getClass().getResource("/DDUTela/assets/images/DDUFE.jpg"));
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
        botaoINFOPASS = criarBotao(e -> trocarTela(new INFOPASS(parentFrame, ordemCliques)));
        botaoMANUT = criarBotao(e -> trocarTela(new MANUT(parentFrame, ordemCliques)));
        btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame, ordemCliques)));

        add(botao1);
        add(botaoINFOPASS);
        add(botaoMANUT);
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
        btn.setForeground(Color.BLACK); // cor padrão da borda quando não está em hover
        return btn;
    }
    
    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();
        //Tamanho e Posicionamento
        botao1.setBounds((int) (w * 0.285), (int) (h * 0.75), (int) (w * 0.03), (int) (h * 0.05));
        botaoINFOPASS.setBounds((int) (w * 0.46), (int) (h * 0.755), (int) (w * 0.03), (int) (h * 0.05));
        botaoMANUT.setBounds((int) (w * 0.595), (int) (h * 0.76), (int) (w * 0.03), (int) (h * 0.05));
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
