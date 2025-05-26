package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.IOException;
import Assets.Cores; 

public class botoes {

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

    private static JButton criarBotaoPadrao(String texto, String iconePath, int iconeWidth, 
                                          int iconeHeight, Dimension preferredSize, int fontSize) { 
        JButton button = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                if (isOpaque()) {
                    g.setColor(getBackground());
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Arial", Font.BOLD, fontSize)); 
        button.setForeground(Color.WHITE);
        button.setBackground(Cores.AZUL_METRO); 
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        button.setFocusPainted(false);
        button.setPreferredSize(preferredSize); 

        button.setHorizontalAlignment(SwingConstants.LEFT); 

        if (iconePath != null && !iconePath.isEmpty()) {
            ImageIcon icon = carregarIcone(iconePath, iconeWidth, iconeHeight);
            if (icon != null) {
                button.setIcon(icon);
                button.setHorizontalTextPosition(SwingConstants.RIGHT); 
                button.setVerticalTextPosition(SwingConstants.CENTER);
            
                button.setIconTextGap(60); 
            }
        }
        return button;
    }

    public static JButton criarBotaoMaquinario() {
        return criarBotaoPadrao("Maquinário", "/Assets/Imagens/metro.png", 80, 80, 
                               new Dimension(500, 200), 40); 
    }

    public static JButton criarBotaoFeedBackPessoal() {
        return criarBotaoPadrao("Feedbacks", "/Assets/Imagens/feedback.png", 80, 80,
                               new Dimension(500, 200), 40); 
    }

    public static JButton criarBotaoSupervisor() {
        return criarBotaoPadrao("Supervisor", "/Assets/Imagens/supervisor.png", 80, 80,
                               new Dimension(500, 200), 40); 
    }
    
    public static JButton criarBotaoDesconectar() {
        return criarBotaoPadrao("Desconectar", "/Assets/Imagens/sair.png", 50, 50,
                               new Dimension(250, 70), 28); 
    }

    public static JButton criarBotaoConfiguracoes() {
        return criarBotaoPadrao("Configurações", "/Assets/Imagens/engrenagem.png", 50, 50,
                               new Dimension(250, 70), 28); 
    }
}