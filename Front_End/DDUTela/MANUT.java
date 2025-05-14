package DDUTela;

import javax.swing.*;
import java.awt.*;

public class MANUT extends JFrame {
    public MANUT() {
        setTitle("DDU Manut");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/DDUTela/assets/images/DDUmanut.png"));

        Image imagem = icon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);

        JLabel fundo = new JLabel(new ImageIcon(imagem));
        fundo.setBounds(0, 0, 1920, 1080);
        
        setContentPane(painel);

        setVisible(true);

        JButton botao1 = new JButton(""); 
        botao1.setBounds(30, 980, 130, 90); 
        botao1.addActionListener(e -> {new DDUPrincipal(); dispose();});
        botao1.setOpaque(false);
        botao1.setContentAreaFilled(false);
        botao1.setBorderPainted(false);
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton botao2 = new JButton("");  
        botao2.setBounds(615, 980, 130, 90);
        botao2.addActionListener(e -> {new FE(); dispose();});
        botao2.setOpaque(false);
        botao2.setContentAreaFilled(false);
        botao2.setBorderPainted(false);
        botao2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton botao3 = new JButton(""); 
        botao3.setBounds(770 , 980, 130, 90); 
        botao3.addActionListener(e -> {new INFOPASS(); dispose();});
        botao3.setOpaque(false);
        botao3.setContentAreaFilled(false);
        botao3.setBorderPainted(false);
        botao3.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton botao4 = new JButton("");  
        botao4.setBounds(945, 980, 130, 100); 
        botao4.addActionListener(e -> {new PREPARA(); dispose();});
        botao4.setOpaque(false);
        botao4.setContentAreaFilled(false);
        botao4.setBorderPainted(false);
        botao4.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton botao5 = new JButton(""); 
        botao5.setBounds(1350, 980, 130, 90);
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
