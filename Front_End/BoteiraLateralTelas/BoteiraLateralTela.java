package BoteiraLateralTelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import CabineDeControleTela.*;
import ChaveReversoraTela.*;
import Model.SalvarResposta;
import ModuloDeComunicaçãoTela.*;
import Assets.*;

public class BoteiraLateralTela extends JPanel {

    private final String[] backgrounds = {
        "Imagens/BoteiraLateral.png",
        "Imagens/BoteiraLateralAcesa.png"
    };

    private static int index = 1;
    private Image imagemDeFundo;
    private JFrame parentFrame;
    private JButton btnTrocar;
    private JButton btnVoltar;
    private int idUsuarioLogado;
    private String tipo_usuarioLogado;
    private boolean primeiroClique = true;
    private int feedback;   
    private int ordemCliques;

    public BoteiraLateralTela(JFrame frame, String tipo_usuario, int idUsuario) {
        this.tipo_usuarioLogado = tipo_usuario;
        this.parentFrame = frame;
        this.idUsuarioLogado = idUsuario;
        setLayout(null);

        carregarImagemFundo();

        // Botão para trocar o fundo
        btnTrocar = new JButton("") {
            private boolean isHovering = false;
        
            {
                setOpaque(false);
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setForeground(Color.BLACK);
        
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
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();
        
                if (getModel().isPressed()) {
                    g2.setColor(new Color(0, 0, 0, 0));

                } else if (isHovering) {
                    g2.setColor(new Color(0, 0, 0, 0));

                } else {
                    g2.setColor(new Color(0, 0, 0, 0));

                }
        
                g2.fillOval(0, 0, width, height);
        
                if (isHovering) {
                    g2.setColor(Color.YELLOW);
                    g2.setStroke(new BasicStroke(2));
                } else {
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(0));
                }
        
                g2.drawOval(0, 0, width - 1, height - 1);
                g2.dispose();
            }
        
            @Override
            public boolean contains(int x, int y) {
                int radius = getWidth() / 2;
                return Math.pow(x - getWidth() / 2, 2) + Math.pow(y - getHeight() / 2, 2) <= Math.pow(radius, 2);
            }
        };
        btnTrocar.addActionListener(e -> {
            index = (index + 1) % backgrounds.length;
            if (ChaveReversoraTela.indexChaveReversora != 0 && ModuloDeComunicacaoTelaInicial.primeiroClique == false){
                if (primeiroClique == true){
                    SalvarResposta.pontuacao += 2;
                    feedback = 10;
                    SalvarResposta.salvarResposta(idUsuarioLogado, feedback);
                    }
                primeiroClique = false;
                }
            carregarImagemFundo();
            reposicionarComponentes();
            repaint();
        });

        if (ChaveReversoraTela.indexChaveReversora != 0){
            add(btnTrocar);
        }
        

        // Botão Voltar
        btnVoltar = new JButton("Voltar");
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        btnVoltar.addActionListener(e -> voltarParaLateral());
        add(btnVoltar);

        adicionarListenerRedimensionamento();
        reposicionarComponentes();
    }

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }

    private void voltarParaLateral() {
        parentFrame.setContentPane(new AreaLateral(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
        parentFrame.revalidate();
        parentFrame.repaint();
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

    private void reposicionarComponentes() {
        int w = getWidth();
        int h = getHeight();

        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));

        // Troca posição do botão de acordo com a imagem de fundo
        if (index == 0) {
            btnTrocar.setBounds((int)(w * 0.472), (int)(h * 0.467), (int)(w * 0.06), (int)(h * 0.09));
        } else {
            btnTrocar.setBounds((int)(w * 0.472), (int)(h * 0.714), (int)(w * 0.06), (int)(h * 0.09));
        }
    }

    private void adicionarListenerRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarComponentes();
            }
        });
    }
}
