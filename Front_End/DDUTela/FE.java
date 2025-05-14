package DDUTela;

import javax.swing.*;
import java.awt.*;

public class FE extends JFrame {
    public FE() {
        setTitle("DDU FE");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/DDUTela/assets/images/DDUfe.jpg"));

        Image imagem = icon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);

        JLabel fundo = new JLabel(new ImageIcon(imagem));
        fundo.setBounds(0, 0, 1920, 1080);

        setContentPane(painel);

        setVisible(true);

        JButton botao1 = new JButton(""); 
        botao1.setBounds(390, 870, 100, 80); 
        botao1.addActionListener(e -> {new DDUPrincipal(); dispose();});
        botao1.setOpaque(false);
        botao1.setContentAreaFilled(false);
        botao1.setBorderPainted(false);
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton botao2 = new JButton("");  
        botao2.setBounds(780, 870, 100, 80); 
        botao2.addActionListener(e -> {new FE(); dispose();});
        botao2.setOpaque(false);
        botao2.setContentAreaFilled(false);
        botao2.setBorderPainted(false);
        botao2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton botao3 = new JButton(""); 
        botao3.setBounds(900, 870, 100, 80); 
        botao3.addActionListener(e -> {new INFOPASS(); dispose();});
        botao3.setOpaque(false);
        botao3.setContentAreaFilled(false);
        botao3.setBorderPainted(false);
        botao3.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton botao4 = new JButton("");  
        botao4.setBounds(1038, 880, 100, 80); 
        botao4.addActionListener(e -> {new MANUT(); dispose();});
        botao4.setOpaque(false);
        botao4.setContentAreaFilled(false);
        botao4.setBorderPainted(false);
        botao4.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton botao5 = new JButton("");  
        botao5.setBounds(1300, 880, 100, 80); 
        botao5.addActionListener(e -> {new MANUT(); dispose();});
        botao5.setOpaque(false);
        botao5.setContentAreaFilled(false);
        botao5.setBorderPainted(false);
        botao5.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painel.add(botao1);
        painel.add(botao2);
        painel.add(botao3);
        painel.add(botao4);
        painel.add(botao5);
        painel.add(fundo);
        
        setContentPane(painel);
 
        setVisible(true);

    }
}