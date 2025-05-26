package ModuloDeComunicaçãoTela;

import javax.swing.*;
import CabineDeControleTela.CabineDeControleTela;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ModuloDeComunicacaoTelaInicial extends JPanel {

    private Image imagemDeFundo;
    private JFrame parentFrame;

    private CircleButton botao1;
    private CircleButton botao2;
    private JButton btnVoltar;

    private int ordemCliques;

    public ModuloDeComunicacaoTelaInicial(JFrame frame, int ordemCliques) {
        this.ordemCliques = ordemCliques;
        ordemCliques++;

        this.parentFrame = frame;
        setLayout(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/Modulo de comunicação.jpg"));
        imagemDeFundo = icon.getImage();

        criarBotoes();
        adicionarListenerRedimensionamento();
        reposicionarBotoes();
    }

    private void criarBotoes() {
        botao1 = new CircleButton("", e -> substituirPainel(new PATela(parentFrame, ordemCliques)));
        botao2 = new CircleButton("", e -> substituirPainel(new PALista(parentFrame, ordemCliques)));
        btnVoltar = new JButton("Voltar");
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> substituirPainel(new CabineDeControleTela(parentFrame, ordemCliques)));


        add(botao1);
        add(botao2);
        add(btnVoltar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        // Tamanho e Posicionamento
        botao1.setBounds((int)(w * 0.164), (int)(h * 0.257), (int)(w * 0.04), (int)(h * 0.06));
        botao2.setBounds((int)(w * 0.649), (int)(h * 0.855), (int)(w * 0.04), (int)(h * 0.06));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
    }

    

    private void adicionarListenerRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    // Classe personalizada para botões circulares
    class CircleButton extends JButton {
        private boolean isHovering = false;
    
        public CircleButton(String texto, java.awt.event.ActionListener acao) {
            super(texto);
            addActionListener(acao);
            setOpaque(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.BLACK);
    
            // Mouse listener para detectar hover
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
            Graphics2D g2 = (Graphics2D) g;
            if (isHovering) {
                g2.setColor(Color.YELLOW); // cor da borda de hover
                g2.setStroke(new BasicStroke(2));
            } else {
                g2.setColor(getForeground());
                g2.setStroke(new BasicStroke(1));
            }
            g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
        }
    
        @Override
        public boolean contains(int x, int y) {
            int radius = getWidth() / 2;
            return Math.pow(x - getWidth() / 2, 2) + Math.pow(y - getHeight() / 2, 2) <= Math.pow(radius, 2);
        }
    }
}
    
