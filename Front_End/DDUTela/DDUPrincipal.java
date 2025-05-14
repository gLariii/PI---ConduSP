package DDUTela;

import javax.swing.*;
import java.awt.*;

public class DDUPrincipal extends JFrame {

    public DDUPrincipal() {
        setTitle("Menu Principal");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("assets/images/DDUprepara.jpg"));

        Image imagem = icon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);

        JLabel fundo = new JLabel(new ImageIcon(imagem));
        fundo.setBounds(0, 0, 1920, 1080);

        // Botão 2
        JButton botao2 = new JButton(""); 
        botao2.setBounds(797, 818, 55, 53);
        botao2.addActionListener(e -> {new FE(); dispose();});
        botao2.setOpaque(false);
        botao2.setContentAreaFilled(false);
        botao2.setBorderPainted(false);
        botao2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Botão 3
        JButton botao3 = new JButton(""); 
        botao3.setBounds(882, 818, 55, 53);
        botao3.addActionListener(e -> {new INFOPASS(); dispose();});
        botao3.setOpaque(false);
        botao3.setContentAreaFilled(false);
        botao3.setBorderPainted(false);
        botao3.setCursor(new Cursor(Cursor.HAND_CURSOR));
         

        // Botão 4
        JButton botao4 = new JButton(""); 
        botao4.setBounds(968, 818, 60, 55);
        botao4.addActionListener(e -> {new PREPARA(); dispose();});
        botao4.setOpaque(false);
        botao4.setContentAreaFilled(false);
        botao4.setBorderPainted(false);
        botao4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Botão 5
        JButton botao5 = new JButton("");
        botao5.setBounds(1140, 818, 60, 55);
        botao5.addActionListener(e -> {new MANUT(); dispose();});
        botao5.setOpaque(false);
        botao5.setContentAreaFilled(false);
        botao5.setBorderPainted(false);
        botao5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        painel.add(botao2);
        painel.add(botao3);
        painel.add(botao4);
        painel.add(botao5);
        painel.add(fundo);

        setContentPane(painel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DDUPrincipal());
    }
}