package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import Assets.Cores;

public class FeedbackPanel extends JPanel {

    private Image imagemDeFundo;
    private Image logoOriginal, logoRedimensionada;
    private int logoWidth = 40;
    private int logoHeight = 40;
    private Runnable voltarAcao;

    public FeedbackPanel(Runnable voltarAcao) {
        this.voltarAcao = voltarAcao;

        carregarImagemDeFundo();
        carregarLogo();

        setLayout(new BorderLayout());
        setOpaque(false);

        add(criarNavBar(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);
    }

    private void carregarImagemDeFundo() {
        try {
            InputStream isFundo = getClass().getResourceAsStream("/Assets/Imagens/TelaInicial4Corrigida.png");
            if (isFundo != null) {
                imagemDeFundo = ImageIO.read(isFundo);
            } else {
                System.err.println("Imagem de fundo não encontrada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarLogo() {
        try (InputStream isLogo = getClass().getResourceAsStream("/Assets/Imagens/logoORG.png")) {
            if (isLogo != null) {
                logoOriginal = ImageIO.read(isLogo);
                if (logoOriginal != null) {
                    logoRedimensionada = logoOriginal.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
                }
            } else {
                System.err.println("Logo não encontrada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JPanel criarNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO);
        navBar.setPreferredSize(new Dimension(getWidth(), 60));

        JButton btnVoltar = botoes.criarBotaoVoltar();
        btnVoltar.addActionListener(e -> {
            if (voltarAcao != null) {
                voltarAcao.run();
            }
        });

        JPanel painelEsquerdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        painelEsquerdo.setOpaque(false);
        painelEsquerdo.add(btnVoltar);
        navBar.add(painelEsquerdo, BorderLayout.WEST);

        JLabel titulo = new JLabel("Feedbacks Gerais", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        navBar.add(titulo, BorderLayout.CENTER);

        JPanel painelDireito = new JPanel();
        painelDireito.setOpaque(false);
        painelDireito.setPreferredSize(new Dimension(btnVoltar.getPreferredSize().width + 30, 60));
        navBar.add(painelDireito, BorderLayout.EAST);

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagemDeFundo != null) {
            g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
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
