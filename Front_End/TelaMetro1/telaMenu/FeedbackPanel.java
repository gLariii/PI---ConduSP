package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import Assets.Cores;

public class FeedbackPanel extends JPanel {

    private Image ImagemDeFundo;
    private Image logoOriginal, logoRedimensionada;
    private int logoWidth = 40;
    private int logoHeight = 40;
    private Runnable voltarAcao;

    public FeedbackPanel(String imagemPath, Runnable voltarAcao) {
        this.voltarAcao = voltarAcao;

        carregarImagens(imagemPath);

        setLayout(new BorderLayout());
        setOpaque(false);

        add(criarNavBar(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);

        configurarRedimensionamentoMouse();
    }

    private void carregarImagens(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null) {
                ImagemDeFundo = ImageIO.read(isFundo);
            }

            InputStream isLogo = getClass().getResourceAsStream("/Assets/Imagens/logoORG.png");
            if (isLogo != null) {
                logoOriginal = ImageIO.read(isLogo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        redimensionarLogo(logoWidth, logoHeight);
    }

    private JPanel criarNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO);
        navBar.setPreferredSize(new Dimension(getWidth(), 60));

        // Botão de Voltar
        JButton btnVoltar = botoes.criarBotaoVoltar();
        btnVoltar.addActionListener(e -> {
            if (voltarAcao != null) {
                voltarAcao.run();
            }
        });

        JPanel painelEsquerdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        painelEsquerdo.setOpaque(false);
        painelEsquerdo.add(btnVoltar);

        navBar.add(painelEsquerdo, BorderLayout.WEST);

        // Título
        JLabel titulo = new JLabel("Feedbacks Gerais");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        navBar.add(titulo, BorderLayout.CENTER);

        return navBar;
    }

    private JPanel criarPainelCentral() {
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);

        JLabel label = new JLabel("Aqui serão exibidos os feedbacks.");
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setForeground(Color.WHITE);

        centro.add(label);

        return centro;
    }

    public void redimensionarLogo(int width, int height) {
        if (logoOriginal != null) {
            width = Math.max(20, Math.min(width, 100));
            height = Math.max(20, Math.min(height, 100));

            logoRedimensionada = logoOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.logoWidth = width;
            this.logoHeight = height;
            repaint();
        }
    }

    private void configurarRedimensionamentoMouse() {
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                int incremento = e.isControlDown() ? 10 : 5;
                int novoSize = Math.max(20, logoWidth - (notches * incremento));
                redimensionarLogo(novoSize, novoSize);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ImagemDeFundo != null) {
            g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }

        if (logoRedimensionada != null) {
            int x = getWidth() - logoWidth - 15;
            int y = getHeight() - logoHeight - 15;
            g.drawImage(logoRedimensionada, x, y, this);

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));

            String texto = "Condução SP";
            int textoX = 15;
            int textoY = getHeight() - 15;

            g2d.drawString(texto, textoX, textoY);
            g2d.dispose();
        }
    }
}
