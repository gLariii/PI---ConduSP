package DDUTela;

import javax.swing.*;
import java.awt.*;

public class AUTO extends JFrame {
    public AUTO() {
        setTitle("DDU Manut");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/DDUTela/assets/images/DDUmanut.jpg"));
        
        Image imagem = icon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);

        JLabel fundo = new JLabel(new ImageIcon(imagem));
        fundo.setBounds(0, 0, 1920, 1080);

        painel.add(fundo);
        setContentPane(painel);

        setVisible(true);
    }
}