package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import Assets.Cores;

public class menu extends JPanel {
    private Image ImagemDeFundo, logoOriginal, logoRedimensionada;
    private JButton btnMaquinario, btnFeedbacks, btnSupervisor, btnConfiguracoes;
    private int logoWidth = 40; 
    private int logoHeight = 40; 

    public menu(String imagemPath) {
        carregarImagens(imagemPath);
        redimensionarLogo(logoWidth, logoHeight);
        setLayout(new BorderLayout());
        setOpaque(false);

        add(criarNavBar(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);
        configurarRedimensionamentoMouse();
    }

    private void carregarImagens(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null)
                ImagemDeFundo = ImageIO.read(isFundo);

            InputStream isLogo = getClass().getResourceAsStream("/Assets/Imagens/logoORG.png");
            if (isLogo != null) {
                logoOriginal = ImageIO.read(isLogo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        addMouseWheelListener(e -> {
            int notches = e.getWheelRotation();
            int incremento = e.isControlDown() ? 10 : 5; 
            int novoSize = Math.max(20, logoWidth - (notches * incremento));
            redimensionarLogo(novoSize, novoSize);
        });
    }

    private JPanel criarNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO);
        navBar.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel titulo = new JLabel("Sistema de Gerenciamento");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 350, 0, 0));

        navBar.add(titulo, BorderLayout.CENTER);

        btnConfiguracoes = botoes.criarBotaoConfiguracoes();
        JPanel painelConfig = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        painelConfig.setOpaque(false);
        painelConfig.add(btnConfiguracoes);
        navBar.add(painelConfig, BorderLayout.WEST);

        return navBar;
    }

    private JPanel criarPainelCentral() {
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);

        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 0, 40));
        painelBotoes.setOpaque(false);
        painelBotoes.setPreferredSize(new Dimension(500, 570));

        btnMaquinario = botoes.criarBotaoMaquinario();
        btnFeedbacks = botoes.criarBotaoFeedBackPessoal();
        btnSupervisor = botoes.criarBotaoSupervisor();

        painelBotoes.add(btnMaquinario);
        painelBotoes.add(btnFeedbacks);
        painelBotoes.add(btnSupervisor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(60, 0, 0, 0);

        centro.add(painelBotoes, gbc);
        return centro;
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