package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class menu extends JPanel {
    private Image ImagemDeFundo;

    public menu(String imagemErro) {
        try (InputStream is = getClass().getResourceAsStream(imagemErro)) {
            if (is != null) {
                ImagemDeFundo = ImageIO.read(is);
            } else {
                System.out.println("Imagem de fundo não encontrada: " + imagemErro);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a imagem de fundo");
        }

        setLayout(null); 

        // Texto Bem Vindo
        JLabel bemVindo = new JLabel("Bem Vindos!");
        bemVindo.setFont(new Font("Serif", Font.BOLD, 48)); 
        bemVindo.setForeground(Color.WHITE); 
        bemVindo.setBounds(250, 140, 400, 60); 
        add(bemVindo);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ImagemDeFundo != null) {
            g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}