package TelaMetro1.telaMenu; 
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.IOException;
import Assets.Cores;

public class botoes {

    // Carrega um ícone de imagem
    private static ImageIcon carregarIcone(String path, int width, int height) {
        try (InputStream is = botoes.class.getResourceAsStream(path)) {
            if (is != null) {
                Image img = ImageIO.read(is).getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cria um botão padrão com texto e ícone
    public static JButton criarBotaoPadrao(String texto, String iconePath, int iconeWidth,
                                         int iconeHeight, int horizontalTextPosition,
                                         Dimension preferredSize, int fontSize) {
        JButton button = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };

        // Configurações visuais do botão padrão
        button.setFont(new Font("Arial", Font.BOLD, fontSize));
        button.setForeground(Color.WHITE);
        button.setBackground(Cores.AZUL_METRO);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 2),
                BorderFactory.createEmptyBorder(20, 40, 20, 20)));
        button.setFocusPainted(false);
        button.setPreferredSize(preferredSize);

        // Adiciona ícone se houver
        if (iconePath != null && !iconePath.isEmpty()) {
            ImageIcon icon = carregarIcone(iconePath, iconeWidth, iconeHeight);
            if (icon != null) {
                button.setIcon(icon);
                button.setHorizontalTextPosition(SwingConstants.RIGHT);
                button.setVerticalTextPosition(SwingConstants.CENTER);
                button.setIconTextGap(20);
                button.setHorizontalAlignment(SwingConstants.LEFT);
            }
        }
        return button;
    }

    // Botão Maquinarios
    public static JButton criarBotaoMaquinario() {
        return criarBotaoPadrao("Maquinário", "/Assets/Imagens/metro.png", 80, 80,
                                 SwingConstants.RIGHT, new Dimension(500, 200), 40);
    }

    // Botão Feedbacks
    public static JButton criarBotaoFeedBackPessoal() {
        return criarBotaoPadrao("Feedbacks", "/Assets/Imagens/feedback.png", 80, 80,
                                 SwingConstants.RIGHT, new Dimension(500, 200), 40);
    }

    // Botão Supervisor
    public static JButton criarBotaoSupervisor() {
        return criarBotaoPadrao("Supervisor", "/Assets/Imagens/supervisor.png", 80, 80,
                                 SwingConstants.RIGHT, new Dimension(500, 200), 40);
    }

    // Botão Desconectar
    public static JButton criarBotaoDesconectar() {
        return criarBotaoPadrao("Desconectar", "/Assets/Imagens/sair.png", 50, 50,
                                 SwingConstants.LEFT, new Dimension(250, 70), 28);
    }

    // Botão Configurações 
    public static JButton criarBotaoConfiguracoes() {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Desenha o ícone centralizado
                if (getIcon() != null) {
                    int x = (getWidth() - getIcon().getIconWidth()) / 2;
                    int y = (getHeight() - getIcon().getIconHeight()) / 2;
                    getIcon().paintIcon(this, g2d, x, y);
                }
                g2d.dispose();
            }
        };

        // Carrega e define o ícone
        ImageIcon icon = carregarIcone("/Assets/Imagens/engrenagem.png", 50, 50);
        if (icon != null) {
            button.setIcon(icon);
        }

        // Configurações visuais do botão
        button.setPreferredSize(new Dimension(60, 60));
        button.setOpaque(true);
        button.setBackground(Cores.AZUL_METRO);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);

        return button;
    }

    // Botão Fechar
    public static JButton criarBotaoFechar() {
        return criarBotaoPadrao("Fechar", null, 0, 0,
                                 SwingConstants.CENTER, new Dimension(150, 50), 18);
    }

    // Botão voltar 
    public static JButton criarBotaoVoltar() {
        JButton button = criarBotaoPadrao("", "/Assets/Imagens/seta.png", 40, 40,
                                 SwingConstants.CENTER, new Dimension(60, 60), 0);

        // Remove bordas e preenchimento
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setIconTextGap(0);

        // Centraliza o ícone
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);

        return button;
    }
}